package com.itea.shop.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyFilter implements Filter {
    private String pattern;
    private String replacePattern;
    Logger logger = Logger.getLogger(MyFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.log(Level.INFO, "Init Filter");
        pattern = filterConfig.getInitParameter("pattern");
        replacePattern = filterConfig.getInitParameter("replacePattern");
        logger.log(Level.INFO, String.format("Got parameters: pattern=%s, replacePattern=%s\n", pattern, replacePattern));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.log(Level.INFO, "doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getParameter("comment") !=  null) {
            request.setAttribute("comment", request.getParameter("comment").replaceAll(pattern, replacePattern));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("Destroy Filter");
    }
}
