package com.kubawach.nfs.teaching.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.kubawach.nfs.teaching.config.RootConfiguration;

public class WebApplicationInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer  {
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfiguration.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/api/*"};
    }
}
