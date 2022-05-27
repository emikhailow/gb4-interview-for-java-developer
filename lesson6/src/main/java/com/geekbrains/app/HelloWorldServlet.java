package com.geekbrains.app;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; chasrset=UTF-8");
        String html = String.format("<html><body><h1>%s</h1></body></html>", "Hello World");
        resp.getWriter().printf(html);
    }
}
