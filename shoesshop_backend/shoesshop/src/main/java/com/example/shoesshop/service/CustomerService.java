package com.example.shoesshop.service;

import com.example.shoesshop.entity.Account;
import com.example.shoesshop.entity.Customer;
import com.example.shoesshop.entity.Order;
import com.example.shoesshop.exception.CustomException;
import com.example.shoesshop.exception.ErrorResponseEnum;
import com.example.shoesshop.repository.AccountRepository;
import com.example.shoesshop.repository.CustomerRepository;
import com.example.shoesshop.repository.OrderRepository;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.request.AccountUpdateRequest;
import com.example.shoesshop.security.PasswordEncoder;
import com.example.shoesshop.specification.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Page<Customer> getAllCustomers(Pageable pageable, String search) {
        Specification<Customer> where = null;
        if(!StringUtils.isEmpty(search)){
            CustomerSpecification customerSpecification = new CustomerSpecification("name","LIKE", search);
            where = Specification.where(customerSpecification);
        }
        return customerRepository.findAll(Specification.where(where), pageable);
    }

    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public void createCustomer(AccountRequest accountRequest) throws ParseException {
        Account account = accountRepository.findByUsername(accountRequest.getUsername());
        if(account != null){
            throw new CustomException(ErrorResponseEnum.USERNAME_EXISTED);
        }

        Optional<Account> optionalEmail = accountRepository.findByEmail(accountRequest.getEmail());
        if(optionalEmail.isPresent()){
            throw new CustomException(ErrorResponseEnum.EMAIL_EXISTED);
        }

        if(accountRequest.getPassword().length() < 6){
            throw new CustomException(ErrorResponseEnum.PASSWORD_LESSTHAN_6CHARACTERS);
        }

        LocalDate createDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday =  LocalDate.parse(accountRequest.getBirthday(), formatter);
        //String password = PasswordEncoder.getInstance().encodePassword(accountRequest.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(accountRequest.getPassword());

        Customer customer = new Customer();
        customer.setUsername(accountRequest.getUsername());
        customer.setPassword(encodedPassword);
        customer.setFirstName(accountRequest.getFirstName());
        customer.setLastName(accountRequest.getLastName());
        customer.setAddress(accountRequest.getAddress());
        customer.setBirthday(birthday);
        customer.setEmail(accountRequest.getEmail());
        customer.setPhone(accountRequest.getPhone());
        customer.setRole(Account.Role.CUSTOMER);
        customer.setGender(accountRequest.getGender());
        customer.setCreatedDate(createDate);
        LocalDate creating_date = LocalDate.now();
        Order order = new Order(
                creating_date,
                Order.OrderStatus.ADDED_TO_CARD,
                customer
        );
        accountRepository.save(customer);
        orderRepository.save(order);

    }

    public void updateCustomer(int id, AccountUpdateRequest accountUpdateRequest) throws ParseException {
        Customer customer = customerRepository.getCustomerById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        if(accountUpdateRequest.getPassword() != null){
//            String password = PasswordEncoder.getInstance().encodePassword(accountUpdateRequest.getPassword());
//            customer.setPassword(password);
//        }
        if (accountUpdateRequest.getFirstName() == null) {
            throw new CustomException(ErrorResponseEnum.FRISTNAME_NULL);
        }
        if (accountUpdateRequest.getLastName() == null) {
            throw new CustomException(ErrorResponseEnum.LASTNAME_NULL);
        }
        if (accountUpdateRequest.getAddress() == null) {
            throw new CustomException(ErrorResponseEnum.ADDRESS_NULL);
        }
        if (accountUpdateRequest.getBirthday() == null) {
            throw new CustomException(ErrorResponseEnum.BIRTHDAY_NULL);
        }
        customer.setFirstName(accountUpdateRequest.getFirstName());
        customer.setLastName(accountUpdateRequest.getLastName());
        customer.setAddress(accountUpdateRequest.getAddress());

        LocalDate birthday = LocalDate.parse(accountUpdateRequest.getBirthday(), formatter);
        customer.setBirthday(birthday);
        customerRepository.save(customer);
    }

    public Customer getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    public void deleteCustomers(List<Integer> ids) {
        for(Integer id : ids){
            customerRepository.deleteById(id);
        }
    }
}
