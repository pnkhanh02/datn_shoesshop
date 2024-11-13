package com.example.shoesshop.controller;

import com.example.shoesshop.dto.PaymentMethodDTO;
import com.example.shoesshop.entity.PaymentMethod;
import com.example.shoesshop.request.PaymentMethodRequest;
import com.example.shoesshop.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping(value = "api/v1/paymentMethods")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPaymentMethods(Pageable pageable, @RequestParam String search){
        Page<PaymentMethod> entitiesPage = paymentMethodService.getAllPaymentMethods(pageable, search);
        Page<PaymentMethodDTO> dtoPage = entitiesPage.map(new Function<PaymentMethod, PaymentMethodDTO>() {
            @Override
            public PaymentMethodDTO apply(PaymentMethod paymentMethod) {
                PaymentMethodDTO dto = new PaymentMethodDTO();
                dto.setId(paymentMethod.getId());
                dto.setName(paymentMethod.getName());
                dto.setDescription_payment(paymentMethod.getDescription_payment());

                return dto;
            }

        });
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping(value = "/full")
    public ResponseEntity<?> getFullPaymentMethods(){
        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethods();
        ArrayList<PaymentMethodDTO> paymentMethodDTOS = new ArrayList<>();
        for (PaymentMethod paymentMethod : paymentMethods){
            PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
            paymentMethodDTO.setId(paymentMethod.getId());
            paymentMethodDTO.setName(paymentMethod.getName());
            paymentMethodDTO.setDescription_payment(paymentMethod.getDescription_payment());
            paymentMethodDTOS.add(paymentMethodDTO);
        }
        return new ResponseEntity<>(paymentMethodDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createPaymentMethod(@RequestBody(required = false) PaymentMethodRequest paymentMethodRequest){
        paymentMethodService.createPaymentMethod(paymentMethodRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable(name = "id") int id, @RequestBody(required = false) PaymentMethodRequest paymentMethodRequest){


        if (paymentMethodRequest.getDescription_payment() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        paymentMethodService.updatePaymentMethod(id, paymentMethodRequest);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPaymentMethodById(@PathVariable(name = "id") int id){
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setId(paymentMethod.getId());
        paymentMethodDTO.setName(paymentMethod.getName());
        paymentMethodDTO.setDescription_payment(paymentMethod.getDescription_payment());
        return new ResponseEntity<PaymentMethodDTO>(paymentMethodDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable(name = "id") int id){
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
        if(paymentMethod == null){
            return new ResponseEntity<String>("No value found", HttpStatus.BAD_REQUEST);
        }
        paymentMethodService.deletePaymentMethod(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }


    @DeleteMapping()
    public ResponseEntity<?> deletePaymentMethods(@RequestParam(name="ids") List<Integer> ids){
        for(int id : ids){
            PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
            if(paymentMethod == null){
                return new ResponseEntity<String>("No value found", HttpStatus.BAD_REQUEST);
            }
        }
        paymentMethodService.deletePaymentMethods(ids);
        return new ResponseEntity<String>("Delete paymentMethods successfull!", HttpStatus.OK);
    }
}
