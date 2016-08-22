package io.egreen.apistudio.monitor.ws.decoder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.egreen.apistudio.monitor.model.UnitValue;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * Created by dewmal on 8/20/16.
 */
public class UnitValueDecoder<T> implements Decoder.Text<T> {


    @Override
    public T decode(String s) throws DecodeException {

        try {
            Class<?> decode = UnitValueDecoder.class.getMethod("decode").getReturnType();
            ObjectMapper mapper = new ObjectMapper();
            T t = mapper.readValue(s, (Class<T>) decode);
            return t;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
