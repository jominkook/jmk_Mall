package com.jmk.controller.cart;

import com.jmk.service.cart.CartService;
import com.jmk.vo.cart.Cart;
import com.jmk.vo.member.Member;
import com.jmk.vo.product.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

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
        
        //장바구니 세션 유지 -> 하드코딩
        /*if (member != null) {
            int cartCount = cartService.getCartCount(member.getMemberId());
            model.addAttribute("cartCount", cartCount);
        }*/

        int cartCount = cartService.getCartCount(member.getMemberId());
        model.addAttribute("cartCount", cartCount);
        return "redirect:/main";
    }

    @RequestMapping(value = "/member/cart/list" ,method = RequestMethod.GET)
    public String cartSelect(HttpSession session, Model model) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/login"; // 로그인 안 했으면 로그인 페이지로
        }
        List<Cart> cartList = cartService.getCartList(member.getMemberId());
        model.addAttribute("cartList", cartList);
        model.addAttribute("contentPage", "cart/cartList.jsp");
        return "main";
    }

    @RequestMapping(value = "/member/cart/delete" ,method = RequestMethod.POST)
    public String cartDelete(@RequestParam("cartId") int cartId) throws Exception {
        cartService.deleteProduct(cartId);
        return "redirect:/member/cart/list";
    }
}
