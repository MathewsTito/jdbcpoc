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
import java.util.List;

public class CloudConfig    {


        @Configuration
        @Profile("cloud")
        static class CloudConfiguration extends AbstractCloudConfig {

            @Bean
            public DataSource dataSource() {
                CloudFactory cloudFactory = new CloudFactory();
                Cloud cloud = cloudFactory.getCloud();
                PooledServiceConnectorConfig.PoolConfig poolConfig = new PooledServiceConnectorConfig.PoolConfig(2, 2, 8000);
                List<String> dataSourceNames = Arrays.asList("TomcatDbcpPooledDataSourceCreator", "TomcatJdbcPooledDataSourceCreator", "HikariCpPooledDataSourceCreator");
                DataSourceConfig dbConfig = new DataSourceConfig(poolConfig, null,dataSourceNames);
//                List<String> dataSourceNames = Arrays.asList("TomcatJdbcPooledDataSourceCreator", "HikariCpPooledDataSourceCreator", "BasicDbcpPooledDataSourceCreator");
//                DataSourceConfig dbConfig = new DataSourceConfig(dataSourceNames);
                DataSource ds = connectionFactory().dataSource("mysql-vakyam", dbConfig);
                try {
                    ds.setLoginTimeout(5);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return ds;
                //String serviceID = cloud.getServiceID();
                //return cloud.getServiceConnector("database", DataSource.class, null);
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
