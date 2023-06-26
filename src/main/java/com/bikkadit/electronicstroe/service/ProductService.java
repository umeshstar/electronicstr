package com.bikkadit.electronicstroe.service;

import com.bikkadit.electronicstroe.dtos.ProductDto;
import com.bikkadit.electronicstroe.helper.PageableResponse;

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

    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //getAll-LIVE
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir);

    //create product with category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);

    //update category of product
    ProductDto updateCategory(String productId,String categoryId);

    PageableResponse<ProductDto> getAllCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir);

}
