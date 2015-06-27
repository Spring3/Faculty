package com.kpi.faculty.util;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;


/*
    Connection pool
 */
public class ConnectionPool {

    private static ConnectionPool pool;
    private DataSource dataSource;
    private static boolean dbChecked = false;

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool(){
        Config config = Config.getInstance();
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setUrl(config.getValue(Config.URL)); //DB jdbc connection url
        poolProperties.setDriverClassName(config.getValue(Config.DRIVER)); //db jdbc driver type
        poolProperties.setMaxIdle(3); //max amount of thread that are allowed to idle at the same time
        poolProperties.setMaxWait(100); //max wait amount until request timeout exception
        poolProperties.setMaxActive(10); //max amount of active threads in the pool
        dataSource = new DataSource();
        dataSource.setPoolProperties(poolProperties);
    }

    public Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }

    //Close connection method
    public void closeConnection(Connection connection) throws SQLException{
        if (connection != null)
            connection.close();
    }

    public static ConnectionPool getInstance(){
        if (pool == null){
            synchronized (Object.class){
                if (pool == null){
                    pool = new ConnectionPool();
                    if (!dbChecked)
                        intitDb();
                }
            }
        }
        return pool;
    }

    /*
        To properly create a user and the schema of the database, use this as a query for mysql
        CREATE SCHEMA IF NOT EXISTS faculty CHARACTER SET NAMES utf8 COLLATE utf8_unicode_ci;
        CREATE USER 'facultyadmin'@'localhost' IDENTIFIED BY 'verysecret';
        GRANT ALL PRIVILEGES ON * . * TO 'facultyadmin'@'localhost';
     */
    private static void intitDb(){

        String[] queries = new String[]{
                "CREATE TABLE IF NOT EXISTS HUMAN (id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name varchar(10) NOT NULL, lastname varchar(20) NOT NULL, username varchar(20) NOT NULL, password varchar(50) NOT NULL, role varchar(10) NOT NULL);",
                "CREATE TABLE IF NOT EXISTS COURSE (id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name varchar(40), teacher_id INT UNSIGNED);",
                "CREATE TABLE IF NOT EXISTS COURSE_STUDENT (id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, course_id INT UNSIGNED, student_id INT UNSIGNED, mark varchar(1), feedback text);"
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
        dbChecked = true;
    }
}
