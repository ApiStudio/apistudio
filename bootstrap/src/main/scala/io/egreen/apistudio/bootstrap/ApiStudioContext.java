package io.egreen.apistudio.bootstrap;

import java.util.Properties;

/**
 * Created by dewmal on 8/11/16.
 */
public class ApiStudioContext extends Properties {


    private static ApiStudioContext INSTANCE;


    private ApiStudioContext() {
    }


    public static ApiStudioContext getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ApiStudioContext();
        }
        return INSTANCE;
    }


    public enum CONTENT {
        ROOT, HOST, PORT, NAME
    }

}
