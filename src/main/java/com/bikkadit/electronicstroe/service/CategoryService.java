package com.bikkadit.electronicstroe.service;

import com.bikkadit.electronicstroe.dtos.CategoryDto;
import com.bikkadit.electronicstroe.dtos.UserDto;
import com.bikkadit.electronicstroe.helper.PageableResponse;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId);

    //ritriev get-single,
    CategoryDto getSingleById(String categoryId);
    //getAll
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize,String sortBy, String sortDir);

    //delete
    void delete(String categoryId);

    //Search
    List<CategoryDto> searchCategory(String keyword);
}
