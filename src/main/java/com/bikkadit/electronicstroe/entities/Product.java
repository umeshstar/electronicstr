package com.bikkadit.electronicstroe.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {

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
}
