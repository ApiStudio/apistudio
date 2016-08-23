package io.egreen.apistudio.bootstrap.module;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Created by dewmal on 8/10/16.
 */
public class ModulRegistar extends AbstractProcessor {

    private static final Logger LOGGER = LogManager.getLogger(ModulRegistar.class);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // Get all classes that has the annotation
        Set<? extends Element> classElements = roundEnv.getElementsAnnotatedWith(Module.class);

        // For each class that has the annotation
        for (final Element classElement : classElements) {
            LOGGER.info(classElement);
        }
        return false;
    }
}
