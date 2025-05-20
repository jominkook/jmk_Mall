package com.jmk.dto.order;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private int memberOrderId;
    private String memberId;
    private int orderTotalPrice;
    private int memberOrderStatus;
    private Timestamp created_dt;
}
