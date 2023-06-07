package com.bikkadit.electronicstroe.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;
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

    @Email(message = "Invalid email...!!")
    private String email;

    //^ represents starting character of the string.
    //(?=.*[0-9]) represents a digit must occur at least once.
    //(?=.*[a-z]) represents a lower case alphabet must occur at least once.
    @NotBlank
    @Pattern(regexp =".^(?=.*[0-9]) + (?=.*[a-z]){5}")
    private String password;

    @Size(min = 4,max = 6,message = "invalid gender!!!")
    private String gender;

    @NotBlank(message = "write somthing about yourself")
    private String about;

    private String imageName;

}
