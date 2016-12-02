package com.hfi.client;

import com.hfi.util.PropertiesUtil;
import com.hfi.util.StackTraceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class MyClientApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyClientApp.class);



    public Session session;

    public void connect()
    {

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        String uri = "ws://192.20.34.144:8081/nbd/ws/bidding/2";
        System.out.println("Connecting to " + uri);
        try {
            session = container.connectToServer(MyClient.class, URI.create(uri));
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public void threadStart() {

          String wsServer= PropertiesUtil.getProperty("ws.server");

           int threadNum=PropertiesUtil.getPropertyInt("thread.num") ;
        int sleepScan= PropertiesUtil.getPropertyInt("sleep.time");

        LOGGER.info("测试的地址为："+wsServer+",共启动"+threadNum+"个线程,启动间隔为"+sleepScan / 1000 + "秒...");
        // 文件扫描启动线程

        try {

            for (int i = 0; i < threadNum; i++) {
                LOGGER.info("正在启动第" + (i+1)+ "个线程");

                Thread connectThread = new Thread() {
                    public void run() {
                        connect();
                    }
                };
                connectThread.setName(String.valueOf(i+1));
                connectThread.start();
                LOGGER.debug("主线程sleep" + sleepScan / 1000 + "秒...");
                Thread.sleep(sleepScan);

            }
        } catch (InterruptedException e) {
            LOGGER.error(StackTraceUtil.getStackTrace(e));
        }
    }


}
