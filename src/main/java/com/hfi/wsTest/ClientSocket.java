package com.hfi.wsTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.hfi.util.StackTraceUtil;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic Echo Client Socket
 */
@org.eclipse.jetty.websocket.api.annotations.WebSocket(maxTextMessageSize = 64 * 1024)
public class ClientSocket {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSocket.class);

    private final CountDownLatch closeLatch;

    private int num=1;

    private String threadName;

    private Time time;

    @SuppressWarnings("unused")
    private Session session;

    public ClientSocket(String threadName) {
        this.closeLatch = new CountDownLatch(1);
        this.threadName=threadName;
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOGGER.info("Connection closed:"+statusCode+reason);
        this.session = null;
        this.closeLatch.countDown();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        LOGGER.info(" Got connect");
        time=new Time();
        this.session = session;
        try {
            /*Future<Void> fut;
            fut = session.getRemote().sendStringByFuture("Hello");
            fut.get(2, TimeUnit.SECONDS);
            fut = session.getRemote().sendStringByFuture("Thanks for the conversation.");
            fut.get(2, TimeUnit.SECONDS);
            session.close(StatusCode.NORMAL, "I'm done");*/
        } catch (Throwable t) {
            LOGGER.error(StackTraceUtil.getStackTrace(t));
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        time.setTimeStamp(System.currentTimeMillis());
        if(num==500){
            LOGGER.info(time.toString());
            LOGGER.info("Got message:"+msg);
            num=0;
        }
        num++;
       // System.out.printf("Got msg: %s%n", msg);
    }
}