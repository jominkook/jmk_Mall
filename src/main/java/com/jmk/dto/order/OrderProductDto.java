package com.jmk.dto.order;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto {
    private int memberOrderProductId;
    private int memberOrderId;
    private int productId;
    private int quantity;
    private int price;
    private Timestamp created_dt;
}
