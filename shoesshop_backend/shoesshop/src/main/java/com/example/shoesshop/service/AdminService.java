package com.example.shoesshop.service;

import com.example.shoesshop.entity.Account;
import com.example.shoesshop.entity.Admin;
import com.example.shoesshop.repository.AdminRepository;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.request.AccountUpdateRequest;
import com.example.shoesshop.security.PasswordEncoder;
import com.example.shoesshop.specification.AdminSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Page<Admin> getAllAdmins(Pageable pageable, String search) {
        Specification<Admin> where = null;
        if(!StringUtils.isEmpty(search)){
            AdminSpecification adminSpecification = new AdminSpecification("name","LIKE", search);
            where = Specification.where(adminSpecification);
        }
        return adminRepository.findAll(Specification.where(where), pageable);
    }

    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public void createAdmin(AccountRequest accountRequest) throws ParseException {
        LocalDate createDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday =  LocalDate.parse(accountRequest.getBirthday(), formatter);
        String password = PasswordEncoder.getInstance().encodePassword(accountRequest.getPassword());
        Admin admin = new Admin();
        admin.setUsername(accountRequest.getUsername());
        admin.setPassword(password);
        admin.setFirstName(accountRequest.getFirstName());
        admin.setLastName(accountRequest.getLastName());
        admin.setAddress(accountRequest.getAddress());
        admin.setBirthday(birthday);
        admin.setEmail(accountRequest.getEmail());
        admin.setPhone(accountRequest.getPhone());
        admin.setRole(Account.Role.ADMIN);
        admin.setGender(accountRequest.getGender());
        admin.setCreatedDate(createDate);

        adminRepository.save(admin);
    }

    public void updateAdmin(int id, AccountUpdateRequest accountRequest) throws ParseException {
        Admin admin = adminRepository.getAdminById(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(accountRequest.getPassword() != null){
            String password = PasswordEncoder.getInstance().encodePassword(accountRequest.getPassword());
            admin.setPassword(password);
        }
        if(accountRequest.getFirstName() != null){
            admin.setFirstName(accountRequest.getFirstName());
        }
        if(accountRequest.getLastName() != null){
            admin.setLastName(accountRequest.getLastName());
        }
        if(accountRequest.getAddress() != null) {
            admin.setAddress(accountRequest.getAddress());
        }
        if(accountRequest.getBirthday() != null){
            LocalDate birthday =  LocalDate.parse(accountRequest.getBirthday(), formatter);
            admin.setBirthday(birthday);
        }

        adminRepository.save(admin);
    }

    public Admin getAdminById(int id) {
        return adminRepository.getAdminById(id);
    }

    public void deleteAdmin(int id) {
        adminRepository.deleteById(id);
    }

    public void deleteAdmins(List<Integer> ids) {
        for(Integer id : ids){
            adminRepository.deleteById(id);
        }
    }
}
