package com.jmk.service.cart;

import com.jmk.mapper.CartMapper;
import com.jmk.vo.cart.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

public interface CartService {

    public void addCartItem(String memberId, int productId, int cartQuantity) throws Exception;

    public int getCartCount(String memberId) throws Exception;

}
