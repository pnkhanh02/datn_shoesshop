package com.example.shoesshop.controller;

import com.example.shoesshop.dto.PaymentDTO;
import com.example.shoesshop.repository.OrderRepository;
import com.example.shoesshop.request.OrderCustomerRequest;
import com.example.shoesshop.response.ResponseObject;
import com.example.shoesshop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/v1/payment")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(@RequestBody OrderCustomerRequest orderCustomerRequest, HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(orderCustomerRequest, request));
    }

    //    @GetMapping("/vn-pay-callback")
//    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
//        String status = request.getParameter("vnp_ResponseCode");
//        if (status.equals("00")) {
//            return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
//        } else {
//            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
//        }
//    }
    @GetMapping("/vn-pay-callback")
    public RedirectView payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String idOrder = request.getParameter("vnp_TxnRef");
        log.info("idOrder: ");
        log.info(idOrder);
        if ("00".equals(status)) {
            return new RedirectView("http://localhost:3000/client/result?status=success");
        } else {
            orderRepository.deleteById(Integer.valueOf(idOrder));
            return new RedirectView("http://localhost:3000/client/result?status=fail");
        }
    }
}
