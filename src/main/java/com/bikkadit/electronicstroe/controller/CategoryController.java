package com.bikkadit.electronicstroe.controller;

import com.bikkadit.electronicstroe.dtos.CategoryDto;
import com.bikkadit.electronicstroe.dtos.ProductDto;
import com.bikkadit.electronicstroe.helper.ApiResponse;
import com.bikkadit.electronicstroe.helper.AppConstant;
import com.bikkadit.electronicstroe.helper.ImageResponse;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.service.CategoryService;
import com.bikkadit.electronicstroe.service.FileServices;
import com.bikkadit.electronicstroe.service.ProductService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileServices fileServices;

    @Value("${category.image.path}")
    private String imagePath;

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

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<CategoryDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){
        log.info("Category Controller -searchproduct method is Start");
        PageableResponse<CategoryDto> response = categoryService.searchCategory(query, pageNumber, pageSize, sortBy, sortDir);
        log.info("Category Controller -searchproduct method is end");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //upload image
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(
            @PathVariable String categoryId,
            @RequestParam("productImage") MultipartFile image
    ) throws IOException {
        log.info("Category Controller -upload image method is Start");
        String fileName = fileServices.uploadFile(image, imagePath);
        CategoryDto singleById = categoryService.getSingleById(categoryId);
        singleById.setCategoryImage(fileName);
        CategoryDto updatedIMgName = categoryService.update(singleById, categoryId);

        ImageResponse categoryImageIsSuccessfullyUpload = ImageResponse.builder()
                .imageName(updatedIMgName.getCategoryImage())
                .message("Product image is successfully upload")
                .status(HttpStatus.CREATED)
                .success(true)
                .build();
        log.info("Category Controller -upload image method is end");
        return new ResponseEntity<>(categoryImageIsSuccessfullyUpload,HttpStatus.CREATED);


    }

    @GetMapping("/image/{categoryId}")
    public void serveProductImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        log.info("Category Controller -serve image method is start");
        CategoryDto singleById = categoryService.getSingleById(categoryId);
        log.info("product image name:{}",singleById.getCategoryImage());
        InputStream resource = fileServices.getResource(imagePath, singleById.getCategoryImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Category Controller -serve image method is end");
        StreamUtils.copy(resource,response.getOutputStream());
    }

    //create product with category...
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable String categoryId,
            @RequestBody ProductDto productDto
    ){
        log.info("Category Controller -product create with categoy method is start");
        ProductDto productServiceWithCategory = productService.createWithCategory(productDto, categoryId);
        log.info("Category Controller -product create with categoy method is end");
        return  new ResponseEntity<>(productServiceWithCategory,HttpStatus.CREATED);
    }

    //update category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable String categoryId,
            @PathVariable String productId
    ){
        log.info("Category Controller -updateCategoryOfProduct method is start");
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        log.info("Category Controller -updateCategoryOfProduct method is end");
        return new ResponseEntity<>(productDto,HttpStatus.OK);


    }
    //get products of categorys
    @GetMapping ("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){
        log.info("Category Controller -getProductOfCategory method is start");
        PageableResponse<ProductDto> allCategory = productService.getAllCategory(categoryId,pageNumber, pageSize, sortBy, sortDir);
        log.info("Category Controller -getProductOfCategory method is start");
        return new ResponseEntity<>(allCategory,HttpStatus.OK);


    }
}
