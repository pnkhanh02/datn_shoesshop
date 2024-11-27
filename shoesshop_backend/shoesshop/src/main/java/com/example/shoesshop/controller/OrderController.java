package com.example.shoesshop.controller;

import com.example.shoesshop.dto.*;
import com.example.shoesshop.entity.*;
import com.example.shoesshop.request.OrderCustomerRequest;
import com.example.shoesshop.request.OrderRequest;
import com.example.shoesshop.service.*;
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
@RequestMapping(value = "api/v1/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private AccountService customerService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductService productService;

    @GetMapping(value = ("/getAllOrders"))
    public ResponseEntity<?> getAllOrders(Pageable pageable, @RequestParam(required = false) String search){
        Page<Order> entitiesPage = orderService.getAllOrders(pageable, search);
        Page<OrderDTO> dtoPage = entitiesPage.map(new Function<Order, OrderDTO>() {
            @Override
            public OrderDTO apply(Order order) {

                if(order.getOrderStatus() == Order.OrderStatus.ADDED_TO_CARD){
                    OrderDTO dto = new OrderDTO(order.getId(),
                            order.getOrder_date(),
                            order.getOrderStatus(),
                            order.getCustomer().getId());
                    Account customer = order.getCustomer();
                    dto.setCustomer_name(customer.getFirstName()+" "+customer.getFirstName());
                    dto.setPhone(customer.getPhone());

                    return dto;
                }else if(order.getOrderStatus() == Order.OrderStatus.TO_PAY || order.getOrderStatus() == Order.OrderStatus.CANCELED){
                    OrderDTO dto = new OrderDTO(order.getId(),
                            order.getTotal_amount(),
                            order.getOrder_date(),
                            order.getOrderStatus(),
                            order.getCustomer().getId(),
                            order.getPayment_method().getId());
                    return dto;
                }else{
                    if(order.getEmployee() == null){
                        OrderDTO dto = new OrderDTO(order.getId(),
                                order.getTotal_amount(),
                                order.getOrder_date(),
                                order.getOrderStatus(),
                                order.getCustomer().getId(),
                                order.getPayment_method().getId());
                        return dto;
                    }else{
                        OrderDTO dto = new OrderDTO(order.getId(),
                                order.getTotal_amount(),
                                order.getOrder_date(),
                                order.getOrderStatus(),
                                order.getCustomer().getId(),
                                order.getEmployee().getId(),
                                order.getPayment_method().getId());
                        return dto;
                    }

                }

            }

        });
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getOrders(){
        ArrayList<Order> orders = orderService.getAll();
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders){
            if(order.getOrderStatus() == Order.OrderStatus.ADDED_TO_CARD){
                continue;
            }else if(order.getOrderStatus() == Order.OrderStatus.TO_PAY || order.getOrderStatus() == Order.OrderStatus.CANCELED){
                OrderDTO dto = new OrderDTO(order.getId(),
                        order.getTotal_amount(),
                        order.getOrder_date(),
                        order.getOrderStatus(),
                        (order.getCustomer().getFirstName()+order.getCustomer().getLastName()),
                        order.getAddress(),
                        order.getPhone(),
                        order.getPayment_method().getName());
                orderDTOS.add(dto);
            }else{
                if(order.getEmployee() == null){
                    OrderDTO dto = new OrderDTO(order.getId(),
                            order.getTotal_amount(),
                            order.getOrder_date(),
                            order.getOrderStatus(),
                            (order.getCustomer().getFirstName()+order.getCustomer().getLastName()),
                            order.getAddress(),
                            order.getPhone(),
                            order.getPayment_method().getName());
                    orderDTOS.add(dto);
                }else{
                    OrderDTO dto = new OrderDTO(order.getId(),
                            order.getTotal_amount(),
                            order.getOrder_date(),
                            order.getOrderStatus(),
                            (order.getCustomer().getFirstName()+order.getCustomer().getLastName()),
                            (order.getEmployee().getFirstName()+order.getEmployee().getLastName()),
                            order.getAddress(),
                            order.getPhone(),
                            order.getPayment_method().getName());
                    orderDTOS.add(dto);
                }

            }
        }

        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderCustomerRequest orderCustomerRequest){
        orderService.customer_createOder(orderCustomerRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable(name = "id") int id, @RequestBody OrderRequest orderRequest){
        orderService.updateOder(id, orderRequest);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }
    @PutMapping(value = "/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable(name = "id") int id){
        orderService.cancelOrder(id);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }
    @PutMapping(value = "/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable(name = "id") int id, @RequestBody ChangeStatusDTO changeStatusDTO){
        orderService.changeStatus(id, changeStatusDTO);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }

    @GetMapping(value = "/getOrderbyID/{id}")
    public ResponseEntity<?> getOrderbyID(@PathVariable(name = "id") int id){
        Order order = orderService.getOrderById(id);
        List<OrderItem> orderItems= order.getOrderItems();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            OrderItemDTO dto = new OrderItemDTO(orderItem.getId(),
                    orderItem.getSell_price(),
                    orderItem.getSubtotal(),
                    orderItem.getQuantity(),
                    orderItem.getOrder().getId(),
                    orderItem.getProduct_detail_order().getId(),
                    orderItem.getProduct_detail_order().getProduct_detail().getName(),
                    orderItem.getProduct_detail_order().getSize(),
                    orderItem.getProduct_detail_order().getColor(),
                    orderItem.getProduct_detail_order().getImg_url(),
                    orderItem.isFeedbackReceived());
            orderItemDTOS.add(dto);
        }
        return new ResponseEntity<>(orderItemDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getByID/{id}")
    public ResponseEntity<?> getByID(@PathVariable(name = "id") int id){
        Order order = orderService.getOrderById(id);
        if(order.getOrderStatus() == Order.OrderStatus.TO_PAY){
            OrderDTO dto = new OrderDTO(order.getId(),
                    order.getTotal_amount(),
                    order.getOrder_date(),
                    order.getOrderStatus(),
                    (order.getCustomer().getFirstName()+order.getCustomer().getLastName()),
                    order.getAddress(),
                    order.getPhone(),
                    order.getPayment_method().getDescription_payment());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }else{
            OrderDTO dto = new OrderDTO(order.getId(),
                    order.getTotal_amount(),
                    order.getOrder_date(),
                    order.getOrderStatus(),
                    (order.getCustomer().getFirstName()+order.getCustomer().getLastName()),
                    (order.getEmployee().getFirstName()+order.getEmployee().getLastName()),
                    order.getAddress(),
                    order.getPhone(),
                    order.getPayment_method().getDescription_payment());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getCartByCustomer/{id}")
    public ResponseEntity<?> getCartByCustomerId(@PathVariable(name = "id") int id){
        Account customer = customerService.getAccountById(id);
        List<Order> orders = orderService.getOrderByCustomer(id);
        int cartId = 0;
        for (Order order: orders){
            if(order.getOrderStatus() == Order.OrderStatus.ADDED_TO_CARD){
                cartId = order.getId();
            }
        }
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrder(cartId);
        ArrayList<CartDTO> cartDTOS = new ArrayList<>();
        for (OrderItem orderItem : orderItems){
            ProductDetail productDetail = orderItem.getProduct_detail_order();
            Product product = productService.getProductById(productDetail.getProduct_detail().getId());
            CartDTO cartDTO = new CartDTO(orderItem.getId(),
                    productDetail.getImg_url(),
                    orderItem.getQuantity(),
                    orderItem.getSubtotal(),
                    product.getPrice(),
                    productDetail.getSize(),
                    product.getName(),
                    product.getTypeProduct().getName(),
                    productDetail.getColor());
            cartDTOS.add(cartDTO);
        }

        return new ResponseEntity<>(cartDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getOrderToPayAndToReceiveAndCompleted")
    public ResponseEntity<?> getOrderToPayAndToReceiveAndCompleted(){
        ArrayList<Order> orders = orderService.getOrderToPayAndToReceiveAndCompleted();
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders){
            System.out.println(order.getOrderStatus());
            OrderDTO dto = new OrderDTO(order.getId(),
                    order.getTotal_amount(),
                    order.getOrder_date(),
                    order.getOrderStatus(),
                    (order.getCustomer().getFirstName()+order.getCustomer().getLastName()),
                    order.getAddress(),
                    order.getPhone(),
                    order.getPayment_method().getName());

            orderDTOS.add(dto);
        }

        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/status/{id}")
    public ResponseEntity<?> getOrderByCustomerId(@PathVariable(name = "id") int id){
        Account customer = customerService.getAccountById(id);
        List<Order> orders = orderService.getOrderByCustomer(id);
        ArrayList<CustomerOrderDTO> customerOrderDTOS = new ArrayList<>();
        for(Order order : orders){
            if(order.getOrderStatus() != Order.OrderStatus.ADDED_TO_CARD) {
                CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO();
                customerOrderDTO.setIdOrder(order.getId());
                customerOrderDTO.setOrderStatus(order.getOrderStatus());
                customerOrderDTO.setOrderDate(order.getOrder_date());
                customerOrderDTO.setPaymentName(order.getPayment_method().getName());
                int totalQuantity = order.getOrderItems().size();
                customerOrderDTO.setTotalQuantity(totalQuantity);
                customerOrderDTO.setTotalAmount(order.getTotal_amount());
                customerOrderDTO.setImg_url(order.getOrderItems().get(0).getProduct_detail_order().getImg_url());
                customerOrderDTO.setColor(order.getOrderItems().get(0).getProduct_detail_order().getColor());
                customerOrderDTO.setSubQuantity(order.getOrderItems().get(0).getQuantity());
                customerOrderDTO.setProductName(order.getOrderItems().get(0).getProduct_detail_order().getProduct_detail().getName());
                customerOrderDTOS.add(customerOrderDTO);
            }
        }
        return new ResponseEntity<>(customerOrderDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "id") int id){
        orderService.deleteOrder(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }


    @DeleteMapping()
    public void deleteOrders(@RequestParam(name="ids") List<Integer> ids){
        orderService.deleteOrders(ids);
    }

    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyRevenues() {
        List<MonthlyRevenueDTO> revenues = orderService.getMonthlyRevenues();
        return ResponseEntity.ok(revenues);
    }

    @GetMapping("/CountOrderMonthly")
    public ResponseEntity<?> getCountOrderMonthly() {
        List<MonthlyOrderCountDTO> orderCountDTOS = orderService.getMonthlyCount();
        return ResponseEntity.ok(orderCountDTOS);
    }
}
