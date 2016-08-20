package io.egreen.apistudio.bootstrap.document;

import io.egreen.apistudio.bootstrap.ApiStudio;
import io.egreen.apistudio.bootstrap.ext.SwaggerBootstrap;
import io.egreen.apistudio.bootstrap.module.Module;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by dewmal on 8/20/16.
 */
@ApplicationScoped
@Module(name = "swagger")
public class SwaggerModule {


    public void init(WebappContext webappContext) {
        ServletRegistration swagger = webappContext.addServlet("Swagger", SwaggerBootstrap.class);
        swagger.setInitParameter("host", ApiStudio.host + ":" + ApiStudio.port);
        swagger.setInitParameter("path", ApiStudio.root);
        swagger.setInitParameter("res", ApiStudio.applicationClass.getPackage().getName());
        swagger.setLoadOnStartup(2);
    }
}
