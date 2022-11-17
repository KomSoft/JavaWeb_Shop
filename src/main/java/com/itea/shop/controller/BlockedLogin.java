package com.itea.shop.controller;

import com.itea.shop.entity.UserRegisteringData;
import com.itea.shop.exception.DataBaseException;
import com.itea.shop.form.LoginForm;
import com.itea.shop.form.MenuForm;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class BlockedLogin extends HttpServlet {
    @Serial
    private static final long serialVersionUID = -6198900590472649299L;
    public static final String AUTHENTICATED_USER_KEY = "Authenticated-User";
    final int TIME_OUT = 10;
    final int MAX_TRY = 3;
    private int count = 0;
    private LocalDateTime endTime = null;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.UK);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isShowForm = true;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuilder message = new StringBuilder();
        if (endTime != null) {
            long waitSecond = TIME_OUT - Duration.between(endTime, LocalDateTime.now()).getSeconds();
            isShowForm = waitSecond <= 0;
            if (isShowForm) {
                count = -1;
                endTime = null;
            } else {
                message.append(String.format(bundle.getString("waitForTimeout"), waitSecond));
            }
        }
        if (count < MAX_TRY) {
            isShowForm = true;
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            if (login != null && !login.isBlank()) {
                try {
                    UserRepository userRepository = new UserRepository(new PostgreSQLJDBC());
                    String fullName = userRepository.getFullNameByLoginAndPassword(login, UserRegisteringData.encryptPassword(password));
                    userRepository.closeConnection();
                    if (fullName != null) {
                        message.append(MenuForm.getMenu(true));
                        message.append(String.format(bundle.getString("accessGranted"), fullName));
                        request.getSession().setAttribute(AUTHENTICATED_USER_KEY, fullName);
                        isShowForm = false;
                    } else {
                        count++;
                        if (count < MAX_TRY) {
                            if (count > 0) {
                                message.append(String.format(bundle.getString("accessDenied"), (MAX_TRY - count)));
                            }
                        } else {
                            endTime = LocalDateTime.now();
                            message.append(String.format(bundle.getString("blockedForTimeout"), TIME_OUT));
                            isShowForm = false;
                        }
                    }
                } catch (DataBaseException e) {
                    message.append(String.format(bundle.getString("dataBaseError"), e.getMessage()));
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
//      If exists Logout_Key parameter - close session (logout) and start new
        if (request.getParameter(MenuForm.LOGOUT_KEY) != null) {
            session.invalidate();
            session = request.getSession(true);
        }
        StringBuilder outString = new StringBuilder();
//      if user is authenticated - show Welcome else show login form
        if (session.getAttribute(AUTHENTICATED_USER_KEY) == null) {
            outString.append(MenuForm.getMenu(false));
            outString.append(LoginForm.LOGIN_FORM);
        } else {
            outString.append(MenuForm.getMenu(true));
            outString.append(String.format(bundle.getString("welcomeBack"), session.getAttribute(AUTHENTICATED_USER_KEY)));
        }
        out.write(outString.toString());
    }

}
