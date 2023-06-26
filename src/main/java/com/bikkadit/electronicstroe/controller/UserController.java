package com.bikkadit.electronicstroe.controller;

import com.bikkadit.electronicstroe.helper.ApiResponse;
import com.bikkadit.electronicstroe.dtos.UserDto;

import com.bikkadit.electronicstroe.helper.AppConstant;
import com.bikkadit.electronicstroe.helper.ImageResponse;
import com.bikkadit.electronicstroe.helper.PageableResponse;
import com.bikkadit.electronicstroe.service.FileServices;
import com.bikkadit.electronicstroe.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileServices fileServices;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    //create
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        log.info("This is method for create user request start");
        UserDto createUserDto = this.userService.createUser(userDto);
        log.info("This is method for create user request complete");
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable String userId){
        log.info("This is method for update user request start" );
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        log.info("This is method for update user request complete");
        return  ResponseEntity.ok(updatedUser);
    }
    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId)
    {
        log.info("This is method for delete user request start");
        userService.deleteUser(userId);
        ApiResponse api= ApiResponse
                .builder()
                .message(AppConstant.DELETED)
                .success(true)
                .build();
        log.info("This is method for delete user request complete");
        return new ResponseEntity<>(api,HttpStatus.OK);
    }
    //getall
    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir


    ){
        log.info("This is request for getall user request start");
        PageableResponse<UserDto> list = this.userService.getAllUser(pageNumber, pageSize,sortBy,sortDir);
        log.info("This is method for getall user request complete");
        return new ResponseEntity<>(list,HttpStatus.OK);


    }

    //single get
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUsers(@PathVariable String userId){
        log.info("This is request for getsingle user by id request start");
        UserDto userbyId = this.userService.getUserById(userId);
        log.info("This is request for getsingle user by id request end");
        return new ResponseEntity<>(userbyId,HttpStatus.OK);

    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail( @PathVariable String email) {
        log.info("This is request for get user by email request start");
        UserDto userEmail = userService.getUserByEmail(email);
        log.info("This is request for get user by email request start");
        return new ResponseEntity<>(userEmail,HttpStatus.OK);

    }
    @GetMapping("/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("user controller-This is request for search user by keyword request start");
        List<UserDto> srUser = this.userService.searchUser(keyword);
        log.info("This is request for search user by keyword request start");
        return new ResponseEntity<>(srUser,HttpStatus.OK);

    }



    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse>uploadImage(
            @RequestParam("userImage")MultipartFile image,
            @PathVariable String userId            )
            throws IOException {

        log.info("This is request for uploadimage  request start");
        String imageName = fileServices.uploadFile(image, imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        userService.updateUser(user,userId);
        ImageResponse imageResponse= ImageResponse.builder()
                .imageName(imageName)
                .success(true)
                .message("your image is uploaded succesfully!!")
                .status(HttpStatus.CREATED)
                .build();
        log.info("This is request for uploadimage  request end");
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }


    //serve user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        log.info("This is request for serveUserImage  request end");
        UserDto user = userService.getUserById(userId);
        log.info("User image name:{}",user.getImageName());
        InputStream resource = fileServices.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("This is request for serveUserImage  request end");
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
