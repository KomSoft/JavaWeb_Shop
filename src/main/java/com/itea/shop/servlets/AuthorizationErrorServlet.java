package com.itea.shop.servlets;
import com.itea.shop.controller.BlockedLogin;
import com.itea.shop.form.MenuForm;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthorizationErrorServlet extends HttpServlet{

    private static final long serialVersionUID = -1864577434892655937L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuilder outString = new StringBuilder();
        outString.append(MenuForm.getMenu(request.getParameter(BlockedLogin.AUTHENTICATED_USER_KEY) != null));
        outString.append("Error 403. Access forbidden.<br>");
        outString.append("You aren't authorized for this page");
        out.write(outString.toString());
    }
}
