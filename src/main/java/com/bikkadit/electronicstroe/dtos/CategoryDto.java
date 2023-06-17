package com.bikkadit.electronicstroe.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder

public class CategoryDto {

    @Column(name="id")
    private String CategoryId;
    @Column(name = "C_title",length = 60,nullable = false)
    private String CategoryTitle;
    @Column(name = "C_description",length = 100)
    private String CategoryDescription;

    private String CategoryImage;

}
