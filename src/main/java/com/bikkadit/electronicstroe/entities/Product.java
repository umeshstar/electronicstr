package com.bikkadit.electronicstroe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends CustomeFields{

    @Id
    private String productId;

    @NotBlank
    private String title;

    @Column(length = 1000)
    private String description;

    private double price;

    private double discountedPrice;

    private Integer quantity;

    private Date addedDate;

    private boolean live;

    private boolean stock;

    private String productImageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
