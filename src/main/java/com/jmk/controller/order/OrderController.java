package com.jmk.controller.order;

import com.jmk.controller.member.MemberController;
import com.jmk.service.cart.CartService;
import com.jmk.service.order.OrderService;
import com.jmk.service.payment.PaymentService;
import com.jmk.service.product.ProductService;
import com.jmk.vo.cart.Cart;
import com.jmk.vo.member.Member;
import com.jmk.vo.product.Product;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/member/order/direct" , method = RequestMethod.GET)
    public String orderDirect(@RequestParam int productId, @RequestParam int quantity, Model model) throws Exception {
        Product product = productService.getProductById(productId);

//        logger.info("상품정보 값 확인하기");
//        logger.info("상품ID :" + productId);
//        logger.info("상품명" + product.getProductName());

        model.addAttribute("product", product);
        model.addAttribute("quantity", quantity);
        model.addAttribute("contentPage", "order/productOrder.jsp");
        return "main";
    }

    // OrderController.java
    @RequestMapping(value = "/member/order/submit" , method = RequestMethod.POST)
    public String orderSubmit(
            @RequestParam String imp_uid,
            @RequestParam int productId,
            @RequestParam int quantity,
            @RequestParam String paymentMethod,
            HttpSession session,
            Model model) throws Exception {

        // 1. 아임포트 REST API로 결제 검증 (금액, 결제상태 등)
        boolean isValid = paymentService.verifyPayment(imp_uid);

        if (!isValid) {
            model.addAttribute("msg", "결제 검증에 실패했습니다.");
            model.addAttribute("contentPage", "order/orderFailed.jsp");
            return "main";
        }

        System.out.println("주문과정 시작");
        Member member = (Member) session.getAttribute("member");
        System.out.println(member.getMemberId());
        int orderId = orderService.saveOrderAndPayment(
                member.getMemberId(), productId, quantity, paymentMethod, imp_uid
        );

        model.addAttribute("orderId", orderId);
        model.addAttribute("contentPage", "order/orderSuccess.jsp");
        return "main";
    }
}
