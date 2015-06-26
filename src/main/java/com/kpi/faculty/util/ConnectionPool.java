package com.kpi.faculty.util;

import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

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
                    intitDb();
                }
            }
        }
        return pool;
    }

    /*
                CREATE SCHEMA IF NOT EXISTS faculty;
                CREATE USER 'facultyadmin'@'localhost' IDENTIFIED BY 'verysecret';
                GRANT ALL PRIVILEGES ON * . * TO 'facultyadmin'@'localhost';
     */
    private static void intitDb(){

        String[] queries = new String[]{
                "CREATE TABLE IF NOT EXISTS HUMAN (id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name varchar(10) NOT NULL, lastname varchar(20) NOT NULL, username varchar(20) NOT NULL, password varchar(50) NOT NULL, role varchar(10) NOT NULL);",
                "CREATE TABLE IF NOT EXISTS COURSE (id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name varchar(40), teacher_id INT UNSIGNED);",
                "CREATE TABLE IF NOT EXISTS COURSE_STUDENT (course_id INT UNSIGNED, student_id INT UNSIGNED, mark varchar(1), feedback text)"
        };

        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            for (String query : queries) {
             connection.createStatement().execute(query);
            }
            pool.closeConnection(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }
}
