package com.bikkadit.electronicstroe.service.impl;

import com.bikkadit.electronicstroe.dtos.CategoryDto;
import com.bikkadit.electronicstroe.dtos.ProductDto;
import com.bikkadit.electronicstroe.entities.Category;
import com.bikkadit.electronicstroe.entities.Product;
import com.bikkadit.electronicstroe.exception.ResourceNotFoundException;
import com.bikkadit.electronicstroe.helper.PHelper;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.repositories.ProductRepository;
import com.bikkadit.electronicstroe.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto create(ProductDto productDto) {

        Product product = modelMapper.map(productDto, Product.class);
        //product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added date
        product.setAddedDate(new Date());
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Foun this id"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct,ProductDto.class);

    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with this id"));
        productRepository.delete(product);

    }

    @Override
    public ProductDto get(String productId) {
        Product product1 = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with this id"));

        return modelMapper.map(product1,ProductDto.class);
    }



    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize,String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products = productRepository.findAll(pageable);

        PageableResponse<ProductDto> pageableResponse = PHelper.getPageableResponse(products, ProductDto.class);

        return pageableResponse;

    }




    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products = productRepository.findByLiveTrue(pageable);

        PageableResponse<ProductDto> pageableResponse = PHelper.getPageableResponse(products, ProductDto.class);

        return pageableResponse;


    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products = productRepository.findByTitleContaining(subTitle,pageable);

        PageableResponse<ProductDto> pageableResponse = PHelper.getPageableResponse(products, ProductDto.class);

        return pageableResponse;

    }
}
