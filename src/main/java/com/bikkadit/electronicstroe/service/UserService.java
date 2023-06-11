package com.bikkadit.electronicstroe.service;

import com.bikkadit.electronicstroe.dtos.UserDto;
import com.bikkadit.electronicstroe.entities.User;
import com.bikkadit.electronicstroe.helper.PageableResponse;

import java.util.List;

public interface UserService {


    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId);

    //getall
    PageableResponse<UserDto>getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single user by id
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String email);

    //search
    List<UserDto>searchUser(String keyword);

    //other user specific feature

}
