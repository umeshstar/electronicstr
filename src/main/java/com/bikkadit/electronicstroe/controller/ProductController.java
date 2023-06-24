package com.bikkadit.electronicstroe.controller;

import com.bikkadit.electronicstroe.dtos.CategoryDto;
import com.bikkadit.electronicstroe.dtos.ProductDto;
import com.bikkadit.electronicstroe.helper.ApiResponse;
import com.bikkadit.electronicstroe.helper.AppConstant;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    //create
    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){

        ProductDto productDto1 = productService.create(productDto);

        return new ResponseEntity<>(productDto, HttpStatus.CREATED);

    }
    //update
    @PutMapping ("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId,@RequestBody ProductDto productDto){

        ProductDto updated = productService.update(productDto, productId);

        return new ResponseEntity<>(updated, HttpStatus.OK);

    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable String productId){
        log.info("Product Controller -deleteproduct method is Start");
        productService.delete(productId);
        ApiResponse response = ApiResponse.builder()
                .message("Product is Deleted Succesfully!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Product Controller -dleteproduct method is End");
        return new ResponseEntity<>(response,HttpStatus.OK);


    }

    //getSingle
    @GetMapping("/getSingle/{productId}")
    public ResponseEntity<ProductDto> getSingle(@PathVariable String productId){
        log.info("product Controller -getSingle method is Start");
        ProductDto productDto = productService.get(productId);
        log.info("product Controller -getSingle method is End");
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    //getAll
    @GetMapping("/")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){

        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    //getAll-Live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){

        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    //search
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){

        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

}
