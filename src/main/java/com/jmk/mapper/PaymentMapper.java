package com.jmk.mapper;

import com.jmk.dto.payment.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentMapper {
    public void insertPayment(Payment payment);

    public void updatePaymentStatusByProductId(@Param("memberOrderProductId") int memberOrderProductId,
                                               @Param("paymentStatus") String paymentStatus);

    public String selectImpUidByProductId(@Param("memberOrderProductId") int memberOrderProductId);
}
