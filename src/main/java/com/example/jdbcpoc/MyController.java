package com.example.jdbcpoc;

import com.example.jdbcpoc.bo.MyBO;
import com.example.jdbcpoc.dao.CodeValue;
import com.example.jdbcpoc.dao.MyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Collection;

@RestController
public class MyController {

    MyBO theBO;

    @Autowired
    public MyController(MyBO bo){
        theBO = bo;
    }

    @RequestMapping("/execute")
    public Object execute(@RequestParam String s) throws SQLException{
        try {
            return theBO.execute();
        } catch (Exception e){
            e.printStackTrace();
            return "{error : "+e.getMessage()+"}";
        }
    }

    @RequestMapping("/delay")
    public Object execute(@RequestParam int d1, int d2) throws SQLException{
        try {
            long sTime = System.currentTimeMillis();
            if (d1 > 10)
                d1 = 10;
            if (d2 > 10)
                d2 = 10;
            String elapsed = theBO.primaryCall(d1,d2);
            long eTime = System.currentTimeMillis();
            return "{elapsed :"+(eTime-sTime)+"ms}";
        } catch (Exception e){
            e.printStackTrace();
            return "{error : "+e.getMessage()+"}";
        }
    }
}
