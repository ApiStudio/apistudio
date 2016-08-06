package io.egreen.apistudio.bootstrap.processors;

import org.glassfish.jersey.server.ResourceConfig;
import org.reflections.Reflections;

import javax.ws.rs.Path;
import java.util.Set;

/**
 * Created by dewmal on 7/17/16.
 */
public class JaxRsAnnotationProcessor {


    public static ResourceConfig genResourceConfig(String packageName) {
        ResourceConfig resConfig = new ResourceConfig();

        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Path.class);
        resConfig.registerClasses(typesAnnotatedWith);

        return resConfig;
    }

}
