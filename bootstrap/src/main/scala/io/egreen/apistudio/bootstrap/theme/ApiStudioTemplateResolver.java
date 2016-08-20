package io.egreen.apistudio.bootstrap.theme;

import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by dewmal on 7/30/16.
 */
public class ApiStudioTemplateResolver extends ServletContextTemplateResolver{
    public ApiStudioTemplateResolver(ServletContext servletContext) {
        super(servletContext);

//        final InputStream inputStream = servletContext.getResourceAsStream(".");



    }



}
