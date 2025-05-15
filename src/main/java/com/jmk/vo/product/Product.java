package com.jmk.vo.product;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private int productId;
    private int categoryId;
    private String productName;
    private int productPrice;
    private String productIngredient;
    private String productImage;
    private int stockQuantity;
    private String productStatus;
    private Timestamp createdDt;
    private Timestamp modifiedDt;
}
