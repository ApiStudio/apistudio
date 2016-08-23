package io.egreen.apistudio.bootstrap.config.processor;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dewmal on 8/22/16.
 */
@Singleton
public class AnnotationFilterService {

    private final List<AnnotationConfig> annotationConfigList = new ArrayList<AnnotationConfig>();


    public void process(final String packageName, final boolean recursive) throws ClassNotFoundException {

        for (AnnotationConfig annotationConfig : annotationConfigList) {
            List<String> namesOfClassesWithAnnotation = new FastClasspathScanner(packageName).scan().getNamesOfClassesWithAnnotation(annotationConfig.getType());

            List<Class<?>> classes = new ArrayList<>();

            for (String className : namesOfClassesWithAnnotation) {
                Class<?> loadClass = getClass().getClassLoader().loadClass(className);
                classes.add(loadClass);
            }
            annotationConfig.process(classes);

        }

    }

    public void process(final String packageName, final boolean recursive, AnnotationConfig annotationConfig) throws ClassNotFoundException {

        List<Class<?>> classes = new ArrayList<>();

        List<String> namesOfClassesWithAnnotation = new FastClasspathScanner(packageName).scan().getNamesOfClassesWithAnnotation(annotationConfig.getType());


        for (String className : namesOfClassesWithAnnotation) {
            Class<?> loadClass = getClass().getClassLoader().loadClass(className);
            classes.add(loadClass);
        }
        annotationConfig.process(classes);
    }

    public void register(AnnotationConfig annotationConfig) {
        annotationConfigList.add(annotationConfig);
    }

}
