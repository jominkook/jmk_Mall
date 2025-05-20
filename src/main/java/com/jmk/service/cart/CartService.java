package com.jmk.service.cart;

import com.jmk.dto.cart.CartDto;

import java.util.List;

public interface CartService {

    public void addCartItem(String memberId, int productId, int cartQuantity) throws Exception;

    public int getCartCount(String memberId) throws Exception;

    public List<CartDto> getCartList(String memberId) throws Exception;

    public void deleteProduct(int cartId) throws Exception;

}
