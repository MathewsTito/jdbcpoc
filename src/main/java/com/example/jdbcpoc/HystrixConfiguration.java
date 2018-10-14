package com.example.jdbcpoc;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.strategy.HystrixPlugins;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixConfiguration {

    @Bean
    public HystrixCommandAspect hystrixAspect() {
        return new HystrixCommandAspect();
    }

//    @Bean
//    public CircuitBreakerHystrixEventNotifier hystrixEventNotifier(){
//        CircuitBreakerHystrixEventNotifier notifier = new CircuitBreakerHystrixEventNotifier();
//        HystrixPlugins.getInstance().registerEventNotifier(notifier);
//        return notifier;
//    }
}
