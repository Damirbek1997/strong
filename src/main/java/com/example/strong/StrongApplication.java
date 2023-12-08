package com.example.strong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class StrongApplication {
    public static void main(String[] args) {
        SpringApplication.run(StrongApplication.class, args);
    }
}
