package lk.egreen.apistudio.bootstrap.ext;

import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by dewmal on 7/17/16.
 */
public class SwaggerBootstrap extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost(config.getInitParameter("host"));
        beanConfig.setBasePath(config.getInitParameter("path"));
        beanConfig.setResourcePackage(config.getInitParameter("res"));
        beanConfig.setScan(true);
    }
}
