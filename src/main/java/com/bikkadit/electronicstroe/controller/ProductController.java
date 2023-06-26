package com.bikkadit.electronicstroe.controller;

import com.bikkadit.electronicstroe.dtos.ProductDto;
import com.bikkadit.electronicstroe.helper.ApiResponse;
import com.bikkadit.electronicstroe.helper.AppConstant;
import com.bikkadit.electronicstroe.helper.ImageResponse;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.service.FileServices;
import com.bikkadit.electronicstroe.service.ProductService;
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
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileServices fileServices;

    @Value("${product.image.path}")
    private String imagePath;


    //create
    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        log.info("Product Controller -createProduct method is start");
        ProductDto productDto1 = productService.create(productDto);
        log.info("Product Controller -createProduct method is end");

        return new ResponseEntity<>(productDto, HttpStatus.CREATED);

    }
    //update
    @PutMapping ("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId,@RequestBody ProductDto productDto)
    {
        log.info("Product Controller -updateProduct method is start");
        ProductDto updated = productService.update(productDto, productId);
        log.info("Product Controller -updateProduct method is end");
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
        log.info("product Controller -getallproduct method is start");
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("product Controller -getallproduct method is End");
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
        log.info("product Controller -getAllLive method is start");
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("product Controller -getAllLive method is End");
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
        log.info("product Controller -searchProduct method is start");
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        log.info("product Controller -searchProduct method is End");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    //uploadimage
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage")MultipartFile image
            )
            throws IOException {
        log.info("product Controller -uploadProductImage method is start");
        String fileName = fileServices.uploadFile(image, imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedIMgName = productService.update(productDto, productId);

        ImageResponse productImageIsSuccessfullyUpload = ImageResponse.builder()
                .imageName(updatedIMgName.getProductImageName())
                .message("Product image is successfully upload")
                .status(HttpStatus.CREATED)
                .success(true)
                .build();

        log.info("product Controller -uploadProductImage method is end");
        return new ResponseEntity<>(productImageIsSuccessfullyUpload,HttpStatus.CREATED);

    }
    //serve image

    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        log.info("product Controller -serveProductImage method is start");
        ProductDto productDto = productService.get(productId);
        log.info("product image name:{}",productDto.getProductImageName());
        InputStream resource = fileServices.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("product Controller -serveProductImage method is start");
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
