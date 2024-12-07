package com.example.shoesshop.controller;

import com.example.shoesshop.dto.CustomerDTO;
import com.example.shoesshop.entity.Customer;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.request.AccountUpdateRequest;
import com.example.shoesshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping(value = "api/v1/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public ResponseEntity<?> getAllCustomers(Pageable pageable, @RequestParam String search){
        Page<Customer> entities = customerService.getAllCustomers(pageable, search);
        Page<CustomerDTO> dtos = entities.map(new Function<Customer, CustomerDTO>() {
            @Override
            public CustomerDTO apply(Customer customer) {
                CustomerDTO dto = new CustomerDTO();
                dto.setId(customer.getId());
                dto.setUsername(customer.getUsername());
                dto.setAddress(customer.getAddress());
                dto.setBirthday(customer.getBirthday());
                dto.setEmail(customer.getEmail());
                dto.setCreatedDate(customer.getCreatedDate());
                dto.setGender(customer.getGender());
                return dto;
            }
        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    @PostMapping(value = "/register")
    public ResponseEntity<?> createCustomer(@RequestBody AccountRequest accountRequest) throws ParseException {
        customerService.createCustomer(accountRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable(name = "id") int id, @RequestBody AccountUpdateRequest accountUpdateRequest) throws ParseException {
        customerService.updateCustomer(id, accountUpdateRequest);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable(name = "id") int id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }
    @DeleteMapping()
    public void deleteCustomers(@RequestParam(name="ids") List<Integer> ids){
        customerService.deleteCustomers(ids);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable(name = "id") int id){
        Customer customer = customerService.getCustomerById(id);
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setUsername(customer.getUsername());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setAddress(customer.getAddress());
        dto.setBirthday(customer.getBirthday());
        dto.setEmail(customer.getEmail());
        dto.setCreatedDate(customer.getCreatedDate());
        dto.setGender(customer.getGender());
        return new ResponseEntity<CustomerDTO>(dto, HttpStatus.OK);
    }
}
