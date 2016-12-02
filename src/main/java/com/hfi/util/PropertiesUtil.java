package com.hfi.util;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * 读取Properties文件的例子
 * File: PropertiesUtil.java
 * User: leizhimin
 * Date: 2008-2-15 18:38:40
 */
public final class PropertiesUtil {
    static  Properties prop;

    static {
         prop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/config.properties");
        try {
            prop.load(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 私有构造方法，不需要创建对象
     */
    private PropertiesUtil() {
    }


    public static String getProperty(String key){
        return prop.getProperty(key);
    }

    public static int getPropertyInt(String key){
        return Integer.parseInt(prop.getProperty(key));
    }

    public static Properties getProperty(){
        return prop;
    }
}
