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
import java.util.List;

/**
 * Created by user on 6/30/2015.
 */
public class RedirectToCoursesCommand implements ICommand {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageUrl;
        if (request.getSession().getAttribute("username") == null){
            pageUrl = Config.getInstance().getValue(Config.LOGIN);
        }
        else{
            HumanDAO dao = new HumanDAO();
            CourseDAO courseDAO = new CourseDAO();
            pageUrl = Config.getInstance().getValue(Config.COURSES);
            Human student = dao.get((String)request.getSession().getAttribute("username"));
            List<Course> availableCourses =  courseDAO.getAllAvailableCoursesFor(student);
            request.setAttribute("availableCourses", availableCourses);
        }
        return pageUrl;
    }
}
