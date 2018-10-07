package com.example.jdbcpoc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class MyDAO {

    private DataSource datasource;

    @Autowired
    public MyDAO(DataSource ds){

        datasource = ds;

    }

    public Collection<CodeValue> execute() throws SQLException{

        System.out.println("Inside dao.execute()");

        Collection col = new ArrayList();

        String sql = "SELECT CODE, DESCRIPTION FROM MYTABLE";
        Connection c =datasource.getConnection();
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet rs = p.executeQuery();

        while (rs.next()){
            String code = rs.getString(1);
            String desc = rs.getString(2);
            col.add(new CodeValue(code,desc));
        }

        rs.close();
        p.close();
        c.close();

        System.out.println("Exiting dao.execute()");

        return col;
    }

    public String delay(int delayInSec) throws SQLException {

        long sTime = System.currentTimeMillis();
       // System.out.println("Inside dao.delay()");

        String sql = "{call DELAY(?)}";
        Connection c = datasource.getConnection();
        PreparedStatement p = c.prepareStatement(sql);
        p.setInt(1, delayInSec);
        p.execute();

        p.close();
        c.close();

       // System.out.println("Exiting dao.delay()");

        long eTime = System.currentTimeMillis();

        long elapsed = eTime - sTime;

        return Long.toString(elapsed);
    }

}
