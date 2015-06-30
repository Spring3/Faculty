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
public class EnrollCommand extends RedirectToProfileExtention implements ICommand {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        CourseDAO dao = new CourseDAO();
        HumanDAO humanDAO = new HumanDAO();
        String courseName = request.getParameter("name");
        Human student = humanDAO.get((String) request.getSession().getAttribute("username"));
        Course course = dao.get(courseName);
        if (dao.enroll(course, student)){
            page = Config.getInstance().getValue(Config.PROFILE);
            addRequestContent(request, student);
        }
        else{
            response.sendError(500, "Failed to enroll a student on course " + courseName);
        }
        return page;
    }
}
