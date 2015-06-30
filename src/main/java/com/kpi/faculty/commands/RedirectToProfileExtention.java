package com.kpi.faculty.commands;

import com.kpi.faculty.dao.CourseDAO;
import com.kpi.faculty.dto.StudentInfo;
import com.kpi.faculty.models.Course;
import com.kpi.faculty.models.Human;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/1/2015.
 */
public abstract class RedirectToProfileExtention {
    protected void addRequestContent(HttpServletRequest request, Human user){
        request.setAttribute("user", user);
        request.setAttribute("role", user.getRole().name());
        CourseDAO courseDAO = new CourseDAO();
        if (user.getRole() == Human.Role.STUDENT){
            List<StudentInfo> studentInfos = new ArrayList<StudentInfo>();
            List<Course> enrolledCourses = courseDAO.getAllCoursesFor(user);
            for(Course course : enrolledCourses){
                String feedback = courseDAO.getFeedBackForStudent(user, course, null);
                String mark = courseDAO.getMarkForStudent(user, course, null);
                studentInfos.add(new StudentInfo(user.getName(), user.getLastName(), user.getUsername(), feedback, mark, course));
            }
            request.setAttribute("courses", enrolledCourses);
            request.setAttribute("studentInfos", studentInfos);
        }
        else{
            List<Course> courses = courseDAO.getAllCoursesOf(user);
            request.setAttribute("courses", courses);
        }
    }
}
