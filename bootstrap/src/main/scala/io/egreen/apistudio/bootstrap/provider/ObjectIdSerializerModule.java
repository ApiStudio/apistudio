package io.egreen.apistudio.bootstrap.provider;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sun.corba.se.spi.ior.ObjectId;
import com.fasterxml.jackson.core.Version;

import java.io.IOException;

/**
 * Created by dewmal on 8/15/16.
 */
public class ObjectIdSerializerModule  extends SimpleModule {
    public ObjectIdSerializerModule() {
        super("ObjectIdSerializerModule", new Version(0, 1, 0, "alpha"));
        this.addSerializer(ObjectId.class, new ObjectIdSerializer());
    }

    public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

        @Override
        public void serialize(ObjectId value, JsonGenerator jgen
                , SerializerProvider provider)
                throws IOException, JsonProcessingException {
            jgen.writeString(value.toString());
        }
    }
}
