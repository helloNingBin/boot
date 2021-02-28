package com.example.boot.mvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class NBWebApplicationInitializerImpl implements NBWebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("NBWebApplicationInitializerImpl print..............");
    }
}
