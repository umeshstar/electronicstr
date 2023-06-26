package com.bikkadit.electronicstroe.dtos;

import com.bikkadit.electronicstroe.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto {

    private String userId;

    @Size(min = 4 ,max = 20,message = "Invalid name")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "Invalid email...!!")
    private String email;

    //^ represents starting character of the string.
    //(?=.*[0-9]) represents a digit must occur at least once.
    //(?=.*[a-z]) represents a lower case alphabet must occur at least once.
    @NotBlank(message = "password is required")
    @Pattern(regexp = "^[a-z0-9]{5}",message = "invalid password please fill min-5 char with number and alphabet")
    private String password;

    @NotBlank
    @Size(min = 4,max = 6,message = "invalid gender!!!")
    private String gender;

    @NotBlank(message = "write somthing about yourself")
    private String about;

    @ImageNameValid(message = "write image name")
    private String imageName;

}
