package com.bikkadit.electronicstroe.service.impl;

import com.bikkadit.electronicstroe.dtos.CategoryDto;
import com.bikkadit.electronicstroe.entities.Category;
import com.bikkadit.electronicstroe.exception.ResourceNotFoundException;
import com.bikkadit.electronicstroe.helper.PHelper;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.repositories.CategoryRepository;
import com.bikkadit.electronicstroe.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper; //it is used to convert dto to entity and ...entity to dto

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        log.info("Category CategoryImpl -create method is Start");
        //creating cat..Id randomly
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        //it is used to convert dto to entity
        Category category = modelMapper.map(categoryDto, Category.class);

        Category savedCategory = categoryRepository.save(category);
        log.info("Category CategoryImpl -create method is End");
       // it is used to convert  ...entity to dto
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        log.info("Category CategoryImpl -update method is Start");
        //below code for get category of given id..which is given byUser by using findbyid ..and findbyid gives us optional respo.
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found exception"));

        //if category is present then update it..with details
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryImage(categoryDto.getCategoryImage());
        Category updatedCategory = categoryRepository.save(category);

        log.info("Category CategoryImpl -update method is End");
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto getSingleById(String categoryId) {
        log.info("Category CategoryImpl -getSingle method is Start");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not foundwith " + categoryId));
        log.info("Category CategoryImpl -getSingle method is End");
        return modelMapper.map(category,CategoryDto.class);
    }

//    here below method we required all category in return but in paging and sorting
//    so that we required peageble obj so by using Pageable interface we can get that
    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize,String sortBy, String sortDir) {
        log.info("Category CategoryImpl -getAll method is Start");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = PHelper.getPageableResponse(page, CategoryDto.class);
        log.info("Category CategoryImpl -getAll method is End");
        return pageableResponse;
    }


    @Override
    public void delete(String categoryId) {
        log.info("Category CategoryImpl -delete method is Start");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found exception"));
        log.info("Category CategoryImpl -delete method is End");
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> searchCategory(String keyword) {
        log.info("Category CategoryImpl -searchCategory method is Start");
        List<Category> titleContaining = categoryRepository.findBycategoryTitleContaining(keyword);
        List<CategoryDto> categoryDtos = titleContaining.stream().map(l -> new ModelMapper().map(titleContaining, CategoryDto.class)).collect(Collectors.toList());
        log.info("Category CategoryImpl -searchCategory method is End");
        return categoryDtos;
    }
}
