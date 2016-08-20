package io.egreen.apistudio.bootstrap.module;

import io.egreen.apistudio.bootstrap.document.SwaggerModule;
import org.glassfish.grizzly.servlet.WebappContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by dewmal on 8/20/16.
 */
@ApplicationScoped
public class ModuleIntergrator {

    @Inject
    private SwaggerModule swaggerModule;


    public void init(final WebappContext webappContext) {

        swaggerModule.init(webappContext);

    }



}
