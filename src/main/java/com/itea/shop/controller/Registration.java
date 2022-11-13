package com.itea.shop.controller;

import com.itea.shop.entity.UserRegisteringData;
import com.itea.shop.form.MenuForm;
import com.itea.shop.form.RegistrationForm;
import com.itea.shop.utils.CheckUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

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
            UserRegisteringData newUser = new UserRegisteringData(checkedUser.getLogin(), checkedUser.getPassword(),
                    checkedUser.getFullName(), checkedUser.getRegion(), checkedUser.getGender(), checkedUser.getComment());
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
            outString.append("<h2 align='center' style='color:blue;'>Register completed</h2>");
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
