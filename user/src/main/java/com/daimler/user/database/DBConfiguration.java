package com.daimler.user.database;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DBConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DBWriter createDBWriter() throws SQLException {
        String url = null;
//        Connection conn = DriverManager.getConnection(url, "", "");
        Connection conn = null;
        return new DBWriter(conn);
    }
}
