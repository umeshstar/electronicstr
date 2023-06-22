package com.bikkadit.electronicstroe.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="category")
public class Category {

    @Id
    @Column(name="id")
    private String categoryId;
    @Column(name = "C_title",length = 60,nullable = false)
    private String categoryTitle;
    @Column(name = "C_description",length = 100)
    private String categoryDescription;

    private String categoryImage;





}
