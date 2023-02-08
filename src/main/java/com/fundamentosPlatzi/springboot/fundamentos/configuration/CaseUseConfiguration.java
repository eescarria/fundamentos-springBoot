package com.fundamentosPlatzi.springboot.fundamentos.configuration;

import com.fundamentosPlatzi.springboot.fundamentos.caseuse.GetUserImplement;
import com.fundamentosPlatzi.springboot.fundamentos.caseuse.GetUser;
import com.fundamentosPlatzi.springboot.fundamentos.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {

    @Bean
    GetUser getUser(UserService userService){
        return new GetUserImplement(userService);
    }
}
