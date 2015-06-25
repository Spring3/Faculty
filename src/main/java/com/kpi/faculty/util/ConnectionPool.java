package com.kpi.faculty.util;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by user on 6/26/2015.
 */
public class ConnectionPool {
    private static ConnectionPool pool;
    private DataSource dataSource;
    private PoolProperties poolProperties;

    public ConnectionPool(){
        Config config = Config.getInstance();
        poolProperties = new PoolProperties();
        poolProperties.setUrl(config.getValue(Config.URL));
        poolProperties.setDriverClassName(config.getValue(Config.DRIVER));
        poolProperties.setMaxIdle(3);
        poolProperties.setMaxWait(100);
        poolProperties.setMaxActive(10);
        dataSource = new DataSource();
        dataSource.setPoolProperties(poolProperties);
    }

    public Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }

    public void closeConnection(Connection connection) throws SQLException{
        if (connection != null)
            connection.close();
    }

    public static ConnectionPool getInstance(){
        if (pool == null){
            synchronized (Object.class){
                if (pool == null){
                    pool = new ConnectionPool();
                }
            }
        }
        return pool;
    }
}
