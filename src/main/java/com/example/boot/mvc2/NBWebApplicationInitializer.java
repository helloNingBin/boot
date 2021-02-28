package com.example.boot.mvc2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public interface NBWebApplicationInitializer {
    void onStartup(ServletContext servletContext) throws ServletException;
}
