package com.itea.shop.filter;

import com.itea.shop.servlets.GzipServletResponseWrapper;

import java.io.IOException;

import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GzipVtFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (acceptGzipEncoding(request)) {
			response.addHeader("Content-Encoding", "gzip");
			GzipServletResponseWrapper gzipServletResponseWrapper = new GzipServletResponseWrapper(response);
			filterChain.doFilter(servletRequest, gzipServletResponseWrapper);
			gzipServletResponseWrapper.close();
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {
	}

	private boolean acceptGzipEncoding(HttpServletRequest request) {
		String acceptEncoding = request.getHeader("Accept-Encoding");
		return acceptEncoding != null && acceptEncoding.indexOf("gzip") != -1;
	}

}
