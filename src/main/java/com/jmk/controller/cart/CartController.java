package com.jmk.controller.cart;

import com.jmk.dto.cart.CartDto;
import com.jmk.service.cart.CartService;
import com.jmk.service.order.OrderService;
import com.jmk.service.payment.PaymentService;
import com.jmk.service.payment.PaymentServiceImpl;
import com.jmk.vo.member.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

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
        
        //장바구니 세션 유지 -> 하드코딩X
        /*if (member != null) {
            int cartCount = cartService.getCartCount(member.getMemberId());
            model.addAttribute("cartCount", cartCount);
        }*/

        int cartCount = cartService.getCartCount(member.getMemberId());
        model.addAttribute("cartCount", cartCount);
        return "redirect:/member/cart/list";
    }

    @RequestMapping(value = "/member/cart/list" ,method = RequestMethod.GET)
    public String cartSelect(HttpSession session, Model model) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/login"; // 로그인 안 했으면 로그인 페이지로
        }
        List<CartDto> cartList = cartService.getCartList(member.getMemberId());
        model.addAttribute("cartList", cartList);
        model.addAttribute("contentPage", "cart/cartList.jsp");
        return "main";
    }

    @RequestMapping(value = "/member/cart/delete" ,method = RequestMethod.POST)
    public String cartDelete(@RequestParam("cartId") int cartId) throws Exception {
        cartService.deleteProduct(cartId);
        return "redirect:/member/cart/list";
    }

    @RequestMapping(value = "/member/order/cart", method = RequestMethod.GET)
    public String showCartOrderPage(HttpSession session, Model model) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) return "redirect:/member/login";
        List<CartDto> cartList = cartService.getCartList(member.getMemberId());
        model.addAttribute("cartList", cartList);
        model.addAttribute("contentPage", "order/productOrder.jsp");
        return "main";
    }

    @RequestMapping(value = "/member/order/cart", method = RequestMethod.POST)
    public String orderFromCart(
            HttpSession session,
            @RequestParam String imp_uid,
            @RequestParam String paymentMethod,
            Model model) throws Exception {

        Member member = (Member) session.getAttribute("member");
        if (member == null) return "redirect:/member/login";
        List<CartDto> cartList = cartService.getCartList(member.getMemberId());

        // 1. 아임포트 REST API로 결제 검증 (금액, 결제상태 등)
        boolean isValid = paymentService.verifyPayment(imp_uid);

        if (!isValid) {
            model.addAttribute("msg", "결제 검증에 실패했습니다.");
            model.addAttribute("contentPage", "order/orderFailed.jsp");
            return "main";
        }

        int orderId = orderService.saveCartOrderAndPayment(member.getMemberId(), cartList, paymentMethod, imp_uid);

        model.addAttribute("orderId", orderId);
        model.addAttribute("contentPage", "order/orderSuccess.jsp");
        return "main";
    }
}
