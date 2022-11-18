package com.example.mall.sale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.example.mall.sale.dao")
@SpringBootApplication
public class MallSaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallSaleApplication.class, args);
    }

}
