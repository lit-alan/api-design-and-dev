package com.sd4.L11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
@EnableWebSecurity
public class Authentication_And_Authorization_With_2FA {

    public static void main(String[] args) {
        SpringApplication.run(Authentication_And_Authorization_With_2FA.class, args);
    }

}
