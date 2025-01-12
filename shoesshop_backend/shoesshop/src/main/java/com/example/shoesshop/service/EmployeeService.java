package com.example.shoesshop.service;

import com.example.shoesshop.entity.Account;
import com.example.shoesshop.entity.Employee;
import com.example.shoesshop.repository.EmployeeRepository;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.request.AccountUpdateRequest;
import com.example.shoesshop.security.PasswordEncoder;
import com.example.shoesshop.specification.EmployeeSpecification;
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

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> getAllEmployees(Pageable pageable, String search) {
        Specification<Employee> where = null;
        if(!StringUtils.isEmpty(search)){
            EmployeeSpecification employeeSpecification = new EmployeeSpecification("name","LIKE", search);
            where = Specification.where(employeeSpecification);
        }
        return employeeRepository.findAll(Specification.where(where), pageable);
    }

    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    public void createEmployee(AccountRequest accountRequest) throws ParseException {
        LocalDate createDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday =  LocalDate.parse(accountRequest.getBirthday(), formatter);
        //String password = PasswordEncoder.getInstance().encodePassword(accountRequest.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(accountRequest.getPassword());


        Employee employee = new Employee();
        employee.setUsername(accountRequest.getUsername());
        employee.setPassword(encodedPassword);
        employee.setFirstName(accountRequest.getFirstName());
        employee.setLastName(accountRequest.getLastName());
        employee.setAddress(accountRequest.getAddress());
        employee.setBirthday(birthday);
        employee.setEmail(accountRequest.getEmail());
        employee.setPhone(accountRequest.getPhone());
        employee.setRole(Account.Role.EMPLOYEE);
        employee.setGender(accountRequest.getGender());
        employee.setCreatedDate(createDate);


        employeeRepository.save(employee);
    }

    public void updateEmployee(int id, AccountUpdateRequest accountUpdateRequest) throws ParseException {
        Employee employee = employeeRepository.getEmployeeById(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        if(accountUpdateRequest.getPassword() != null){
//            String password = PasswordEncoder.getInstance().encodePassword(accountUpdateRequest.getPassword());
//            employee.setPassword(password);
//        }
        if(accountUpdateRequest.getFirstName() != null){
            employee.setFirstName(accountUpdateRequest.getFirstName());
        }
        if(accountUpdateRequest.getLastName() != null){
            employee.setLastName(accountUpdateRequest.getLastName());
        }
        if(accountUpdateRequest.getAddress() != null) {
            employee.setAddress(accountUpdateRequest.getAddress());
        }
        if(accountUpdateRequest.getBirthday() != null){
            LocalDate birthday =  LocalDate.parse(accountUpdateRequest.getBirthday(), formatter);
            employee.setBirthday(birthday);
        }
        employeeRepository.save(employee);
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.getEmployeeById(id);
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    public void deleteEmployees(List<Integer> ids) {
        for(Integer id : ids){
            employeeRepository.deleteById(id);
        }
    }
}
