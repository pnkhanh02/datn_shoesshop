package com.example.shoesshop.config;


import com.example.shoesshop.service.AccountService;
import com.example.shoesshop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
//@EnableWebSecurity
public class WebSecurityConfiguration {

    /*@Autowired
    private AccountService accountService;


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests()
                //
                .requestMatchers("/api/v1/customers/register").permitAll()
                .requestMatchers("/api/v1/orderItems/delete/{id}").hasAnyAuthority("CUSTOMER")
                .requestMatchers("/api/v1/orderItems/create").hasAnyAuthority("CUSTOMER")
                .requestMatchers("/api/v1/orders/getCartByCustomer/{id}").hasAnyAuthority("CUSTOMER")
                .requestMatchers("/api/v1/orders/status/{id}").hasAnyAuthority("CUSTOMER")
                .requestMatchers("/api/v1/orders/create").hasAnyAuthority("CUSTOMER")
                .requestMatchers("/api/v1/orders/changeStatus/{id}").hasAnyAuthority("EMPLOYEE","ADMIN")
                .requestMatchers("/api/v1/orders/getAll").hasAnyAuthority("ADMIN","EMPLOYEE")

                .requestMatchers("/api/v1/orders/getOrderToPayAndToReceive").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("/api/v1/orders/monthly").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/orders/CountOrderMonthly").hasAnyAuthority("ADMIN")


                .requestMatchers("/api/v1/orders/getOrderbyID/{id}").hasAnyAuthority("ADMIN", "CUSTOMER")
                .requestMatchers("/api/v1/orders/getByID/{id}").hasAnyAuthority("ADMIN", "CUSTOMER")
                .requestMatchers("/api/v1/orders/cancel/{id}").hasAnyAuthority("CUSTOMER")
                .requestMatchers("/api/v1/login").permitAll()
                .requestMatchers("/api/v1/accounts/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/accounts").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/employees/create").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/paymentMethods/all").permitAll()
                .requestMatchers("/api/v1/paymentMethods/create").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/paymentMethods/update/{id}").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/paymentMethods/delete").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/productTypes/full").permitAll()
                .requestMatchers("/api/v1/productTypes/create").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/productTypes/update/{id}").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/productTypes/delete/{id}").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/products/full").permitAll()
                .requestMatchers("/api/v1/products/update/{id}").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/v1/products/create").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/v1/products/productDetail/{id}").permitAll()
                .requestMatchers("/api/v1/productDetails/update/{id}").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/v1/productDetails/create/{id}").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/v1/productDetails/delete/{id}").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/v1/sales").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/sales/create").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/sales/update/{id}").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/sales/delete/{id}").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/products/filter").permitAll()
                .requestMatchers("/api/v1/products/allsize").permitAll()
                .requestMatchers("/api/v1/products/allcolor").permitAll()
                .requestMatchers("/api/v1/products/all").permitAll()
                .requestMatchers("/api/v1/sales").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/sales/**").hasAnyAuthority("ADMIN")
                //



                .requestMatchers("/api/v1/products/{id}").permitAll()
                .requestMatchers("/api/v1/products/type/{id}").permitAll()
                .requestMatchers("/api/v1/feedbacks/**").hasAnyAuthority("ADMIN", "CUSTOMER")
                .requestMatchers("/api/v1/feedbacks/customer").hasAnyAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customers").permitAll()
                .requestMatchers("/api/v1/customers/**").permitAll()
//                .requestMatchers("/api/v1/paymentMethods/**").permitAll()
                .requestMatchers("/api/v1/customers/update/{id}").permitAll()
                .requestMatchers("/api/v1/employees/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/employees/update/{id}").hasAnyAuthority("EMPLOYEE")

                .requestMatchers("/api/v1/admins").hasAnyAuthority("ADMIN")
//                .requestMatchers("/api/v1/orders/checkCart/{id}").hasAnyAuthority("CUSTOMER")
//                .requestMatchers("/api/v1/orders/createCart").hasAnyAuthority("CUSTOMER")
                .requestMatchers("/api/v1/orders/update").hasAnyAuthority("EMPLOYEE", "CUSTOMER")
//                .requestMatchers("api/v1/orderItems/**").hasAnyAuthority("ADMIN", "CUSTOMER")
//                .requestMatchers("/api/v1/orderItems/update/{id}").hasAnyAuthority("CUSTOMER")
//                .requestMatchers("api/v1/productDetails/**").hasAnyAuthority("ADMIN", "EMPLOYEE")

                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }*/
}
