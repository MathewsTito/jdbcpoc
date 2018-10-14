package com.example.jdbcpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableHystrixDashboard
@ComponentScan("com.example")
public class JdbcpocApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcpocApplication.class, args);
	}
}
