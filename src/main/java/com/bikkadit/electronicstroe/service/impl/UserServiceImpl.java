package com.bikkadit.electronicstroe.service.impl;

import com.bikkadit.electronicstroe.dtos.UserDto;
import com.bikkadit.electronicstroe.entities.User;
import com.bikkadit.electronicstroe.repositories.UserRepository;
import com.bikkadit.electronicstroe.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        //genrate unique id in stirng formate
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //it convert dto to entity
        User user =dtoToEntity(userDto);
        User savedUser = userRepository.save(user);

        //it convert entity to dto
        UserDto newDto= entityToDto(savedUser);
        return newDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with this id"));
        user.setName(userDto.getName());
       // user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        //save data
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;


    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with this id"));
//      delete user
        userRepository.delete(user);

    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getUserbyId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with this id"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserbyEmail(String email) {
        User user = userRepository.findbyEmail(email).orElseThrow(() -> new RuntimeException("user not found this emial"));

        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

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
