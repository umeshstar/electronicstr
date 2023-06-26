package com.bikkadit.electronicstroe.dtos;

import com.bikkadit.electronicstroe.entities.Category;
import com.bikkadit.electronicstroe.entities.CustomeFields;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto extends CustomeFields {

    private String productId;

    private String title;

    private String description;

    private double price;

    private double discountedPrice;

    private Integer quantity;

    private boolean live;

    private boolean stock;

    private String productImageName;

    private CategoryDto category;
}
