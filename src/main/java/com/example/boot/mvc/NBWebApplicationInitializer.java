package com.example.boot.mvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public interface NBWebApplicationInitializer {
    void onStartup(ServletContext servletContext) throws ServletException;
}
