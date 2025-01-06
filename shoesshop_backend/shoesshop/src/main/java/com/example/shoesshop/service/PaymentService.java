package com.example.shoesshop.service;

import com.example.shoesshop.config.VNPAYConfig;
import com.example.shoesshop.dto.PaymentDTO;
import com.example.shoesshop.entity.Order;
import com.example.shoesshop.request.OrderCustomerRequest;
import com.example.shoesshop.utils.VNPayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    @Autowired
    private  OrderService orderService;

    private final VNPAYConfig vnPayConfig;
    public PaymentDTO.VNPayResponse createVnPayPayment(OrderCustomerRequest orderCustomerRequest, HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        log.info("requset body: ");
        log.info(request.getQueryString());

        Order newOrder = orderService.customer_createOder(orderCustomerRequest);

        log.info(" " + newOrder.getId());
        vnpParamsMap.put("vnp_TxnRef", String.valueOf(newOrder.getId()));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }
}
