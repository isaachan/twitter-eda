package com.daimler.user.database;

import com.daimler.user.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class DBWriter {

    private final Connection connection;

    public DBWriter(Connection conn) {
        this.connection = conn;
    }

    public void createUser(User user) {
        System.out.println("Create user: " + user.getName());
    }

    public void updateUser(User user) {
        System.out.println("Update user: " + user.getName());
    }

    public void addFollower(Long target, Long follower) {
        System.out.println(follower + " follows " + target);
    }

    public void deleteFollower(Long target, Long follower) {
        System.out.println(follower + " unfollows " + target);
    }

    public void close() throws SQLException {
        connection.close();
    }
}
