package com.rwt.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/*
属性文件工具类
 */

public class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /*
    加载属性文件
     */
    public static Properties loadProps(String filename) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (is == null) {
                throw new FileNotFoundException(filename + "file is not found");

            }
            properties = new Properties();
            properties.load(is);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("close inputstream failure ", e);
                }
            }

        }
        return properties;
    }


    /*
     *获取字符型属性（默认值为空）
     * */

    public static String getString(Properties properties, String key) {
       return  getString(properties, key, "");
    }
    /*
    * 获取字符属性（可指定默认值）
    * */
    public static String getString(Properties properties, String key, String defaultValue) {
        String value = defaultValue;
        if (properties.containsKey(key)) {
            value = properties.getProperty(key);
        }
        return value;
    }

}