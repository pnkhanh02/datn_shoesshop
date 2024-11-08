package com.example.shoesshop.controller;

import com.example.shoesshop.dto.EmployeeDTO;
import com.example.shoesshop.entity.Employee;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.request.AccountUpdateRequest;
import com.example.shoesshop.service.EmployeeService;
import com.example.shoesshop.specification.EmployeeSpecification;
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
@RequestMapping(value = "api/v1/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<?> getAllEmployees(Pageable pageable, @RequestParam String search){
        Page<Employee> entities = employeeService.getAllEmployees(pageable, search);
        Page<EmployeeDTO> dtos = entities.map(new Function<Employee, EmployeeDTO>() {
            @Override
            public EmployeeDTO apply(Employee employee) {
                EmployeeDTO dto = new EmployeeDTO();
                dto.setId(employee.getId());
                dto.setUsername(employee.getUsername());
                dto.setAddress(employee.getAddress());
                dto.setBirthday(employee.getBirthday());
                dto.setEmail(employee.getEmail());
                dto.setCreatedDate(employee.getCreatedDate());
                dto.setGender(employee.getGender());
                return dto;
            }

        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createEmployee(@RequestBody AccountRequest accountRequest) throws ParseException {
        employeeService.createEmployee(accountRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable(name = "id") int id, @RequestBody AccountUpdateRequest accountUpdateRequest) throws ParseException {
        employeeService.updateEmployee(id, accountUpdateRequest);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name = "id") int id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }
    @DeleteMapping()
    public void deleteEmployees(@RequestParam(name="ids") List<Integer> ids){
        employeeService.deleteEmployees(ids);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable(name = "id") int id){
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setUsername(employee.getUsername());
        dto.setAddress(employee.getAddress());
        dto.setBirthday(employee.getBirthday());
        dto.setEmail(employee.getEmail());
        dto.setCreatedDate(employee.getCreatedDate());
        dto.setGender(employee.getGender());
        return new ResponseEntity<EmployeeDTO>(dto, HttpStatus.OK);
    }
    @GetMapping(value = "/getEmployee/{id}")
    public  ResponseEntity<?>getEmployeeByUsername(@PathVariable(name="id") String username){
        Employee employee= employeeService.getEmployeeByUsername(username);
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setUsername(employee.getUsername());
        dto.setAddress(employee.getAddress());
        dto.setBirthday(employee.getBirthday());
        dto.setEmail(employee.getEmail());
        dto.setCreatedDate(employee.getCreatedDate());
        dto.setGender(employee.getGender());
        return  new ResponseEntity<EmployeeDTO>(dto,HttpStatus.OK);

    }
}
