package com.jmk.controller.cart;

import com.jmk.service.cart.CartService;
import com.jmk.vo.member.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "member/cart/add" , method = RequestMethod.POST)
    public String addCartItem(
            @RequestParam("productId") int productId,
            @RequestParam("cartQuantity") int cartQuantity,
            HttpSession session,
            Model model) throws Exception {
        Member member = (Member) session.getAttribute("member");

        System.out.println(member.getMemberId());
        System.out.println(productId);
        System.out.println(cartQuantity);
        cartService.addCartItem(member.getMemberId(), productId, cartQuantity);

        int cartCount = cartService.getCartCount(member.getMemberId());
        model.addAttribute("cartCount", cartCount);
        return "main";
    }
}
