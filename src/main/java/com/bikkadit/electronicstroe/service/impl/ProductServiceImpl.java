package com.bikkadit.electronicstroe.service.impl;

import com.bikkadit.electronicstroe.dtos.ProductDto;
import com.bikkadit.electronicstroe.entities.Category;
import com.bikkadit.electronicstroe.entities.Product;
import com.bikkadit.electronicstroe.exception.ResourceNotFoundException;
import com.bikkadit.electronicstroe.helper.PHelper;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.repositories.CategoryRepository;
import com.bikkadit.electronicstroe.repositories.ProductRepository;
import com.bikkadit.electronicstroe.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.uuid.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${product.image.path}")
    private String imagePath;

    @Override
    public ProductDto create(ProductDto productDto) {
        log.info(" ProductImpl -create method is Start");
        Product product = modelMapper.map(productDto, Product.class);
        //product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added date
        product.setAddedDate(new Date());
        Product savedProduct = productRepository.save(product);
        log.info(" ProductImpl -create method is end");
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        log.info(" ProductImpl -update method is start");
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Foun this id"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());
        Product updatedProduct = productRepository.save(product);
        log.info(" ProductImpl -update method is end");
        return modelMapper.map(updatedProduct,ProductDto.class);

    }

    @Override
    public void delete(String productId) {
        log.info(" ProductImpl -delete method is start");
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with this id"));
        //delete user image
        //  image/user/a
        String fullPath = imagePath + product.getProductImageName();
        try{
            Path path1 = Paths.get(fullPath);
            Files.delete(path1);
        } catch (NoSuchFileException ex){

        }catch (IOException e){
            throw new RuntimeException(e);
        }
        log.info(" ProductImpl -delete method is end");
        productRepository.delete(product);

    }

    @Override
    public ProductDto get(String productId) {
        log.info(" ProductImpl -get method is start");
        Product product1 = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with this id"));
        log.info(" ProductImpl -get method is end");
        return modelMapper.map(product1,ProductDto.class);
    }



    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize,String sortBy, String sortDir) {
        log.info(" ProductImpl -getAll method is start");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products = productRepository.findAll(pageable);

        PageableResponse<ProductDto> pageableResponse = PHelper.getPageableResponse(products, ProductDto.class);
        log.info(" ProductImpl -getAll method is end");
        return pageableResponse;

    }




    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        log.info(" ProductImpl -getAllLive method is start");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products = productRepository.findByLiveTrue(pageable);

        PageableResponse<ProductDto> pageableResponse = PHelper.getPageableResponse(products, ProductDto.class);
        log.info(" ProductImpl -getAllLive method is end");
        return pageableResponse;


    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        log.info(" ProductImpl -searchByTitle method is start");

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products = productRepository.findByTitleContaining(subTitle,pageable);

        PageableResponse<ProductDto> pageableResponse = PHelper.getPageableResponse(products, ProductDto.class);
        log.info(" ProductImpl -searchByTitle method is end");

        return pageableResponse;

    }
//product create with category Id...
//bcz of this api easy to save product with category
//and this impl api is not created in product controller ... this is created in category controller.
    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

        //fetch the category form category dto ... for that requird autowire categoryRepository
        log.info(" ProductImpl -createWithCategory method is start");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not found with this Cat.Id"));

        Product product = modelMapper.map(productDto, Product.class);
        //product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added date
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        log.info(" ProductImpl -searchByTitle method is end");
        return modelMapper.map(savedProduct,ProductDto.class);

    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        //productFetch
        log.info(" ProductImpl -updateCategory method is start");
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("prodcut not found with this ID"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));
        product.setCategory(category);
        Product saved = productRepository.save(product);

        log.info(" ProductImpl -updateCategory method is end");
        return modelMapper.map(saved,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        log.info(" ProductImpl -getAllCategory method is start");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> byCategory = productRepository.findByCategory(category,pageable);
        log.info(" ProductImpl -getAllCategory method is end");
        return PHelper.getPageableResponse(byCategory,ProductDto.class);


    }
}
