package io.egreen.apistudio.bootstrap.config.processor;

import java.util.List;

/**
 * Created by dewmal on 8/22/16.
 */
public interface AnnotationConfig {


    Class<?> getType();

    void process(List<Class<?>> annotatedClasses);

}
