package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

<<<<<<< HEAD
@EnableFeignClients
=======
@EnableFeignClients(basePackages = "com.example.auth.feign")
>>>>>>> fdad2d4878c203cec567e0d6d9a52902cee09a36
@EnableDiscoveryClient
@SpringBootApplication
public class MallAuthServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(MallAuthServerApplication.class, args);
    }

}
