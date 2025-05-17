package com.jmk.mapper;

import com.jmk.vo.cart.Cart;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    public Cart findCartItem(@Param("memberId") String memberId, @Param("productId") int productId);
    public int insertCartItem(@Param("memberId") String memberId, @Param("productId") int productId, @Param("cartQuantity") int cartQuantity);
    public int updateCartQuantity(@Param("memberId") String memberId, @Param("productId") int productId, @Param("cartQuantity") int cartQuantity);
    public int getCartCount(@Param("memberId") String memberId); // 장바구니 개수 조회
}
