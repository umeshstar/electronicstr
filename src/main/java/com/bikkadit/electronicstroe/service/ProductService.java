package com.bikkadit.electronicstroe.service;

import com.bikkadit.electronicstroe.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto,String productId);

    //delete
    void delete(String productId);

    //get Single
    ProductDto get(String productId);

    //getAll
   List<ProductDto> getAll();

    //getAll-LIVE
    List<ProductDto> getAllLive();

    //search
    List< ProductDto> searchByTitle(String subTitle);
}
