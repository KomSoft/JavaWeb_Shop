package com.itea.shop.filter;

import com.itea.shop.controller.BlockedLogin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorizationFilter implements Filter {
    Logger logger = Logger.getLogger(AuthorizationFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.log(Level.INFO, "[>>AuthorizationFilter<<] Init Filter");
   }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String authenticatedUser = (String) httpServletRequest.getSession().getAttribute(BlockedLogin.AUTHENTICATED_USER_KEY);
        if (authenticatedUser == null) {
            logger.log(Level.INFO, "[>>AuthorizationFilter<<] Not Authenticated user called");
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("\\authorizationerror");
            filterChain.doFilter(servletRequest, response);
        } else {
            logger.log(Level.INFO, String.format("[>>AuthorizationFilter<<] user: %s called", authenticatedUser));
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
