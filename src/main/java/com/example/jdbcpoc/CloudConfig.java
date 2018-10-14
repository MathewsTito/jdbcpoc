package com.example.jdbcpoc;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudConfig    {


        @Configuration
        @Profile("cloud")
        static class CloudConfiguration extends AbstractCloudConfig {

            @Bean
            public DataSource dataSource() {
                CloudFactory cloudFactory = new CloudFactory();
                Cloud cloud = cloudFactory.getCloud();

                Map<String, Object> connectionProperties = new HashMap<>();
//              connectionProperties.put("loginTimeout", 5);

                PooledServiceConnectorConfig.PoolConfig poolConfig = new PooledServiceConnectorConfig.PoolConfig(4, 4, 500);

                List<String> dataSourceNames = Arrays.asList("TomcatJdbcPooledDataSourceCreator", "BasicDbcpPooledDataSourceCreator","MysqlDataSourceCreator");

                DataSourceConfig dbConfig = new DataSourceConfig(poolConfig, null,dataSourceNames,connectionProperties);
                DataSource ds = connectionFactory().dataSource("mysql-vakyam", dbConfig);
                try {
                    ds.setLoginTimeout(1);  //value is in seconds
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //DBCP2 uses the maxWaitTime in poolConfig, but TomcatJDBC uses the value set in LoginTimeOut

                return ds;

            }
        }

        @Configuration
        @Profile("default")
        static class LocalConfiguration {

            @Bean
            public DataSource dataSource() {
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setURL("jdbc:mysql://localhost:3306/dev");
                dataSource.setUser("test");
                dataSource.setPassword("password");
                return dataSource;
            }
        }

}
