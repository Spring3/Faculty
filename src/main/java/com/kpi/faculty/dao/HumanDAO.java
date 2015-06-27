package com.kpi.faculty.dao;

import com.kpi.faculty.models.Human;
import com.kpi.faculty.models.Student;
import com.kpi.faculty.models.Teacher;
import com.kpi.faculty.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    Data access object for 'HUMAN' table from the database
 */
public class HumanDAO implements IDAO<Human>{

    public HumanDAO(){

    }

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(HumanDAO.class);

    //Get alll users
    public List<Human> getAll() {
        List<Human> resultList = new ArrayList<Human>();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM HUMAN;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String role = resultSet.getString(6);
                Human humanStub = role.equals(Human.Role.TEACHER.name()) ? new Teacher() : new Student();
                humanStub.setId(resultSet.getInt(1));
                humanStub.setName(resultSet.getString(2));
                humanStub.setLastName(resultSet.getString(3));
                humanStub.setUsername(resultSet.getString(4));
                humanStub.setPassword(resultSet.getString(5));
                humanStub.setRole(Human.Role.valueOf(role));
                resultList.add(humanStub);
            }
            connectionPool.closeConnection(connection);
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }

        return resultList;
    }

    //Get a user by username
    public Human get(String username) {
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM HUMAN WHERE username = ?;");
            statement.setString(1, username);
            return getBy(statement, connection);
        }
        catch (SQLException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return null;
    }

    //Get a user by id
    public Human get(int id) {
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM HUMAN WHERE id = ?;");
            statement.setInt(1, id);
            return getBy(statement, connection);
        }
        catch (SQLException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return null;
    }

    //Save user to the database
    public boolean create(Human value) {
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO HUMAN (name, lastname, username, password, role) VALUES (?, ?, ?, ?, ?);");
            statement.setString(1, value.getName());
            statement.setString(2, value.getLastName());
            statement.setString(3, value.getUsername());
            statement.setString(4, value.getPassword());
            statement.setString(5, value.getRole().name());
            statement.execute();
            connectionPool.closeConnection(connection);
            return true;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return false;
    }

    //Remove user from the database
    public boolean remove(Human value) {
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM HUMAN WHERE id = ?;");
            statement.setInt(1, value.getId());
            statement.executeUpdate();
            connectionPool.closeConnection(connection);
            return true;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return false;
    }

    //Update user entity in the database
    public boolean update(Human value) {
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE HUMAN SET id = ?, name = ?, lastname = ?, username = ?, password = ?, role = ? WHERE id = ?;");
            statement.setInt(1, value.getId());
            statement.setString(2, value.getName());
            statement.setString(3, value.getLastName());
            statement.setString(4, value.getUsername());
            statement.setString(5, value.getPassword());
            statement.setString(6, value.getRole().name());
            statement.setInt(7, value.getId());
            statement.executeUpdate();
            connectionPool.closeConnection(connection);
            return true;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return false;
    }

    //'Get by' retrievement method
    private Human getBy(PreparedStatement statement, Connection connection){
        try{
            Human humanBuffer = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String role = resultSet.getString(6);
                humanBuffer = role.equals(Human.Role.TEACHER.name()) ? new Teacher() : new Student();
                humanBuffer.setId(resultSet.getInt(1));
                humanBuffer.setName(resultSet.getString(2));
                humanBuffer.setLastName(resultSet.getString(3));
                humanBuffer.setUsername(resultSet.getString(4));
                humanBuffer.setPassword(resultSet.getString(5));
                humanBuffer.setRole(Human.Role.valueOf(resultSet.getString(6)));
            }
            connectionPool.closeConnection(connection);
            return humanBuffer;
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
