package com.kpi.faculty.commands;

import com.kpi.faculty.dao.CourseDAO;
import com.kpi.faculty.dao.HumanDAO;
import com.kpi.faculty.models.Course;
import com.kpi.faculty.models.Human;
import com.kpi.faculty.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 6/28/2015.
 */
public class FeedbackCommand implements ICommand {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        HumanDAO humanDAO = new HumanDAO();
        CourseDAO courseDAO = new CourseDAO();
        String mark = request.getParameter("mark");
        String feedback = request.getParameter("feedback");
        String courseName = request.getParameter("course");
        String studentUsername = request.getParameter("student");
        Human student = humanDAO.get(studentUsername);
        Course course = courseDAO.get(courseName);
        if (courseDAO.saveFeedback(feedback, mark, student,course)){
            page = Config.getInstance().getValue(Config.LOBBY)+ "?course=" + courseName;
        }
        else{
            response.sendError(500, "Failed to save feedback.");
        }
        return page;
    }
}
