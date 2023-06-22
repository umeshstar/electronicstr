package com.bikkadit.electronicstroe.service.impl;

import com.bikkadit.electronicstroe.dtos.CategoryDto;
import com.bikkadit.electronicstroe.entities.Category;
import com.bikkadit.electronicstroe.exception.ResourceNotFoundException;
import com.bikkadit.electronicstroe.helper.PHelper;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.repositories.CategoryRepository;
import com.bikkadit.electronicstroe.service.CategoryService;
import lombok.experimental.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper; //it is used to convert dto to entity and ...entity to dto

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        //it is used to convert dto to entity
        Category category = modelMapper.map(categoryDto, Category.class);

        Category savedCategory = categoryRepository.save(category);
       // it is used to convert  ...entity to dto
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        //below code for get category of given id..which is given byUser by using findbyid ..and findbyid gives us optional respo.
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found exception"));

        //if category is present then update it..with details
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryImage(categoryDto.getCategoryImage());
        Category updatedCategory = categoryRepository.save(category);


        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto getSingleById(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not foundwith " + categoryId));

        return modelMapper.map(category,CategoryDto.class);
    }

//    here below method we required all category in return but in paging and sorting
//    so that we required peageble obj so by using Pageable interface we can get that
    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize,String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = PHelper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }


    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found exception"));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> searchCategory(String keyword) {
        List<Category> byNameContaining = categoryRepository.findByNameContaining(keyword);
        List<CategoryDto> collect = byNameContaining.stream().map(l -> new ModelMapper().map(byNameContaining, CategoryDto.class)).collect(Collectors.toList());
        return collect;
    }
}
