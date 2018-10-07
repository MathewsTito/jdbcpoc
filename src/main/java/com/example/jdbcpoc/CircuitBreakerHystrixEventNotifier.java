package com.example.jdbcpoc;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;


public class CircuitBreakerHystrixEventNotifier extends HystrixEventNotifier{

    public void markEvent(HystrixEventType eventType, HystrixCommandKey key) {
        System.out.println("**WARNING** Hystrix Event Occured "+ eventType.toString());
    }
}
