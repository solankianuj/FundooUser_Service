package com.example.fundoo_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FundooUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundooUserServiceApplication.class, args);
    }

}
