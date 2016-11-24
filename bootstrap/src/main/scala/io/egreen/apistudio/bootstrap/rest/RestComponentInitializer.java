package io.egreen.apistudio.bootstrap.rest;

import io.egreen.apistudio.bootstrap.ApiStudio;
import io.egreen.apistudio.bootstrap.filter.CORSResponseFilter;
import io.egreen.apistudio.bootstrap.provider.ApiStudioObjectMapperProvider;
import io.egreen.apistudio.bootstrap.theme.ThymeleafViewProcessor;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.concurrent.ConcurrentRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dewmal on 8/20/16.
 */
@Singleton
public class RestComponentInitializer {

    private static final Logger LOGGER = LogManager.getLogger(RestComponentInitializer.class);

    private final ResourceConfig resourceConfig = new ResourceConfig();

    public RestComponentInitializer() {
//        resourceConfig = JaxRsAnnotationProcessor.genResourceConfig(ApiStudio.applicationClass.getPackage().getName());

    }

    @PostConstruct
    void init() {
        resourceConfig.packages(true, ApiStudio.applicationClass.getPackage().getName());
        resourceConfig.register(CORSResponseFilter.class);
        resourceConfig.register(JacksonFeature.class);
        resourceConfig.register(ApiStudioObjectMapperProvider.class);
        resourceConfig.property(ServerProperties.MONITORING_STATISTICS_ENABLED, true);

        //Logging feature
        resourceConfig.register(new LoggingFilter());
        // Enable Tracing support.
        resourceConfig.property(ServerProperties.TRACING, ApiStudio.SETTINGS.TRACING);
        // HTML5 Template Engine
        resourceConfig.register(ThymeleafViewProcessor.class);
        resourceConfig.register(MvcFeature.class);

        resourceConfig.packages("org.glassfish.jersey.examples.multipart")
                .register(MultiPartFeature.class);

        initModules();
    }


    /**
     * Initializing Modules in jax.rs modules
     */
    private void initModules() {
        Queue<Class<?>> modules = new ArrayDeque<>();

        modules.add(ApiStudio.applicationClass);

        if (ApiStudio.modules != null && ApiStudio.modules.length > 0)
            modules.addAll(Arrays.asList(ApiStudio.modules));


        if (modules != null && modules.size() > 0) {

            while (!modules.isEmpty()) {
                Class<?> module = modules.remove();
                if (module != null) {
                    resourceConfig.packages(true, module.getPackage().getName());
                }
            }

        }


    }


    public ResourceConfig getResourceManager() {
        return resourceConfig;
    }


}
