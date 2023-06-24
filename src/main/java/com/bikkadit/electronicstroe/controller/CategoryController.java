package com.bikkadit.electronicstroe.controller;

import com.bikkadit.electronicstroe.dtos.CategoryDto;
import com.bikkadit.electronicstroe.helper.ApiResponse;
import com.bikkadit.electronicstroe.helper.AppConstant;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.service.CategoryService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;



    //Create
    @PostMapping()
    public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto){
        log.info("Category Controller -createCategory method is Start");
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        log.info("Category Controller -createCategory method is End");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }


    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@RequestBody CategoryDto categoryDto,
                                                     @PathVariable String categoryId)
    {
        log.info("Category Controller -updateCategory method is start");
        CategoryDto updated = categoryService.update(categoryDto, categoryId);
        log.info("Category Controller -updateCategory method is End");
        return new ResponseEntity<>(updated, HttpStatus.OK);

    }
    //retieve..get all
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<CategoryDto>>getAll(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)
    {
        log.info("Category Controller -getAll method is Start");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("Category Controller -getAll method is End");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
//get single
    @GetMapping("/getSingle/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
        log.info("Category Controller -getSingle method is Start");
        CategoryDto singleById = categoryService.getSingleById(categoryId);
        log.info("Category Controller -getSingle method is End");
        return new ResponseEntity<>(singleById,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable String categoryId){
        log.info("Category Controller -deleteCategory method is Start");
        categoryService.delete(categoryId);
        ApiResponse response = ApiResponse.builder()
                .message("Category Deleted Succesfully!!")
                .success(true)
                .build();
        log.info("Category Controller -deleteCategory method is End");
        return new ResponseEntity<>(response,HttpStatus.OK);


    }

    @GetMapping("/{keyword}")
    public ResponseEntity<List<CategoryDto>>searchCategory(@PathVariable String keyword){
        log.info("Category Controller -searchCategory method is Start");
        List<CategoryDto> categoryDtos = categoryService.searchCategory(keyword);
        log.info("Category Controller -searchCategory method is End");
        return new ResponseEntity<List<CategoryDto>>(categoryDtos,HttpStatus.OK);

    }
}
