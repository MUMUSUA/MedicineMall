package com.example.mall.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableTransationManagement
@MapperScan("com.example.mall.stock.dao")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MallStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallStockApplication.class, args);
    }

}
