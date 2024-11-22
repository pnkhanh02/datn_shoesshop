package com.example.shoesshop.controller;

import com.example.shoesshop.dto.OrderItemDTO;
import com.example.shoesshop.entity.OrderItem;
import com.example.shoesshop.request.OrderItemRequest;
import com.example.shoesshop.request.OrderItemUpdateRequest;
import com.example.shoesshop.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping(value = "api/v1/orderItems")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping()
    public ResponseEntity<?> getAllOrderItems(Pageable pageable, @RequestParam String search){
        Page<OrderItem> entitiesPage = orderItemService.getAllOrderItems(pageable, search);
        Page<OrderItemDTO> dtoPage = entitiesPage.map(new Function<OrderItem, OrderItemDTO>() {
            @Override
            public OrderItemDTO apply(OrderItem orderItem) {
                OrderItemDTO dto = new OrderItemDTO(
                        orderItem.getId(),
                        orderItem.getSell_price(),
                        orderItem.getSubtotal(),
                        orderItem.getQuantity(),
                        orderItem.getOrder().getId(),
                        orderItem.getProduct_detail_order().getId());

                return dto;
            }

        });
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrderItem(@RequestBody OrderItemRequest orderItemRequest){
        orderItemService.createOderItem(orderItemRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> updateOrderItem(@PathVariable(name = "id") int id, @RequestBody OrderItemUpdateRequest orderItemUpdateRequest){
        orderItemService.updateOderItem(id, orderItemUpdateRequest);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOrderItemById(@PathVariable(name = "id") int id){
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        OrderItemDTO dto = new OrderItemDTO(
                orderItem.getId(),
                orderItem.getSell_price(),
                orderItem.getSubtotal(),
                orderItem.getQuantity(),
                orderItem.getOrder().getId(),
                orderItem.getProduct_detail_order().getId());
        return new ResponseEntity<OrderItemDTO>(dto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable(name = "id") int id){
        orderItemService.deleteOrderItem(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }


    @DeleteMapping()
    public void deleteOrderItems(@RequestParam(name="ids") List<Integer> ids) {
        orderItemService.deleteOrderItems(ids);
    }
}
