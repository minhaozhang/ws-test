package com.hfi.client;

import com.hfi.wsTest.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class MyClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyClient.class);

    private int num=1;

    private String threadName;

    private Time time;


    @OnOpen
    public void onOpen(Session session) {
        time=new Time();
        LOGGER.info("Connected to endpoint: " + session.getBasicRemote());
        try {
          //  session.getBasicRemote().sendText("Hello");

        } catch (Exception ex) {
        }
    }

    @OnMessage
    public void onMessage(String message) {
        //LOGGER.info("Got message:"+message);
        time.setTimeStamp(System.currentTimeMillis());
        if(num==500){
            LOGGER.info(time.toString());
            LOGGER.info("Got message:"+message);
            num=0;
        }
        num++;
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}