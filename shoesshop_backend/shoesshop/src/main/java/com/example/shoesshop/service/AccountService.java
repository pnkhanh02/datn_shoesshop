package com.example.shoesshop.service;

import com.example.shoesshop.entity.Account;
import com.example.shoesshop.entity.Customer;
import com.example.shoesshop.repository.AccountRepository;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.security.PasswordEncoder;
import com.example.shoesshop.specification.AccountSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    public Optional<Account> findByEmail(String email){
        return accountRepository.findByEmail(email);
    }

    // check exist of email or username
    public boolean checkExistEmailOrUsername(String email, String username){
        Optional<Account> optionalEmail = accountRepository.findByEmail(email);
        Account existUserName = accountRepository.findByUsername(username);

        if(optionalEmail.isPresent()) return true;
        if(existUserName != null) return true;

        return false;
    }

    // create new account
    public void createAccount(AccountRequest accountRequest) throws ParseException {
        LocalDate createDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday =  LocalDate.parse(accountRequest.getBirthday(), formatter);
        String password = PasswordEncoder.getInstance().encodePassword(accountRequest.getPassword());

        Customer customer = new Customer();
        customer.setUsername(accountRequest.getUsername());
        customer.setPassword(password);
        customer.setFirstName(accountRequest.getFirstName());
        customer.setLastName(accountRequest.getLastName());
        customer.setAddress(accountRequest.getAddress());
        customer.setBirthday(birthday);
        customer.setEmail(accountRequest.getEmail());
        customer.setPhone(accountRequest.getPhone());
        customer.setRole(accountRequest.getRole() != null ? accountRequest.getRole() : Account.Role.CUSTOMER);
        customer.setGender(accountRequest.getGender());
        customer.setCreatedDate(createDate);

        accountRepository.save(customer);
    }

    public Page<Account> getAllAccounts(Pageable pageable, String search) {
        Specification<Account> where = null;
        if(!StringUtils.isEmpty(search)){
            AccountSpecification accountSpecification = new AccountSpecification("name","LIKE", search);
            where = Specification.where(accountSpecification);
        }
        return accountRepository.findAll(Specification.where(where), pageable);
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account getAccountById(int id) {
        return accountRepository.getAccountById(id);
    }

    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    public void deleteAccounts(List<Integer> ids) {
        accountRepository.deleteByIds(ids);
    }

    public Page<Account> getAllAccountsByRole(Pageable pageable, String role) {
        Specification<Account> where = null;
        if(!StringUtils.isEmpty(role)){
            AccountSpecification accountSpecification = new AccountSpecification("Role","=", role);
            where = Specification.where(accountSpecification);
        }
        return accountRepository.findAll(Specification.where(where), pageable);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);

        // Kiểm tra nếu không tìm thấy tài khoản
        if(account == null){
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(accountOptional.get().getRole().toString());

        authorities.add(new SimpleGrantedAuthority(account.getRole().name()));
        return new User(username, account.getPassword(), authorities);

    }

}
