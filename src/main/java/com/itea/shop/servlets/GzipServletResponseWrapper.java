package com.itea.shop.servlets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GzipServletResponseWrapper extends HttpServletResponseWrapper {
    private GzipServletOutputStream gzipServletOutputStream;
    private PrintWriter out;

    public GzipServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        System.out.println("[Gzip Wrapper] intercepted output stream (getOutputStream)");
        if (gzipServletOutputStream == null) {
            gzipServletOutputStream = new GzipServletOutputStream(getResponse().getOutputStream());
        }
        return gzipServletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        System.out.println("[Gzip Wrapper] intercepted output stream (getWriter)");
        if (out == null) {
            gzipServletOutputStream = new GzipServletOutputStream(getResponse().getOutputStream());
            out = new PrintWriter(new OutputStreamWriter(gzipServletOutputStream));
        }
        return out;
    }

    public void close() {
       if (out != null) {
            out.close();
        }
       if (gzipServletOutputStream != null) {
           try {
               gzipServletOutputStream.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (gzipServletOutputStream != null) {
            try {
                gzipServletOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.flushBuffer();
    }
}
