package com.example.shoesshop.service;

import com.example.shoesshop.entity.*;
import com.example.shoesshop.repository.*;
import com.example.shoesshop.request.OrderItemRequest;
import com.example.shoesshop.request.OrderItemUpdateRequest;
import com.example.shoesshop.specification.OrderItemSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Page<OrderItem> getAllOrderItems(Pageable pageable, String search) {
        Specification<OrderItem> where = null;
        if(!StringUtils.isEmpty(search)){
            OrderItemSpecification searchSpecification = new OrderItemSpecification("name","LIKE", search);
            where = Specification.where(searchSpecification);
        }
        return orderItemRepository.findAll(Specification.where(where), pageable);
    }

    public void createOderItem(OrderItemRequest orderItemRequest) {
        Customer customer = customerRepository.getCustomerById(orderItemRequest.getCustomer_id());
        ArrayList<Order> orderArrayList = orderRepository.findByCustomer(customer);
        int order_id;
        order_id = 0;
        for(Order order : orderArrayList){
            if(order.getOderStatus() == Order.OderStatus.ADDED_TO_CARD){
                order_id = order.getId();
            }
        }
        ProductDetail productDetail = productDetailRepository.getDetailById(orderItemRequest.getProduct_detail_id());
        Product product = productDetail.getProduct_detail();
        Order order = orderRepository.getOrderById(order_id);
        List<OrderItem> orderItems = order.getOrderItems();
        boolean check = false;
        for(OrderItem orderItem : orderItems){
            if(orderItem.getProduct_detail_order().getId() == orderItemRequest.getProduct_detail_id()){
                orderItem.setQuantity(orderItem.getQuantity() + orderItemRequest.getQuantity());
                float subtotal = orderItemRequest.getQuantity() * product.getPrice();
                orderItem.setSubtotal(subtotal +orderItem.getSubtotal());
                orderItemRepository.save(orderItem);
                order.setTotal_amount(order.getTotal_amount() + subtotal);
                check = true;
                break;
            }
        }
        if(!check){
            float subtotal = orderItemRequest.getQuantity() * product.getPrice();
            OrderItem orderItem = new OrderItem(
                    product.getPrice(),
                    subtotal,
                    orderItemRequest.getQuantity(),
                    order,
                    productDetail
            );
            orderItemRepository.save(orderItem);
            order.setTotal_amount(order.getTotal_amount() + subtotal);
        }

    }

    public void updateOderItem(int id, OrderItemUpdateRequest orderItemUpdateRequest) {
        OrderItem orderItem = orderItemRepository.getOrderItemById(id);
        Order order = orderItem.getOrder();
        ProductDetail productDetail = orderItem.getProduct_detail_order();
        if (order.getOderStatus() == Order.OderStatus.TO_PAY){
            productDetail.setQuantity(productDetail.getQuantity() - orderItem.getQuantity());
            productDetail.setQuantity(productDetail.getQuantity()+ orderItemUpdateRequest.getQuantity());
        }
        order.setTotal_amount(order.getTotal_amount()-orderItem.getSubtotal());
        orderItem.setQuantity(orderItemUpdateRequest.getQuantity());
        Product product = productDetail.getProduct_detail();
        float subtotal = orderItemUpdateRequest.getQuantity()*product.getPrice();
        orderItem.setSubtotal(subtotal);
        order.setTotal_amount(order.getTotal_amount()+orderItem.getSubtotal());
        orderItem.setSell_price(product.getPrice());
        orderItemRepository.save(orderItem);
    }

    public void changeCartToOrder(int id_oder, List<Integer> id_oder_items) {
        Order order = orderRepository.getOrderById(id_oder);
        float total_amount = 0;
        for (Integer id_oder_item : id_oder_items){
            OrderItem orderItem = orderItemRepository.getOrderItemById(id_oder_item);
            total_amount = total_amount + orderItem.getSubtotal();
            orderItem.setOrder(order);
        }
        order.setTotal_amount(total_amount);
    }

    public OrderItem getOrderItemById(int id) {
        return orderItemRepository.getOrderItemById(id);
    }

    public List<OrderItem> getOrderItemByOrder(int id) {
        Order order = orderRepository.getOrderById(id);
        return orderItemRepository.getOrderItemByOrder(order);
    }

    public void deleteOrderItem(int id) {
        orderItemRepository.deleteById(id);
    }

    public void deleteOrderItems(List<Integer> ids) {
        orderItemRepository.deleteByIds(ids);
    }
}
