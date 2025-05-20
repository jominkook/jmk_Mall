package com.jmk.service.payment;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {


    public boolean verifyPayment(String impUid) {
        // 1. 아임포트 토큰 발급
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> tokenReq = new HashMap<>();
        tokenReq.put("imp_key", API_KEY);
        tokenReq.put("imp_secret", API_SECRET);

        HttpEntity<Map<String, String>> tokenEntity = new HttpEntity<>(tokenReq, headers);
        ResponseEntity<Map> tokenResp = restTemplate.postForEntity(
                "https://api.iamport.kr/users/getToken", tokenEntity, Map.class);

        String accessToken = (String) ((Map) tokenResp.getBody().get("response")).get("access_token");

        // 2. 결제 정보 조회
        HttpHeaders payHeaders = new HttpHeaders();
        payHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> payEntity = new HttpEntity<>(payHeaders);

        ResponseEntity<Map> payResp = restTemplate.exchange(
                "https://api.iamport.kr/payments/" + impUid,
                HttpMethod.GET,
                payEntity,
                Map.class);

        Map payInfo = (Map) payResp.getBody().get("response");
        String status = (String) payInfo.get("status"); // paid, ready 등

        return "paid".equals(status); // 결제 완료 상태만 true
    }
}