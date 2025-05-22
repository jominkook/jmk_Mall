package com.jmk.dto.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderSelectDto {
    private int memberOrderProductId;
    private String memberName;
    private String productImage;
    private int productPrice;
    private String productName;
    private String memberOrderMemo;
    private String memberOrderStatus;
    private int productId;
    private int quantity;
    private String impUid;
}
