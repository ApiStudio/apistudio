package lk.egreen.msa.studio.sample.helloworld.ws;

import io.egreen.apistudio.bootstrap.websocket.WebsocketConfigurator;
import lk.egreen.msa.studio.sample.helloworld.HelloService;
import lk.egreen.msa.studio.sample.helloworld.data.entity.Order;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by dewmal on 8/19/16.
 */

@ServerEndpoint(value = "/hellows", configurator = WebsocketConfigurator.class)
@Stateless
public class HelloWSApi {


    @Inject
    private HelloService helloService;

    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was
     * successful.
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + " has opened a connection");
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);
        helloService = CDI.current().select(HelloService.class).get();

        Order order = new Order();
        order.setAmount(2345);
        order.setCode(UUID.randomUUID());
//        order.setOrder_id(new Random().nextLong());
        order.setCustomer("User Name");

//        orderDAOController.create(order);


        helloService.sayHello("dewmal " + message);

        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The user closes the connection.
     * <p>
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");
    }
}
