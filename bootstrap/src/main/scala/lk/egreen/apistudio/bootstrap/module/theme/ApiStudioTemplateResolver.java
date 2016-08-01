package lk.egreen.apistudio.bootstrap.module.theme;

import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

/**
 * Created by dewmal on 7/30/16.
 */
public class ApiStudioTemplateResolver extends ServletContextTemplateResolver{
    public ApiStudioTemplateResolver(ServletContext servletContext) {
        super(servletContext);
    }



}
