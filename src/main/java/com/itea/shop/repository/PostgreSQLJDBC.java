package com.itea.shop.repository;

import com.itea.shop.exception.DataBaseException;

import java.sql.*;

public class PostgreSQLJDBC {
//    static final String GET_LOGIN_PASSWORD = "SELECT full_name FROM users WHERE login = ? AND password = ?";
    private Connection connection = null;
    private StringBuilder error = new StringBuilder();

    public Connection establishConnection() throws DataBaseException {
        final String url = "jdbc:postgresql://localhost:5432/testDB";
        final String user = "postgres";
        final String password = "psql";
        if (this.connection != null) {
            return this.connection;
        }
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
//            System.out.println("Opened database successfully");
            return this.connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DataBaseException("No DataBase connected");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                error = new StringBuilder(e.getMessage());
            }
        }
    }

    public String getError() {
        return error.toString();
    }

}
