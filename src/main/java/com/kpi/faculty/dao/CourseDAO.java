package com.kpi.faculty.dao;

import com.kpi.faculty.models.Course;
import com.kpi.faculty.models.Human;
import com.kpi.faculty.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    Data access object for Course class
    Fetches all the data from 'COURSE' table in the database
 */
public class CourseDAO implements IDAO<Course> {

    public CourseDAO(){

    }

    //apache tomcat connection pool
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(CourseDAO.class);

    //Get all courses
    public List<Course> getAll() {
        List<Course> resultList = new ArrayList<Course>();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM COURSE;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Course courseStub = new Course();
                courseStub.setId(resultSet.getInt(1));
                courseStub.setName(resultSet.getString(2));
                courseStub.setTeacher(new HumanDAO().get(resultSet.getInt(3)));
                resultList.add(courseStub);
            }
            connectionPool.closeConnection(connection);
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }

        return resultList;
    }

    //Get all students for a course
    public List<Human> getAllStudentsFor(Course course){
        List<Human> resultList = new ArrayList<Human>();
        HumanDAO humanDAO = new HumanDAO();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT student_id FROM COURSE_STUDENT WHERE course_id = ?;");
            statement.setInt(1, course.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                resultList.add(humanDAO.get(resultSet.getInt(1)));
            }
            connectionPool.closeConnection(connection);
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }

        return resultList;
    }

    //Get feedback for a student on course
    public String getFeedBackForStudent(Human student, Course course){
        String feedback = null;
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT feedback FROM COURSE_STUDENT WHERE student_id = ? AND course_id = ?;");
            statement.setInt(1, student.getId());
            statement.setInt(2, course.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                feedback = resultSet.getString(1);
            }
            connectionPool.closeConnection(connection);
        }
        catch (SQLException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return feedback;
    }

    //Get mark for a student on course
    public String getMarkForStudent(Human student, Course course){
        String mark = null;
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT mark FROM COURSE_STUDENT WHERE student_id = ? AND course_id = ?;");
            statement.setInt(1, student.getId());
            statement.setInt(2, course.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                mark = resultSet.getString(1);
            }
            connectionPool.closeConnection(connection);
        }
        catch (SQLException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return mark;
    }

    //Get course object by name
    public Course get(String name) {
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM COURSE WHERE name = ?;");
            statement.setString(1, name);
            return getBy(statement, connection);
        }
        catch (SQLException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return null;
    }

    //Save course object to the database
    public boolean create(Course value) {
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO COURSE (name, teacher_id) VALUES (?, ?);");
            statement.setString(1, value.getName());
            statement.setInt(2, value.getTeacher().getId());
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

    //Remove course object from the database
    public boolean remove(Course value) {
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM COURSE WHERE id = ?;");
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

    //Enroll a student on course
    public boolean enroll(Course course, Human human){
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO COURSE_STUDENT (course_id, student_id, mark, feedback) VALUES (?, ?, ?, ?);");
            statement.setInt(1, course.getId());
            statement.setInt(2, human.getId());
            statement.setString(3, null);
            statement.setString(4, null);
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

    //Unenroll from a course
    public boolean unenroll(Course course, Human human){
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM COURSE_STUDENT WHERE course_id = ? AND student_id = ?;");
            statement.setInt(1, course.getId());
            statement.setInt(2, human.getId());
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

    //Update course object
    public boolean update(Course value) {
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE COURSE SET id = ?, name = ?, teacher_id = ? WHERE id = ?;");
            statement.setInt(1, value.getId());
            statement.setString(2, value.getName());
            statement.setInt(3, value.getTeacher().getId());
            statement.setInt(4, value.getId());
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

    //Retrievement 'by param' method
    private Course getBy(PreparedStatement statement, Connection connection){
        try{
            Course courseBuffer = new Course();
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                courseBuffer.setId(resultSet.getInt(1));
                courseBuffer.setName(resultSet.getString(2));
                courseBuffer.setTeacher(new HumanDAO().get(resultSet.getInt(3)));
            }
            connectionPool.closeConnection(connection);
            return courseBuffer;
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
