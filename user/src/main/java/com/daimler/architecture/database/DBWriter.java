package com.daimler.architecture.database;

import com.daimler.architecture.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class DBWriter {

    private final Connection connection;

    public DBWriter(Connection conn) {
        this.connection = conn;
    }

    public void createUser(User user) {}

    public void updateUser(User user) {}

    public void addFollower(Long target, Long follower) {}

    public void deleteFollower(Long target, Long follower) {}

    public void close() throws SQLException {
        connection.close();
    }
}
