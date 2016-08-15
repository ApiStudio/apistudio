package io.egreen.apistudio.bootstrap.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Created by dewmal on 8/15/16.
 */

@Priority(1)
@Provider
public class ApiStudioObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private static final Logger LOGGER = LogManager.getLogger(ApiStudioObjectMapperProvider.class);

    ObjectMapper mapper;

    public ApiStudioObjectMapperProvider() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()));
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        LOGGER.info(type);
        return mapper;
    }
}