package com.jmk.controller;

import com.jmk.service.cart.CartService;
import com.jmk.vo.member.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {

    @Autowired
    private CartService cartService;

    //장바구니 카운트 세션 유지
    @ModelAttribute
    public void addCartCountToModel(HttpSession session, Model model) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            int cartCount = cartService.getCartCount(member.getMemberId());
            model.addAttribute("cartCount", cartCount);
        }
    }
}
