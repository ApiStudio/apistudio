package io.egreen.apistudio.bootstrap.config;

import org.glassfish.jersey.server.TracingConfig;

import java.lang.annotation.*;

/**
 * Created by dewmal on 7/19/16.
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MSApp {

//    private

    String name();


    String appId() default "";

    String key() default "";

    String trace() default "OFF";
}
