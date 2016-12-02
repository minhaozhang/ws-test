package com.hfi.sys;


import com.hfi.util.StackTraceUtil;
import com.hfi.wsTest.WsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ApplicationContextHost {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextHost.class);
    public static AbstractRefreshableConfigApplicationContext BeanContext = new ClassPathXmlApplicationContext();
    private static String[] locations = { "/spring/ApplicationContext.xml" };

    public static void init() {
        BeanContext.setConfigLocations(locations);
        BeanContext.refresh();
        LOGGER.info("--------Spring start successful!-------");
        try {
            WsClient simpleEchoClient=(WsClient)getBean("wsClient");
            simpleEchoClient.sysThreadStart();
        } catch (Exception e) {
            LOGGER.error(StackTraceUtil.getStackTrace(e));
        }
    }

    public static Object getBean(String beanName) {
        return BeanContext.getBean(beanName);
    }
}
