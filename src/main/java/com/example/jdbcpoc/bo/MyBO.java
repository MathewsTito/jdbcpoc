package com.example.jdbcpoc.bo;

import com.example.jdbcpoc.dao.MyDAO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class MyBO {


    MyDAO theDAO;

    @Autowired
    public MyBO(MyDAO dao){
        theDAO = dao;
    }

    public Object execute() throws SQLException{
        return theDAO.execute();
    }

    @HystrixCommand(fallbackMethod = "secondaryCall",threadPoolKey = "primaryPool",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "false")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "2"),
                    @HystrixProperty(name = "maximumSize", value = "4"),
                    //@HystrixProperty(name = "maxQueueSize", value = "4"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "4"),
                    @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true")
            }
    )
    
    public String primaryCall(int d1, int d2) throws SQLException {
        System.out.println("LOGGER::In Primary..");
        return theDAO.delay(d1);
    }

    @HystrixCommand(fallbackMethod = "finalCall",threadPoolKey = "secondaryPool",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "false")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "2"),
                    @HystrixProperty(name = "maximumSize", value = "4"),
                    //@HystrixProperty(name = "maxQueueSize", value = "4"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "4"),
                    @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true")
            }
    )
    public String secondaryCall(int d1, int d2, Throwable e) throws SQLException {
        System.out.println("LOGGER::In Secondary.." +e.getMessage());
        return theDAO.delay(d2);
    }


    public String finalCall(int d1, int d2, Throwable e) throws SQLException {
        System.out.println("LOGGER::InfinalCall.."+e.getMessage());
        throw new SQLException("Primary and Secondary Failed");
    }
}
