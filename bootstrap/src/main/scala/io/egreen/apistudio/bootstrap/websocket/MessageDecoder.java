package io.egreen.apistudio.bootstrap.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by dewmal on 8/20/16.
 */
public class MessageDecoder implements Decoder.Text{

    @Override
    public Object decode(String s) throws DecodeException {
        return s;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
