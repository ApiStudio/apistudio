package io.egreen.apistudio.bootstrap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dewmal on 8/11/16.
 */
public class ApiStudioContext {

    private static final List<Class<?>> BEANCLASSES = new ArrayList<>();

    private static ApiStudioContext INSTANCE;


    private ApiStudioContext() {

    }


    public void register(Class<?> aClass) {
        BEANCLASSES.add(aClass);
    }

    public static ApiStudioContext getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ApiStudioContext();
        }
        return INSTANCE;
    }


}
