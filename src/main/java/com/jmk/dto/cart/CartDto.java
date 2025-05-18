package com.jmk.dto.cart;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDto {
    private int cartId;
    private String productImage;
    private String productName;
    private int productPrice;
    private int cartQuantity;
}
