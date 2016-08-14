package io.egreen.apistudio.bootstrap.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dewmal on 8/11/16.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {

    String name() ;


}
