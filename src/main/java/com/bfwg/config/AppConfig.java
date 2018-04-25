package com.bfwg.config;

import com.bfwg.entities.UserEntity;
import com.bfwg.entities.UserEntityPersister;
import com.bfwg.entities.UserEntityPersisterImpl;
import com.bfwg.service.*;
import com.bfwg.service.impl.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {

    @Bean
    public UserEntityPersister userEntityPersister(){
        return new InMemUserPersister();//new UserEntityPersisterImpl();
    }


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenHelper tokenHelper;

    @Bean
    public IdGenerator idGenerator(){
        return new DefaultIdGenerator();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService() {
        return new DefaultUserService(idGenerator(), userEntityPersister(),passwordEncoder(),authenticationManager);
    }

    @Bean
    public AuthenticationService authenticationService(){
        return new AuthenticationService(userService(),tokenHelper,authenticationManager);
    }
    
    @PostConstruct
    public void init(){

        UserEntity admin = new UserEntity();
        admin.id = "adminId";
        admin.email ="admin@itb.com";
        admin.password = passwordEncoder().encode("123");
        admin.username ="admin";
        admin.role = "ROLE_ADMIN";
        admin.timestamp = System.currentTimeMillis();


        UserEntity user = new UserEntity();
        user.id = "userId";
        user.email ="user@itb.com";
        user.password = passwordEncoder().encode("123");
        user.username ="user";
        user.role = "ROLE_USER";
        user.timestamp = System.currentTimeMillis();

        userEntityPersister().save(admin);
        userEntityPersister().save(user);

        
        
    }
}
