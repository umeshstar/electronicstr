package com.bikkadit.electronicstroe.controller;

import com.bikkadit.electronicstroe.dtos.ApiResponse;
import com.bikkadit.electronicstroe.dtos.UserDto;

import com.bikkadit.electronicstroe.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

  //  private Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


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
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable String useraId){
        log.info("This is method for update user request start");
        UserDto updatedUser = this.userService.updateUser(userDto, useraId);
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
                .message("UserDeleted SuccessFully..")
                .success(true)
                .build();
        log.info("This is method for delete user request complete");
        return new ResponseEntity<>(api,HttpStatus.OK);
    }
    //getall
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        log.info("This is request for getall user request start");
        List<UserDto> list = this.userService.getAllUser();
        log.info("This is method for getall user request complete");
        return new ResponseEntity<>(list,HttpStatus.OK);


    }

    //single get
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUsers(@PathVariable String userId){
        log.info("This is request for getsingle user by id request start");
        UserDto userbyId = this.userService.getUserbyId(userId);
        log.info("This is request for getsingle user by id request end");
        return new ResponseEntity<>(userbyId,HttpStatus.OK);

    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserbyEmail( @PathVariable String email) {
        log.info("This is request for get user by email request start");
        UserDto userEmail = userService.getUserbyEmail(email);
        log.info("This is request for get user by email request start");
        return new ResponseEntity<>(userEmail,HttpStatus.OK);

    }
    @GetMapping("/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("This is request for search user by keyword request start");
        List<UserDto> srUser = this.userService.searchUser(keyword);
        log.info("This is request for search user by keyword request start");
        return new ResponseEntity<>(srUser,HttpStatus.OK);

    }



}
