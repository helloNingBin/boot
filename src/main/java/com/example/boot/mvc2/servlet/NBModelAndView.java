package com.example.boot.mvc2.servlet;

import java.util.Map;

/**
 *
 */
public class NBModelAndView {
    private String viewName;
    private Map<String,?> model;

    public NBModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }
    public NBModelAndView(String viewName) {
        this.viewName = viewName;
    }
    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
