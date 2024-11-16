package com.example.shoesshop.service;

import com.example.shoesshop.entity.*;
import com.example.shoesshop.repository.FeedbackRepository;
import com.example.shoesshop.repository.OrderRepository;
import com.example.shoesshop.request.FeedbackRequest;
import com.example.shoesshop.specification.FeedbackSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    public Page<Feedback> getAllFeedbacks(Pageable pageable, String search) {
        Specification<Feedback> where = null;
        if(!StringUtils.isEmpty(search)){
            FeedbackSpecification feedbackSpecification = new FeedbackSpecification("name","LIKE", search);
            where = Specification.where(feedbackSpecification);
        }
        return feedbackRepository.findAll(Specification.where(where), pageable);
    }

    public void createFeedback(FeedbackRequest feedbackRequest) {
        Customer customer = customerService.getCustomerById(feedbackRequest.getCustomer_id());
        LocalDate creating_date = LocalDate.now();
        System.out.println(feedbackRequest);
        Order order = orderService.getOrderById(feedbackRequest.getOrder_id());
        if(order!=null){
            System.out.println(order);
            Product product = productService.getProductById(feedbackRequest.getProduct_id());
            Feedback feedback = new Feedback(feedbackRequest.getComment(),creating_date, feedbackRequest.getRating(), customer, product);
            feedbackRepository.save(feedback);
            List<OrderItem> orderItemList = order.getOrderItems();
            for(OrderItem orderItem : orderItemList){
                if(orderItem.getProduct_detail_order().getProduct_detail().getId() == feedbackRequest.getProduct_id()){
                    orderItem.setFeedbackReceived(true);
                    System.out.println(orderItem);
                }
            }
            order.setOrderItems(orderItemList);
            orderRepository.save(order);
        }

    }

    public ArrayList<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(int id) {
        return feedbackRepository.getFeedbackById(id);
    }

    public void deleteFeedback(int id) {
        feedbackRepository.deleteById(id);
    }

    public void deleteFeedbacks(List<Integer> ids) {
        feedbackRepository.deleteByIds(ids);
    }
}
