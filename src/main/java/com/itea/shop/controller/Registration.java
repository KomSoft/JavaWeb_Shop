package com.itea.shop.controller;

import com.itea.shop.entity.UserRegisteringData;
import com.itea.shop.exception.DataBaseException;
import com.itea.shop.exception.ValidationException;
import com.itea.shop.form.MenuForm;
import com.itea.shop.form.RegistrationForm;
import com.itea.shop.repository.PostgreSQLJDBC;
import com.itea.shop.repository.UserRepository;
import com.itea.shop.service.CheckUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;

public class Registration extends HttpServlet {
    @Serial
    private static final long serialVersionUID = -1210613704116541742L;

    String errorString = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder outString = new StringBuilder();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        CheckUser checkedUser = new CheckUser(request);
        if (checkedUser.isCorrect()) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.UK);
            UserRegisteringData newUser = new UserRegisteringData(checkedUser.getLogin(), checkedUser.getPassword(),
                    checkedUser.getFullName(), checkedUser.getRegion(), checkedUser.getGender(), checkedUser.getComment());
            outString.append(MenuForm.MENU);
            outString.append("<center>login = ").append(checkedUser.getLogin())
                    .append("<br>password = ").append(checkedUser.getPassword())
                    .append("<br>encrypted password = ").append(newUser.getEncryptedPassword())
                    .append("<br>fullName = ").append(checkedUser.getFullName())
                    .append("<br>region = ").append(checkedUser.getRegion())
                    .append("<br>gender = ").append(checkedUser.getGender())
                    .append("<br>comment = ").append(checkedUser.getComment())
                    .append("<br>agreement = ").append(checkedUser.getAgreement())
                    .append("<br>");
//            write to DB, check errors and get result
            PostgreSQLJDBC dataBase = new PostgreSQLJDBC();
            try {
                Connection connection = dataBase.establishConnection();
                UserRepository userRepository = new UserRepository(connection);
                userRepository.saveUser(newUser);
                outString.append(String.format(bundle.getString("registerCompleted"), newUser.getFullName()));
//                     .append("<h2 align='center' style='color:blue;'>Register completed.</h2> Now you can login as </h2>");
            } catch (DataBaseException e) {
                outString.append(String.format(bundle.getString("dataBaseErrorCantSaveUser"), e.getMessage()));
            } catch (ValidationException e) {
                outString.append(String.format(bundle.getString("userIncorrectData"), e.getMessage()));
            }
            out.write(outString.toString());
//      Register completed
        } else {
            errorString = checkedUser.getErrors();
            doGet(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuilder outString  = new StringBuilder(MenuForm.MENU);
        outString.append(RegistrationForm.fill(request, errorString));
        out.write(outString.toString());
    }

}
