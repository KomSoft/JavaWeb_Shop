package com.itea.shop.controller;

import com.itea.shop.entity.UserRegisteringData;
import com.itea.shop.exception.DataBaseException;
import com.itea.shop.form.LoginForm;
import com.itea.shop.form.MenuForm;
import com.itea.shop.form.Messages;
import com.itea.shop.repository.PostgreSQLJDBC;
import com.itea.shop.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDateTime;

public class BlockedLogin extends HttpServlet {
    @Serial
    private static final long serialVersionUID = -6198900590472649299L;
    final int TIME_OUT = 10;
    final int MAX_TRY = 3;
    private StringBuilder message = new StringBuilder();
    private int count = 0;
    private LocalDateTime endTime = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostgreSQLJDBC dataBase = new PostgreSQLJDBC();
        boolean isShowForm = true;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (endTime != null) {
            long waitSecond = TIME_OUT - Duration.between(endTime, LocalDateTime.now()).getSeconds();
            message = new StringBuilder(String.format(Messages.WAIT_FOR_TIMEOUT, waitSecond));
            isShowForm = waitSecond <= 0;
            if (isShowForm) {
                count = -1;
                endTime = null;
            }
        }
        if (count < MAX_TRY) {
            isShowForm = true;
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            if (login != null) {
                try {
                    Connection connection = dataBase.establishConnection();
                    UserRepository userRepository = new UserRepository(connection);
                    String fullName = userRepository.getFullNameByLoginAndPassword(login, UserRegisteringData.encryptPassword(password));
                    dataBase.closeConnection();
                    if (fullName != null) {
                        message = new StringBuilder(String.format(Messages.ACCESS_GRANTED, fullName));
//                        message = new StringBuilder(Messages.getAccessGrantedForName(fullName));
                        isShowForm = false;
                    } else {
                        count++;
                        if (count < MAX_TRY) {
                            message = new StringBuilder();
                            if (count > 0) {
                                message.append(String.format(Messages.ACCESS_DENIED, (MAX_TRY - count)));
//                                message.append(Messages.getAccessDenied(MAX_TRY - count));
                            }
                        } else {
                            endTime = LocalDateTime.now();
                            message = new StringBuilder(String.format(Messages.BLOCKED_FOR_TIMEOUT, TIME_OUT));
                            isShowForm = false;
                        }
                    }
                } catch (DataBaseException e) {
                    message = new StringBuilder(String.format(Messages.CANT_CONNECT_DB, e.getMessage()));
                    isShowForm = false;
                }
           }
        }
        if (isShowForm) {
            doGet(request, response);
        }
        out.write(message.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuilder outString  = new StringBuilder(MenuForm.MENU);
        outString.append(LoginForm.LOGIN_FORM);
        out.write(outString.toString());
    }

}
