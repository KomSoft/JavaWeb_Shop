package com.itea.shop.controller;

import com.itea.shop.entity.AuthorizedUser;
import com.itea.shop.entity.UserRegisteringData;
import com.itea.shop.exception.DataBaseException;
import com.itea.shop.exception.ValidationException;
import com.itea.shop.form.MenuForm;
import com.itea.shop.form.RegistrationForm;
import com.itea.shop.repository.PostgreSQLJDBC;
import com.itea.shop.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.Locale;
import java.util.ResourceBundle;

public class Registration extends HttpServlet {
    @Serial
    private static final long serialVersionUID = -1210613704116541742L;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.UK);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder outString = new StringBuilder();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        AuthorizedUser checkedUser = new AuthorizedUser(request);
        if (checkedUser.isCorrect()) {
            UserRegisteringData newUser = new UserRegisteringData(checkedUser.getLogin(), checkedUser.getPassword(),
                    checkedUser.getFullName(), checkedUser.getRegion(), checkedUser.getGender(), checkedUser.getComment());
            outString.append(MenuForm.getMenu(false));
/*
            outString.append("<center>login = ").append(checkedUser.getLogin())
                    .append("<br>password = ").append(checkedUser.getPassword())
                    .append("<br>encrypted password = ").append(newUser.getEncryptedPassword())
                    .append("<br>fullName = ").append(checkedUser.getFullName())
                    .append("<br>region = ").append(checkedUser.getRegion())
                    .append("<br>gender = ").append(checkedUser.getGender())
                    .append("<br>comment = ").append(checkedUser.getComment())
                    .append("<br>agreement = ").append(checkedUser.getAgreement())
                    .append("<br>");
*/
//            write to DB, check errors and get result
            try {
                UserRepository userRepository = new UserRepository(new PostgreSQLJDBC());
                userRepository.saveUser(newUser);
                userRepository.closeConnection();
                outString.append(String.format(bundle.getString("registerCompleted"), newUser.getFullName()));
//                can redirect to \login there but then we won't see a result
//                response.sendRedirect("\\login");
            } catch (DataBaseException e) {
                outString.append(String.format(bundle.getString("dataBaseError"), e.getMessage()));
            } catch (ValidationException e) {
                outString.append(String.format(bundle.getString("userIncorrectData"), e.getMessage()));
            }
//          Register completed - write result or error
            out.write(outString.toString());
        } else {
            request.setAttribute("errors", checkedUser.getErrors());
            doGet(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuilder outString  = new StringBuilder();
        HttpSession session = request.getSession();
        if (session.getAttribute(BlockedLogin.AUTHENTICATED_USER_KEY) == null) {
            outString.append(MenuForm.getMenu(false));
            outString.append(RegistrationForm.fill(request));
        } else {
            outString.append(MenuForm.getMenu(true));
            outString.append(String.format(bundle.getString("welcomeBack"), session.getAttribute(BlockedLogin.AUTHENTICATED_USER_KEY)));
        }
        out.write(outString.toString());
    }

}
