package com.jmk.dto.payment;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {
    private int memberOrderId;
    private String paymentMethod;
    private String paymentStatus;
    private int paymentAmount;
    private Timestamp paymentDate;
    private String impUid;
}

