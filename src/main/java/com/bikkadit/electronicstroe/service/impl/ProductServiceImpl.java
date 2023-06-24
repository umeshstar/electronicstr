package com.bikkadit.electronicstroe.service.impl;

import com.bikkadit.electronicstroe.dtos.ProductDto;
import com.bikkadit.electronicstroe.repositories.ProductRepository;
import com.bikkadit.electronicstroe.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto create(ProductDto productDto) {


        return null;
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        return null;
    }

    @Override
    public void delete(String productId) {

    }

    @Override
    public ProductDto get(String productId) {
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        return null;
    }

    @Override
    public List<ProductDto> getAllLive() {
        return null;
    }

    @Override
    public List<ProductDto> searchByTitle(String subTitle) {
        return null;
    }
}
