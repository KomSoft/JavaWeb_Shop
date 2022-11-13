package com.itea.shop.repository;

import com.itea.shop.entity.UserRegisteringData;
import com.itea.shop.exception.DataBaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository {
    private Connection connection;

    Logger logger = Logger.getLogger(UserRepository.class.getName());

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public UserRepository() {
        this.connection = null;
    }

    public void checkConnection() throws DataBaseException {
        if (connection == null) {
            logger.log(Level.WARNING, "[UserRepository] No DataBase connected");
            throw new DataBaseException("No DataBase connected");
        }
    }

    public String getFullNameByLoginAndPassword(String login, String password) throws DataBaseException {
        final String GET_FULLNAME_BY_LOGIN_PASSWORD = "SELECT full_name FROM users WHERE login = ? AND password = ?";
        String fullName = null;
        checkConnection();
        if (login == null || password == null || login.isBlank() || password.isBlank()) {
            String error = "[UserRepository] Login or password is null or blank";
            logger.log(Level.WARNING, error);
            throw new DataBaseException(error);
        }
        try {
            PreparedStatement statement = connection.prepareStatement(GET_FULLNAME_BY_LOGIN_PASSWORD);
//            Statement statement = this.connection.createStatement();
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                fullName = resultSet.getString("full_name");
//    System.out.println( "login = " + login + ",     password = " + password);
            }
            resultSet.close();
            statement.close();
            return fullName;
        } catch (SQLException e) {
            String error = "[UserRepository] SQLException " + e.getMessage();
            logger.log(Level.WARNING, error);
            throw new DataBaseException(error);
        }
    }

    public String getFullNameByLogin(String login) throws DataBaseException {
        final String GET_FULLNAME_BY_LOGIN = "SELECT full_name FROM users WHERE login = ?";
        String fullName = null;
        checkConnection();
        if (login == null) {
            String error = "[UserRepository] Login is null";
            logger.log(Level.WARNING, error);
            throw new DataBaseException(error);
        }
        try {
            PreparedStatement statement = connection.prepareStatement(GET_FULLNAME_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               fullName = resultSet.getString("full_name");
            }
            resultSet.close();
            statement.close();
            return fullName;
        } catch (SQLException e) {
            String error = "[UserRepository] SQLException " + e.getMessage();
            logger.log(Level.WARNING, error);
            throw new DataBaseException(error);
        }
    }

    public UserRegisteringData saveUser(UserRegisteringData user) throws DataBaseException {
        final String INSERT_USER = "INSERT INTO users (login, password, fullName, region, gender, comment) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String fullName = null;
        checkConnection();
//        check userRegisteringData
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEncryptedPassword());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getRegion());
            statement.setString(5, user.getGender());
            statement.setString(6, user.getComment());
            int id = statement.executeUpdate();
            statement.close();
            if (id == 1) {
                return user;
            } else {
                String error = "[UserRepository] User not saved";
                logger.log(Level.WARNING, error);
                throw new DataBaseException(error);
            }
        } catch (SQLException e) {
            String error = "[UserRepository] Can't save user. SQLException " + e.getMessage();
            logger.log(Level.WARNING, error);
            throw new DataBaseException(error);
        }

    }

}
