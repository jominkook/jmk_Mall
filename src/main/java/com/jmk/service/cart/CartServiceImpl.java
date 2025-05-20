package com.jmk.service.cart;

import com.jmk.dto.cart.CartDto;
import com.jmk.dto.order.OrderDto;
import com.jmk.dto.order.OrderProductDto;
import com.jmk.dto.payment.Payment;
import com.jmk.mapper.CartMapper;
import com.jmk.mapper.OrderMapper;
import com.jmk.mapper.PaymentMapper;
import com.jmk.mapper.ProductMapper;
import com.jmk.vo.cart.Cart;
import com.jmk.vo.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Transactional
    @Override
    public void addCartItem(String memberId, int productId, int cartQuantity) throws Exception {
        Cart cart = cartMapper.findCartItem(memberId, productId);
        if (cart == null) {
            cartMapper.insertCartItem(memberId, productId, cartQuantity);
        } else {
            cartMapper.updateCartQuantity(memberId, productId, cartQuantity);
        }
    }

    @Transactional
    @Override
    public int getCartCount(String memberId) throws Exception {
        return cartMapper.getCartCount(memberId);
    }

    @Override
    public List<CartDto> getCartList(String memberId) throws Exception {
        return cartMapper.getCartList(memberId);
    }


    @Transactional
    @Override
    public void deleteProduct(int cartId) throws Exception {
        cartMapper.deleteProduct(cartId);
    }
}
