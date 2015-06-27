package com.kpi.faculty.dao;

import com.kpi.faculty.models.Course;
import com.kpi.faculty.models.Human;
import com.kpi.faculty.util.ConnectionPool;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, String> collectFeedbackAndMarksFor(Human student){
        Connection connection = null;
        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        try {
            connection = connectionPool.getConnection();
            List<Course> enrolledCourses = getAllCoursesFor(student);
            for(Course course : enrolledCourses){
                resultMap.put(getMarkForStudent(student, course, connection), getFeedBackForStudent(student, course, connection));
            }
            connectionPool.closeConnection(connection);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    //Gell all available courses for a student. Includes only those, which he wasn't enrolled on.
    public List<Course> getAllAvailableCoursesFor(Human student){
        List<Course> resultList = new ArrayList<Course>();

        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement;
            if (student.getId() == 0){
                statement = connection.prepareStatement("SELECT c.id FROM COURSE AS c WHERE c.id NOT IN(SELECT cs.course_id FROM HUMAN AS h LEFT JOIN COURSE_STUDENT AS cs ON cs.student_id = h.id WHERE h.username = ? AND cs.course_id IS NOT NULL GROUP BY course_id);");
                statement.setString(1, student.getUsername());
            }
            else{
                statement = connection.prepareStatement("SELECT c.id FROM COURSE AS c WHERE c.id NOT IN(SELECT cs.course_id FROM HUMAN AS h LEFT JOIN COURSE_STUDENT AS cs ON cs.student_id = ? WHERE cs.course_id IS NOT NULL GROUP BY course_id);;");
                statement.setInt(1, student.getId());
            }

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                resultList.add(get(resultSet.getInt(1)));
            }
            connectionPool.closeConnection(connection);

        }
        catch(SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return resultList;
    }

    //Get all courses on which a student is already enrolled
    public List<Course> getAllCoursesFor(Human human){
        List<Course> resultList = new ArrayList<Course>();

        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement;
            if (human.getId() == 0){
                statement = connection.prepareStatement("SELECT course_id FROM HUMAN as h INNER JOIN COURSE_STUDENT as c ON h.id = c.student_id WHERE h.username = ?;");
                statement.setString(1, human.getUsername());
            }
            else{
                statement = connection.prepareStatement("SELECT course_id FROM COURSE_STUDENT WHERE student_id = ?;");
                statement.setInt(1, human.getId());
            }

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                resultList.add(get(resultSet.getInt(1)));
            }
            connectionPool.closeConnection(connection);

        }
        catch(SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return resultList;
    }

    public List<Course> getAllCoursesOf(Human teacher){
        List<Course> resultList = new ArrayList<Course>();

        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement;
            if (teacher.getId() == 0){
                statement = connection.prepareStatement("SELECT c.id FROM COURSE as c INNER JOIN HUMAN as h ON h.id = c.teacher_id WHERE h.username = ?;");
                statement.setString(1, teacher.getUsername());
            }
            else{
                statement = connection.prepareStatement("SELECT id FROM COURSE WHERE teacher_id = ?;");
                statement.setInt(1, teacher.getId());
            }

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                resultList.add(get(resultSet.getInt(1)));
            }
            connectionPool.closeConnection(connection);

        }
        catch(SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return resultList;
    }

    //Get feedback for a student on course
    public String getFeedBackForStudent(Human student, Course course, Connection connection){
        String feedback = null;
        boolean haveToCloseConnection = false;

        try{
            if (connection == null){
                haveToCloseConnection = true;
                connection = connectionPool.getConnection();
            }
            PreparedStatement statement = connection.prepareStatement("SELECT feedback FROM COURSE_STUDENT WHERE student_id = ? AND course_id = ?;");
            statement.setInt(1, student.getId());
            statement.setInt(2, course.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                feedback = resultSet.getString(1);
            }
            if (haveToCloseConnection)
                connectionPool.closeConnection(connection);
        }
        catch (SQLException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return feedback;
    }

    //Get mark for a student on course
    public String getMarkForStudent(Human student, Course course, Connection connection){
        String mark = null;
        boolean haveToCloseConnection = false;
        try{
            if (connection == null){
                haveToCloseConnection = true;
                connection = connectionPool.getConnection();
            }
            PreparedStatement statement = connection.prepareStatement("SELECT mark FROM COURSE_STUDENT WHERE student_id = ? AND course_id = ?;");
            statement.setInt(1, student.getId());
            statement.setInt(2, course.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                mark = resultSet.getString(1);
            }
            if (haveToCloseConnection)
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

    public Course get(int id){
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM COURSE WHERE id = ?;");
            statement.setInt(1, id);
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

    public boolean saveFeedback(String feedback, String mark, Human student, Course course){
        boolean result = false;
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE COURSE_STUDENT SET mark = ?, feedback = ? WHERE course_id = ? AND student_id = ?");
            statement.setString(1, mark);
            statement.setString(2, feedback);
            statement.setInt(3, course.getId());
            statement.setInt(4, student.getId());
            result = statement.executeUpdate() > 0;
            connectionPool.closeConnection(connection);
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    //Enroll a student on course
    public boolean enroll(Course course, Human human){
        if (isEnrolled(human, course))
            return false;
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

    private boolean isEnrolled(Human student, Course course){
        boolean result = false;
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT course_id FROM COURSE_STUDENT WHERE student_id = ? AND course_id = ?");
            statement.setInt(1, student.getId());
            statement.setInt(2, course.getId());
            result = statement.executeQuery().next();
            connectionPool.closeConnection(connection);
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
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
