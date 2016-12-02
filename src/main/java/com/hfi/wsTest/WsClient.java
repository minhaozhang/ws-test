package com.hfi.wsTest;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import com.hfi.util.StackTraceUtil;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class WsClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WsClient.class);

    @Value("#{configProperties['ws.server']}")
    public  String wsServer;

    @Value("#{configProperties['thread.num']}")
    public   int threadNum;
    @Value("#{configProperties['sleep.time']}")
    public    int sleepScan;

    public void sysThreadStart() {
        LOGGER.info("测试的地址为："+wsServer+",共启动"+threadNum+"个线程,启动间隔为"+sleepScan / 1000 + "秒...");
        // 文件扫描启动线程

        try {

        for (int i = 0; i < threadNum; i++) {
            LOGGER.info("正在启动第" + (i+1)+ "个线程");

                Thread connectThread = new Thread() {
                    public void run() {
                        connect(wsServer,this.getName());
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

    public static void connect(String destUri,String threadId) {
        QueuedThreadPool threadPool = new QueuedThreadPool(5000,200);
        String name = WebSocketClient.class.getSimpleName() + "@" + threadId;
        threadPool.setName(name);
        threadPool.setDaemon(false);
        WebSocketClient client = new WebSocketClient(threadPool);
        ClientSocket socket = new ClientSocket(threadId);
        try {
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            LOGGER.info("Connecting to : "+ echoUri);
            //自动关闭时间
            socket.awaitClose(500000, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                LOGGER.info(StackTraceUtil.getStackTrace(e));
            }
        }
    }

    public static void main(String[] args) {
        connect("ws://localhost:8080/nbd/ws/bidding/2","main");
    }


}



