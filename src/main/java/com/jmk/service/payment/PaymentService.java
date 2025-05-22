package com.jmk.service.payment;

public interface PaymentService {
    public boolean verifyPayment(String impUid);
    public String getImpUidByProductId(int memberOrderProductId);
}
