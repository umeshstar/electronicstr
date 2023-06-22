package com.bikkadit.electronicstroe.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder

public class CategoryDto {

    @Column(name="id")
    private String categoryId;

    @Column(name = "C_title",length = 60,nullable = false)
    private String categoryTitle;

    @Column(name = "C_description",length = 100)
    private String categoryDescription;

    private String categoryImage;

}
