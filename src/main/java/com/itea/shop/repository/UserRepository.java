package com.itea.shop.repository;

import com.itea.shop.entity.AuthorizedUser;
import com.itea.shop.entity.UserRegisteringData;
import com.itea.shop.exception.DataBaseException;
import com.itea.shop.exception.ValidationException;

import java.sql.*;

public class UserRepository {
    private PostgreSQLJDBC postgreSQLJDBC;
    private Connection connection;

    public UserRepository(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }
/*
    public UserRepository(Connection connection) {
        this.connection = connection;
    }
*/

    public UserRepository() {
        this.postgreSQLJDBC = null;
        this.connection = null;
    }

    public void getConnection() throws DataBaseException {
        if (connection == null) {
            if (postgreSQLJDBC == null) {
                this.postgreSQLJDBC = new PostgreSQLJDBC();
            }
            postgreSQLJDBC.establishConnection();
            connection = postgreSQLJDBC.getConnection();
        }
    }
/*
    public void getConnection() throws DataBaseException {
        if (connection == null) {
            PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
            postgreSQLJDBC.establishConnection();
            connection = postgreSQLJDBC.getConnection();
        }
    }
*/
    public void closeConnection() throws DataBaseException {
        try {
            if (connection != null) {
                connection.close();
            }
            if (postgreSQLJDBC != null) {
                postgreSQLJDBC.closeConnection();
            }
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] Error closing connection" + e.getMessage());
        }
    }

    public String getFullNameByLoginAndPassword(String login, String password) throws DataBaseException {
        final String GET_FULLNAME_BY_LOGIN_PASSWORD = "SELECT full_name FROM users WHERE login = ? AND password = ?";
        String fullName = null;
        getConnection();
        if (login == null || password == null || login.isBlank() || password.isBlank()) {
            throw new DataBaseException("[UserRepository] Login or password is null or blank");
        }
        try {
            PreparedStatement statement = connection.prepareStatement(GET_FULLNAME_BY_LOGIN_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                fullName = resultSet.getString("full_name");
            }
            resultSet.close();
            statement.close();
//            closeConnection();
            return fullName;
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] SQLException " + e.getMessage());
        }
    }

    public String getFullNameByLogin(String login) throws DataBaseException {
        final String GET_FULLNAME_BY_LOGIN = "SELECT full_name FROM users WHERE login = ?";
        String fullName = null;
        getConnection();
        if (login == null || login.isBlank()) {
            throw new DataBaseException("[UserRepository] Login is null or empty");
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
//            closeConnection();
            return fullName;
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] SQLException " + e.getMessage());
        }
    }

    public UserRegisteringData saveUser(UserRegisteringData user) throws DataBaseException, ValidationException {
        final String INSERT_USER = "INSERT INTO users (login, password, full_name, region, gender, comment) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        if (AuthorizedUser.isUserRegisteringDataCorrect(user)) {
            getConnection();
//        check userRegisteringData
            try {
                String fullName = this.getFullNameByLogin(user.getLogin());
                if (fullName != null) {
                    throw new ValidationException(String.format("[UserRepository] User %s already exists", user.getLogin()));
                }
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
                    throw new DataBaseException("[UserRepository] Unknown reason. User not saved");
                }
            } catch (SQLException e) {
                throw new DataBaseException("[UserRepository] Can't save user. SQLException " + e.getMessage());
            }
        }
        return null;
    }

}
