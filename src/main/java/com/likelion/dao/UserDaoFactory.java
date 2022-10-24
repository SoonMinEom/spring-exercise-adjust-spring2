package com.likelion.dao;

import com.likelion.domain.User;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configurable
public class UserDaoFactory {

    @Bean
    public UserDao awsUserDao() {
        return new UserDao(awsDataSource());
    }

    @Bean
    DataSource awsDataSource() {
        Map<String, String> evn = System.getenv();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl(evn.get("DB_HOST"));
        dataSource.setUsername(evn.get("DB_NAME"));
        dataSource.setPassword(evn.get("DB_PASSWORD"));

        return dataSource;
    }
}
