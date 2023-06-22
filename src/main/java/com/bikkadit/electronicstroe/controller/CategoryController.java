package com.bikkadit.electronicstroe.controller;

import com.bikkadit.electronicstroe.dtos.CategoryDto;
import com.bikkadit.electronicstroe.helper.ApiResponse;
import com.bikkadit.electronicstroe.helper.AppConstant;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //Create
    @PostMapping()
    public ResponseEntity<CategoryDto>createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }


    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@RequestBody CategoryDto categoryDto,
                                                     @PathVariable String categoryId)
    {
        CategoryDto updated = categoryService.update(categoryDto, categoryId);
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
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
//get single
    @GetMapping("/getSingle/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
        CategoryDto singleById = categoryService.getSingleById(categoryId);
        return new ResponseEntity<>(singleById,HttpStatus.OK);
    }

    //delete
    public ResponseEntity<ApiResponse>deleteCategory(@RequestBody String categoryId){
        categoryService.delete(categoryId);
        ApiResponse response = ApiResponse.builder()
                .message("Category Deleted Succesfully!!")
                .success(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);


    }

}
