package com.itea.shop.servlets;
import com.itea.shop.controller.BlockedLogin;
import com.itea.shop.form.MenuForm;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SecretServlet extends HttpServlet{

    private static final long serialVersionUID = -1864577434892655937L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuilder outString = new StringBuilder();
        outString.append(MenuForm.getMenu(request.getSession().getAttribute(BlockedLogin.AUTHENTICATED_USER_KEY) != null));
        outString.append("This is a secret text only for authorized users");
        out.write(outString.toString());
    }
}
