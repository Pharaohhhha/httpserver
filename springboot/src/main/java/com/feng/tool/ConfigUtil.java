package com.feng.tool;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Andy
 * @date 2017-08-01 21:31
 */
public class ConfigUtil {

    private static final Logger logger = Logger.getLogger(ConfigUtil.class);

    public static Properties getConfig(String filePath) {
        InputStream inputStream = null;
        Properties prop = new Properties();
        try {
            inputStream = ConfigUtil.class.getResourceAsStream(filePath);
            prop.load(inputStream);
        } catch (Exception e) {
            logger.error("init properties error: " + filePath, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("can't close the input stream!", e);
                }
            }
        }
        return prop;
    }

    public static Properties getConfigProperties(){
        return ConfigUtil.getConfig("/config/config.properties");
    }

    public static String getPath(){
        return getConfigProperties().getProperty("expandword.path");
    }

    public static void main(String[] args) {
        Properties prop = ConfigUtil.getConfig("/config/config.properties");
        logger.info(prop.getProperty("INDEX_NAME"));
    }

}
