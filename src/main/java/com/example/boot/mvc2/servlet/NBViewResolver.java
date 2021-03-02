package com.example.boot.mvc2.servlet;

import org.springframework.util.StringUtils;

import java.io.File;

/**
 *
 */
public class NBViewResolver {
    private final String DEFAULT_SUFFIX = ".html";
    private File tempateRootDir;

    public NBViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        tempateRootDir = new File(templateRootPath);
    }
    public NBView resolveViewName(String viewName){
        if(StringUtils.isEmpty(viewName)){
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_SUFFIX) ? viewName : (viewName + DEFAULT_SUFFIX);
        File templateFile = new File((tempateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
        return new NBView(templateFile);
    }
}
