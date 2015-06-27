package com.kpi.faculty.util;

import java.util.ResourceBundle;

/*
    Application configuration class
    Uses config.properties as a configuration file
 */
public class Config {

    private static Config instance;
    private ResourceBundle configFile;
    private static final String BUNDLE_NAME = "config";
    public static final String DRIVER = "DRIVER";
    public static final String URL = "URL";
    public static final String LOGIN = "LOGIN";
    public static final String REGISTER = "REGISTER";
    public static final String PROFILE = "PROFILE";
    public static final String COURSES = "COURSES";
    public static final String LOBBY = "LOBBY";

    private Config(){
        configFile = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public static Config getInstance(){
        if (instance == null){
            synchronized (Object.class){
                if (instance == null){
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    public String getValue(String key){
        return configFile.getString(key);
    }

}
