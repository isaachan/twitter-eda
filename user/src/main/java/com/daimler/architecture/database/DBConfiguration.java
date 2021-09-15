package com.daimler.architecture.database;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DBConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DBWriter createDBWriter() throws SQLException {
        String url = null;
        Connection conn = DriverManager.getConnection(url, "", "");
        return new DBWriter(conn);
    }
}
