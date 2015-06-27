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
 * Created by user on 6/27/2015.
 */
public class AddCourseCommand implements ICommand {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        CourseDAO courseDAO = new CourseDAO();
        HumanDAO humanDAO = new HumanDAO();
        String courseName = request.getParameter("name");
        Human teacher = humanDAO.get((String) request.getSession().getAttribute("username"));
        Course course = new Course(courseName, teacher);
        if (courseDAO.create(course)){
            page = Config.getInstance().getValue(Config.PROFILE);
        }
        else{
            response.sendError(500, "Unable to create a course");
        }
        return page;
    }
}
