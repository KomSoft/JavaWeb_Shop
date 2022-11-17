package com.itea.shop.servlets;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GzipServletOutputStream extends ServletOutputStream {

    private GZIPOutputStream gzipOutputStream;

    public GzipServletOutputStream(OutputStream outputStream) throws IOException {
        gzipOutputStream = new GZIPOutputStream(outputStream);
    }

//    всюди замяняємо потік OutputStream  на GZIPOutputStream
    @Override
    public void write(int b) throws IOException {
        gzipOutputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        gzipOutputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        gzipOutputStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        gzipOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        gzipOutputStream.close();
    }

/*
    @Override
    public boolean isReady() {
        return false;
    }
*/
/*
    @Override
    public void setWriteListener(WriteListener arg0) {
    }
*/

}
