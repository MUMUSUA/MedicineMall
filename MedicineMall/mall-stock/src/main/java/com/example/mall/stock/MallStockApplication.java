package com.example.mall.stock;

//import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wxl
 */ //@EnableTransationManagement
@MapperScan("com.example.mall.stock.dao")
@EnableDiscoveryClient
//@SpringBootApplication(exclude = {SeataFeignClientAutoConfiguration.class})
@EnableFeignClients
@SpringBootApplication
public class MallStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallStockApplication.class, args);
    }

}
