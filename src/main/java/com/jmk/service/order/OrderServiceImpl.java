package com.jmk.service.order;

import com.jmk.dto.cart.CartDto;
import com.jmk.dto.order.OrderDto;
import com.jmk.dto.order.OrderProductDto;
import com.jmk.dto.order.OrderSelectDto;
import com.jmk.mapper.CartMapper;
import com.jmk.mapper.OrderMapper;
import com.jmk.mapper.PaymentMapper;
import com.jmk.mapper.ProductMapper;
import com.jmk.dto.payment.Payment;
import com.jmk.vo.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Value("${iamport.api-key}")
    private String iamportApiKey;

    @Value("${iamport.api-secret}")
    private String iamportApiSecret;


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
                //System.out.println("상품 재고 업데이트 성공");
                productMapper.updateProductStock(productId, quantity);
            } catch (Exception e) {
                //System.out.println("상품 재고 업데이트 실패");
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
            //System.out.println("총 결제금액 계산 성공: " + totalPrice);
        } catch (Exception e) {
            //System.out.println("총 결제금액 계산 실패: " + e.getMessage());
            throw e;
        }

        // 2. 주문 저장
        try {
            OrderDto order = new OrderDto();
            order.setMemberId(memberId);
            order.setOrderTotalPrice(totalPrice);
            orderMapper.insertOrder(order);
            memberOrderId = order.getMemberOrderId();
            //System.out.println("주문 저장 성공: " + memberOrderId);
        } catch (Exception e) {
           // System.out.println("주문 저장 실패: " + e.getMessage());
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
            //System.out.println("주문상세/재고 차감 성공");
        } catch (Exception e) {
            //System.out.println("주문상세/재고 차감 실패: " + e.getMessage());
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
            //System.out.println("결제 저장 성공");
        } catch (Exception e) {
            //System.out.println("결제 저장 실패: " + e.getMessage());
            throw e;
        }

        return memberOrderId;
    }

    @Transactional
    @Override
    public List<OrderSelectDto> orderSelect(String memberId, Integer adminCk) throws Exception {
        return orderMapper.orderSelect(memberId, adminCk);
    }

    /*@Override
    public void updateOrderProductStatus(int memberOrderProductId, String memberOrderStatus) throws Exception {
        orderMapper.updateOrderProductStatus(memberOrderProductId,memberOrderStatus);
    }*/

    @Override
    public void updateOrderStatusWithRefund(int memberOrderProductId, String memberOrderStatus) throws Exception {
        orderMapper.updateOrderProductStatus(memberOrderProductId,memberOrderStatus);


        // 2. 주문취소 승인일 때만 환불 및 재고 원복
        if ("주문취소".equals(memberOrderStatus)) {
            // 2-1. 결제정보 조회 (imp_uid 등)
            String impUid;
            try {
                impUid = paymentMapper.selectImpUidByProductId(memberOrderProductId);
            } catch (Exception e) {
                throw new RuntimeException("imp_uid 조회 쿼리 오류: " + e.getMessage(), e);
            }

            // 2-2. 아임포트 환불 API 직접 호출 (IamportClient 없이)
            try {
                // 1. 토큰 발급
                URL tokenUrl = new URL("https://api.iamport.kr/users/getToken");
                HttpURLConnection conn = (HttpURLConnection) tokenUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                org.json.JSONObject tokenBody = new org.json.JSONObject();


                tokenBody.put("imp_key", iamportApiKey);      // 본인 REST API KEY
                tokenBody.put("imp_secret", iamportApiSecret);// 본인 REST API SECRET

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(tokenBody.toString().getBytes());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                br.close();

                String accessToken = new org.json.JSONObject(sb.toString())
                        .getJSONObject("response")
                        .getString("access_token");

                // 2. 환불(취소) 요청
                URL cancelUrl = new URL("https://api.iamport.kr/payments/cancel");
                HttpURLConnection cancelConn = (HttpURLConnection) cancelUrl.openConnection();
                cancelConn.setRequestMethod("POST");
                cancelConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                cancelConn.setRequestProperty("Authorization", accessToken);
                cancelConn.setDoOutput(true);

                String params = "imp_uid=" + impUid + "&reason=관리자 주문취소 승인";
                try (OutputStream os = cancelConn.getOutputStream()) {
                    os.write(params.getBytes());
                }

                BufferedReader cancelBr = new BufferedReader(new InputStreamReader(cancelConn.getInputStream()));
                StringBuilder cancelSb = new StringBuilder();
                while ((line = cancelBr.readLine()) != null) cancelSb.append(line);
                cancelBr.close();

                // 환불 성공 여부 확인
                String result = cancelSb.toString();
                if (result.contains("\"status\":\"cancelled\"")) {
                    try {
                        paymentMapper.updatePaymentStatusByProductId(memberOrderProductId, "결제취소");
                    } catch (Exception e) {
                        throw new RuntimeException("결제상태 변경 쿼리 오류: " + e.getMessage(), e);
                    }
                } else {
                    throw new RuntimeException("아임포트 환불 실패: " + result);
                }
            } catch (Exception e) {
                throw new RuntimeException("아임포트 환불 처리 중 오류: " + e.getMessage(), e);
            }

            // 2-4. 주문상품별 재고 원복
            List<OrderProductDto> orderProducts;
            try {
                orderProducts = orderMapper.selectOrderProductsByProductId(memberOrderProductId);
            } catch (Exception e) {
                throw new RuntimeException("주문상품 정보 조회 쿼리 오류: " + e.getMessage(), e);
            }
            for (OrderProductDto op : orderProducts) {
                try {
                    productMapper.increaseStock(op.getProductId(), op.getQuantity());
                } catch (Exception e) {
                    throw new RuntimeException("재고 원복 쿼리 오류 (productId: " + op.getProductId() + "): " + e.getMessage(), e);
                }
            }
        }
    }

}