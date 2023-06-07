package com.bikkadit.electronicstroe.entities;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="users")
public class User {
    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email",unique = true)
    private String email;

    @Column(name = "user_password",length = 5)
    private String password;

    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(name = "user_imageName")
    private String imageName;

}
