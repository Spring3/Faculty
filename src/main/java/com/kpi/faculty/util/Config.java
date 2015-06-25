package com.kpi.faculty.util;

import java.util.ResourceBundle;

/**
 * Created by user on 6/26/2015.
 */
public class Config {

    private static Config instance;
    private ResourceBundle configFile;
    private static final String BUNDLE_NAME = "com.kpi.faculty.util.config";
    public static final String DRIVER = "DRIVER";
    public static final String URL = "URL";

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
