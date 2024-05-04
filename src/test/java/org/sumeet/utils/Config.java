package org.sumeet.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {

   public static final Logger log = LoggerFactory.getLogger(Config.class);
   public static final String DEFAULT_PROPERTIES = "config/default.properties";
   private static Properties properties;

   public static void intialize() throws IOException {

       properties = loadProperties();

       for(String key : properties.stringPropertyNames()){
           if(System.getProperties().containsKey(key)){
               properties.setProperty(key, System.getProperty(key));
           }
       }

       log.info("Test Properties");
       log.info("----------------");
       for(String key : properties.stringPropertyNames()){

           log.info("{}={}", key, properties.get(key));
       }
       log.info("----------------");
   }

   public static String get(String key){
       return properties.getProperty(key);
   }

   public static Properties loadProperties() throws IOException {
       Properties properties = new Properties();

       try {
           InputStream stream = ResourceLoader.getResource(DEFAULT_PROPERTIES);
           properties.load(stream);
       }
       catch (Exception e){
           log.info("Unable to load properties file {}", DEFAULT_PROPERTIES,e);
       }

       return properties;
   }
}
