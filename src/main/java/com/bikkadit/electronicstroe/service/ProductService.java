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
}
