package com.bikkadit.electronicstroe.service.impl;

import com.bikkadit.electronicstroe.dtos.UserDto;
import com.bikkadit.electronicstroe.entities.User;
import com.bikkadit.electronicstroe.exception.ResourceNotFoundException;
import com.bikkadit.electronicstroe.helper.AppConstant;
import com.bikkadit.electronicstroe.repositories.UserRepository;
import com.bikkadit.electronicstroe.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("This is createuser method start of impl");
        //genrate unique id in stirng formate
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //it convert dto to entity
        User user =dtoToEntity(userDto);
        User savedUser = userRepository.save(user);

        //it convert entity to dto
        UserDto newDto= entityToDto(savedUser);
        log.info("This is createuser method end of impl");
        return newDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("This is updateuser method start of impl");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MESSAGE));
        user.setName(userDto.getName());
       // user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        //save data
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        log.info("This is updateuser method end of impl");
        return updatedDto;


    }

    @Override
    public void deleteUser(String userId) {
        log.info("This is deleteuser method start of impl");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MESSAGE));
//      delete user
        log.info("This is deleteuser method end of impl");
        userRepository.delete(user);

    }

    @Override
    public List<UserDto> getAllUser() {
        log.info("This is getalluser method start of impl");
        List<User> users = userRepository.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("This is getalluser method end of impl");
        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        log.info("This is getuserbyid method start of impl");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MESSAGE));
        log.info("This is getuserbyid method end of impl");
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("This is getuserbyemail method start of impl");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MESSAGE));
        log.info("This is getuserbyemail method end of impl");
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("This is searchUser method start of impl");
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("This is searchUser method end of impl");
        return dtoList;

    }
//for conversion
    private UserDto entityToDto(User savedUser) {
        //      UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .password(savedUser.getPassword())
//                .email(savedUser.getEmail())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();

        return mapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {

//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName())
//                .build();
        return mapper.map(userDto,User.class);
    }

}
