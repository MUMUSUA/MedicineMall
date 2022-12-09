package com.example.mall.order;

//import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author wxl
 */

@MapperScan(basePackages ={ "com.example.mall.order.dao"})
@EnableAspectJAutoProxy(exposeProxy = true)     //开启了aspect动态代理模式,对外暴露代理对象
@EnableRedisHttpSession     //开启springsession
@EnableRabbit
@EnableFeignClients(basePackages = "com.example.mall.order.feign")
@EnableDiscoveryClient
@SpringBootApplication
//@SpringBootApplication(exclude = {SeataFeignClientAutoConfiguration.class})
public class MallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderApplication.class, args);
    }

}
