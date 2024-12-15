package com.example.shoesshop.service;

import com.example.shoesshop.entity.ChatHistory;
import com.example.shoesshop.entity.Customer;
import com.example.shoesshop.repository.ChatHistoryRepository;
import com.example.shoesshop.request.ChatHistoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class ChatbotService {
    @Autowired
    private ChatHistoryRepository chatHistoryRepository;

    @Autowired
    private CustomerService customerService;

    public void createChatHistory(ChatHistoryRequest chatHistoryRequest){
        LocalDate createDate = chatHistoryRequest.getCreateDate();
        Customer customer = customerService.getCustomerById(chatHistoryRequest.getCustomerId());
        ChatHistory chatHistory = new ChatHistory();

        chatHistory.setMessage(chatHistoryRequest.getMessage());
        chatHistory.setResponse(chatHistoryRequest.getResponse());
        chatHistory.setCreateDate(createDate);
        chatHistory.setCustomer(customer);
        chatHistoryRepository.save(chatHistory);
    }

    public List<ChatHistory> findAllByCustomerId(int customerId){
        return chatHistoryRepository.findAllByCustomerId(customerId);
    }
}
