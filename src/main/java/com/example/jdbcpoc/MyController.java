package com.example.jdbcpoc;

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

    MyDAO theDAO;

    @Autowired
    public MyController(MyDAO dao){
        theDAO = dao;
    }

    @RequestMapping("/execute")
    public Object execute(@RequestParam String s) throws SQLException{
        try {
            return theDAO.execute();
        } catch (Exception e){
            e.printStackTrace();
            return "{error : "+e.getMessage()+"}";
        }
    }

    @RequestMapping("/delay")
    public Object execute(@RequestParam int d) throws SQLException{
        try {
            if (d > 10)
                d = 10;
            String elapsed = theDAO.delay(d);
            return "{elapsed :"+elapsed+"}";
        } catch (Exception e){
            e.printStackTrace();
            return "{error : "+e.getMessage()+"}";
        }
    }
}
