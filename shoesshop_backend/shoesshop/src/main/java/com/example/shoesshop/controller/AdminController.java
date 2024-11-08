package com.example.shoesshop.controller;

import com.example.shoesshop.dto.AdminDTO;
import com.example.shoesshop.entity.Admin;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.request.AccountUpdateRequest;
import com.example.shoesshop.service.AdminService;
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
@RequestMapping(value = "api/v1/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping()
    public ResponseEntity<?> getAllAdmins(Pageable pageable, @RequestParam String search){
        Page<Admin> entities = adminService.getAllAdmins(pageable, search);
        Page<AdminDTO> dtos = entities.map(new Function<Admin, AdminDTO>() {
            @Override
            public AdminDTO apply(Admin admin) {
                AdminDTO dto = new AdminDTO();
                dto.setId(admin.getId());
                dto.setUsername(admin.getUsername());
                dto.setAddress(admin.getAddress());
                dto.setBirthday(admin.getBirthday());
                dto.setEmail(admin.getEmail());
                dto.setCreatedDate(admin.getCreatedDate());
                dto.setGender(admin.getGender());
                return dto;
            }
        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value="/create")
    public ResponseEntity<?> createAdmin(@RequestBody AccountRequest accountRequest) throws ParseException {
        adminService.createAdmin(accountRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable(name = "id") int id, @RequestBody AccountUpdateRequest accountUpdateRequest) throws ParseException {
        adminService.updateAdmin(id, accountUpdateRequest);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable(name = "id") int id){
        adminService.deleteAdmin(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }
    @DeleteMapping()
    public void deleteAdmins(@RequestParam(name="ids") List<Integer> ids){
        adminService.deleteAdmins(ids);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable(name = "id") int id){
        Admin admin = adminService.getAdminById(id);
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setAddress(admin.getAddress());
        dto.setBirthday(admin.getBirthday());
        dto.setEmail(admin.getEmail());
        dto.setCreatedDate(admin.getCreatedDate());
        dto.setGender(admin.getGender());
        return new ResponseEntity<AdminDTO>(dto, HttpStatus.OK);
    }
}
