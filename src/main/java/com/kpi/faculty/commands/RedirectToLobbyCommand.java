package com.kpi.faculty.commands;

import com.kpi.faculty.dao.CourseDAO;
import com.kpi.faculty.dao.HumanDAO;
import com.kpi.faculty.dto.StudentInfo;
import com.kpi.faculty.models.Course;
import com.kpi.faculty.models.Human;
import com.kpi.faculty.util.Config;
import com.kpi.faculty.util.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 6/30/2015.
 */
public class RedirectToLobbyCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(RedirectToLobbyCommand.class);
    private CourseDAO dao = new CourseDAO();
    private HumanDAO humanDAO = new HumanDAO();

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageUrl;
        if (request.getSession().getAttribute("username") == null){
            pageUrl = Config.getInstance().getValue(Config.LOGIN);
        }
        else{
            pageUrl =  Config.getInstance().getValue(Config.LOBBY);
            Course course = dao.get(request.getParameter("course"));
            Human user = humanDAO.get((String)request.getSession().getAttribute("username"));
            request.setAttribute("course", course);
            request.setAttribute("teacher", course.getTeacher());
            request.setAttribute("role", user.getRole().name());
            if (user.getRole() == Human.Role.TEACHER){
                fillRequestForTeacher(request, course);
            }
        }
        return pageUrl;
    }

    private void fillRequestForTeacher(HttpServletRequest request, Course course){
        List<Human> students = humanDAO.getAllStudentsFor(course);
        List<StudentInfo> studentInfos = new ArrayList<StudentInfo>();
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            for (Human student : students) {
                String feedback = dao.getFeedBackForStudent(student, course, connection);
                String mark = dao.getMarkForStudent(student,course,connection);
                studentInfos.add(new StudentInfo(student.getName(), student.getLastName(), student.getUsername(), feedback, mark, course));
            }
            ConnectionPool.getInstance().closeConnection(connection);
            request.setAttribute("studentInfos", studentInfos);
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
