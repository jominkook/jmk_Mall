package com.jmk.service.order;

import com.jmk.dto.cart.CartDto;
import com.jmk.dto.order.OrderDto;
import com.jmk.dto.order.OrderProductDto;
import com.jmk.mapper.CartMapper;
import com.jmk.mapper.OrderMapper;
import com.jmk.mapper.PaymentMapper;
import com.jmk.mapper.ProductMapper;
import com.jmk.dto.payment.Payment;
import com.jmk.vo.cart.Cart;
import com.jmk.vo.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartMapper cartMapper;


    @Transactional
    @Override
    public int saveOrderAndPayment(String memberId, int productId, int quantity, String paymentMethod, String impUid) {
        int memberOrderId = 0;

        try {

            Product product = productMapper.selectProductById(productId);

            OrderDto order = new OrderDto();
            order.setMemberId(memberId);
            order.setOrderTotalPrice(product.getProductPrice() * quantity);
            orderMapper.insertOrder(order);
            memberOrderId = order.getMemberOrderId();
            //System.out.println("주문 저장 성공, memberOrderId: " + memberOrderId);

            try {
                OrderProductDto orderProductDto = new OrderProductDto();
                orderProductDto.setMemberOrderId(memberOrderId);
                orderProductDto.setProductId(productId);
                orderProductDto.setQuantity(quantity);
                orderProductDto.setPrice(product.getProductPrice());
                orderMapper.insertOrderProduct(orderProductDto);
                //System.out.println("주문상세 저장 성공");
            } catch (Exception e) {
                //System.out.println("주문상세 저장 실패: " + e.getMessage());
                throw e;
            }

            try {
                Payment payment = new Payment();
                payment.setMemberOrderId(memberOrderId);
                payment.setPaymentMethod(paymentMethod);
                payment.setPaymentStatus("결제완료");
                payment.setPaymentAmount(product.getProductPrice() * quantity);
                payment.setImpUid(impUid);
                paymentMapper.insertPayment(payment);
                //System.out.println("결제 저장 성공");
            } catch (Exception e) {
               // System.out.println("결제 저장 실패: " + e.getMessage());
                throw e;
            }

            try {
                System.out.println("상품 재고 업데이트 성공");
                productMapper.updateProductStock(productId, quantity);
            } catch (Exception e) {
                System.out.println("상품 재고 업데이트 실패");
                throw e;
            }

        } catch (Exception e) {
            //System.out.println("주문 저장 전체 실패: " + e.getMessage());
            throw e;
        }

        return memberOrderId;
    }
    @Transactional
    @Override
    public int saveCartOrderAndPayment(String memberId, List<CartDto> cartList, String paymentMethod, String impUid) throws Exception {
        int memberOrderId = 0;
        int totalPrice = 0;

        // 1. 총 결제금액 계산
        try {
            for (CartDto cart : cartList) {
                Product product = productMapper.selectProductById(cart.getProductId());
                int price = product.getProductPrice();
                int quantity = cart.getCartQuantity();
                totalPrice += price * quantity;
            }
            System.out.println("총 결제금액 계산 성공: " + totalPrice);
        } catch (Exception e) {
            System.out.println("총 결제금액 계산 실패: " + e.getMessage());
            throw e;
        }

        // 2. 주문 저장
        try {
            OrderDto order = new OrderDto();
            order.setMemberId(memberId);
            order.setOrderTotalPrice(totalPrice);
            orderMapper.insertOrder(order);
            memberOrderId = order.getMemberOrderId();
            System.out.println("주문 저장 성공: " + memberOrderId);
        } catch (Exception e) {
            System.out.println("주문 저장 실패: " + e.getMessage());
            throw e;
        }

        // 3. 주문상세/재고 차감
        try {
            for (CartDto cart : cartList) {
                Product product = productMapper.selectProductById(cart.getProductId());
                int price = product.getProductPrice();

                OrderProductDto orderProductDto = new OrderProductDto();
                orderProductDto.setMemberOrderId(memberOrderId);
                orderProductDto.setProductId(cart.getProductId());
                orderProductDto.setQuantity(cart.getCartQuantity());
                orderProductDto.setPrice(price);
                orderMapper.insertOrderProduct(orderProductDto);

                productMapper.updateProductStock(cart.getProductId(), cart.getCartQuantity());
                cartMapper.deleteProduct(cart.getCartId());
            }
            System.out.println("주문상세/재고 차감 성공");
        } catch (Exception e) {
            System.out.println("주문상세/재고 차감 실패: " + e.getMessage());
            throw e;
        }

        // 4. 결제 저장
        try {
            Payment payment = new Payment();
            payment.setMemberOrderId(memberOrderId);
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentStatus("결제완료");
            payment.setPaymentAmount(totalPrice);
            payment.setImpUid(impUid);
            paymentMapper.insertPayment(payment);
            System.out.println("결제 저장 성공");
        } catch (Exception e) {
            System.out.println("결제 저장 실패: " + e.getMessage());
            throw e;
        }

        return memberOrderId;
    }

}