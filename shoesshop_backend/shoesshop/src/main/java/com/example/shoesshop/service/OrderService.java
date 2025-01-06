package com.example.shoesshop.service;

import com.example.shoesshop.dto.MonthlyOrderCountDTO;
import com.example.shoesshop.dto.MonthlyRevenueDTO;
import com.example.shoesshop.dto.ChangeStatusDTO;
import com.example.shoesshop.entity.*;
import com.example.shoesshop.repository.*;
import com.example.shoesshop.request.OrderCustomerRequest;
import com.example.shoesshop.request.OrderItemForm;
import com.example.shoesshop.request.OrderRequest;
import com.example.shoesshop.specification.OrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private AccountRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private OrderItemService orderItemService;

    public Page<Order> getAllOrders(Pageable pageable, String search) {
        Specification<Order> where = null;
        if(!StringUtils.isEmpty(search)){
            OrderSpecification searchSpecification = new OrderSpecification("name","LIKE", search);
            where = Specification.where(searchSpecification);
        }
        return orderRepository.findAll(Specification.where(where), pageable);
    }

    public ArrayList<Order> getOrderToPayAndToReceiveAndCompleted() {
        ArrayList<Order.OrderStatus> statuses = new ArrayList<>();
        statuses.add(Order.OrderStatus.TO_PAY);
        statuses.add(Order.OrderStatus.TO_RECEIVE);
        statuses.add(Order.OrderStatus.COMPLETED);


        return orderRepository.findByOderStatus(statuses);
//        return repository.findByOderStatus(Order.OderStatus.TO_PAY);
    }

    // tạo đơn hàng
//    @Transactional
    public Order customer_createOder(OrderCustomerRequest orderCustomerRequest) {
        Account customer = customerRepository.getAccountById(orderCustomerRequest.getCustomer_id());
        PaymentMethod paymentMethod = paymentMethodRepository.getPaymentMethodById(orderCustomerRequest.getPayment_method_id());
        LocalDate creating_date = LocalDate.now();
        float total_amout = 0;
        Order order = new Order(
                orderCustomerRequest.getAddress(),
                orderCustomerRequest.getPhone(),
                creating_date,
                Order.OrderStatus.TO_PAY,
                customer,
                paymentMethod
        );
        orderRepository.save(order);
        for (OrderItemForm orderItemForm : orderCustomerRequest.getOrderItemForms()){
            OrderItem orderItem = orderItemRepository.getOrderItemById(orderItemForm.getId());
            orderItem.setOrder(order);
            orderItem.setQuantity(orderItemForm.getQuantity_item());
            orderItem.setSubtotal(orderItem.getSell_price()*orderItem.getQuantity());
            ProductDetail productDetail = productDetailRepository.getDetailById(orderItem.getProduct_detail_order().getId());
            productDetail.setQuantity(productDetail.getQuantity()-orderItem.getQuantity());
            total_amout = total_amout + orderItem.getSubtotal();
            orderItemRepository.save(orderItem);
            productDetailRepository.save(productDetail);

        }
        order.setTotal_amount(total_amout);
        Order newOrder = orderRepository.save(order);
        return newOrder;
    }

    public List<Object[]> getTotalAmountByMonthForCurrentYear(int year) {
        return orderRepository.getTotalAmountByMonthForCurrentYear(year);
    }

    public void createCart(OrderRequest orderRequest) {
        Account customer = customerRepository.getAccountById(orderRequest.getCustomer_id());
        LocalDate creating_date = LocalDate.now();
        Order order = new Order(
                customer.getAddress(),
                "0",
                creating_date,
                Order.OrderStatus.ADDED_TO_CARD,
                customer
        );
        orderRepository.save(order);
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenues(int year) {
        List<Object[]> results = orderRepository.getTotalAmountByMonthForCurrentYear(year);
        List<MonthlyRevenueDTO> revenues = new ArrayList<>();
        for (Object[] result : results) {
//                BigDecimal totalAmount = (BigDecimal) result[0];
//
            BigDecimal totalAmount = new BigDecimal(result[0].toString());
            int month = (int) result[1];
            revenues.add(new MonthlyRevenueDTO(month, totalAmount));
        }
        return revenues;
    }

    public void updateOder(int id, OrderRequest orderRequest) {
        System.out.println(orderRequest.getEmployee_id());
        Employee employee = employeeRepository.getEmployeeById(orderRequest.getEmployee_id());
        Order order = orderRepository.getOrderById(id);
        if(0 != orderRequest.getPayment_method_id()){
            PaymentMethod    paymentMethod = paymentMethodRepository.getPaymentMethodById(orderRequest.getPayment_method_id());
            order.setPayment_method(paymentMethod);
        }
        order.setEmployee(employee);
        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setOrder_date(orderRequest.getOrder_date());
        orderRepository.save(order);

    }

    public Order getOrderById(int id) {
        return orderRepository.getOrderById(id);
    }

    public List<Order> getOrderByCustomer(int id) {
        Account customer = customerRepository.getAccountById(id);
        return orderRepository.getOrderByCustomer(customer);
    }

    public ArrayList<Order> getAll() {
        return orderRepository.findAll();
    }

    public void cancelOrder(int id) {
        Order order = orderRepository.getOrderById(id);
        List<OrderItem> orderItems = order.getOrderItems();
        for(OrderItem orderItem : orderItems) {
            ProductDetail productDetail = productDetailRepository.getDetailById(orderItem.getProduct_detail_order().getId());
            productDetail.setQuantity(productDetail.getQuantity() + orderItem.getQuantity());
            productDetailRepository.save(productDetail);
        }
        order.setOrderStatus(Order.OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    public void changeStatus(int id, ChangeStatusDTO changeStatusDTO) {
        Employee employee = employeeRepository.getEmployeeById(changeStatusDTO.getEmployee_id());
        Order order = orderRepository.getOrderById(id);

        if (employee != null) {
            order.setOrderStatus(changeStatusDTO.getOrderStatus());
            order.setEmployee(employee);
            orderRepository.save(order);
        }
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    public void deleteOrders(List<Integer> ids) {
        orderRepository.deleteByIds(ids);
    }

    public List<MonthlyOrderCountDTO> getOrderCountByMonthForCurrentYear() {
        return null;
    }

    public List<MonthlyOrderCountDTO> getMonthlyCount(int year) {
        List<Object[]> results = orderRepository.getOrderCountByMonthForCurrentYear(year);
        List<MonthlyOrderCountDTO> countDTOS = new ArrayList<>();
        for (Object[] result : results) {
//                BigInteger count = new BigInteger(result[0].toString());
            countDTOS.add(new MonthlyOrderCountDTO(result[0].toString(),result[1].toString()));
        }
        return countDTOS;

    }


}
