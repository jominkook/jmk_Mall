package com.jmk.service.order;

import com.jmk.dto.cart.CartDto;
import com.jmk.vo.cart.Cart;
import java.util.List;

public interface OrderService {
    public int saveOrderAndPayment(String memberId, int productId, int quantity, String paymentMethod, String impUid) throws Exception;
    public int saveCartOrderAndPayment(String memberId, List<CartDto> cartList, String paymentMethod, String impUid) throws Exception;
}
