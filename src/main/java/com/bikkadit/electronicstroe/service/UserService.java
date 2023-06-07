package com.bikkadit.electronicstroe.service;

import com.bikkadit.electronicstroe.dtos.UserDto;
import com.bikkadit.electronicstroe.entities.User;

import java.util.List;

public interface UserService {


    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId);

    //getall
    List<UserDto>getAllUser();

    //get single user by id
    UserDto getUserbyId(String userId);

    //get single user by email
    UserDto getUserbyEmail(String email);

    //search
    List<UserDto>searchUser(String keyword);

    //other user specific feature

}
