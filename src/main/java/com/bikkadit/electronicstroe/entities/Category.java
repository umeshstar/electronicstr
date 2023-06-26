package com.bikkadit.electronicstroe.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products= new ArrayList<>();



}
