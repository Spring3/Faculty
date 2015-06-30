package com.kpi.faculty.commands;

import com.kpi.faculty.dao.CourseDAO;
import com.kpi.faculty.dao.HumanDAO;
import com.kpi.faculty.models.Course;
import com.kpi.faculty.models.Human;
import com.kpi.faculty.util.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
    Login command handler
 */
public class LoginCommand implements ICommand {

    // Request parameter names
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    // Error text
    private static final String ERROR_LOGIN = "Wrong username or password";
    private static final Logger logger = Logger.getLogger(LoginCommand.class);
    //Data access object for Human class
    private HumanDAO humanDAO = new HumanDAO();

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        if (username != null && password != null){
            if (!username.isEmpty() && !password.isEmpty()){
                Human user = humanDAO.get(username);
                if (user != null){
                    if (user.getPassword().equals(DigestUtils.sha1Hex(password))){
                        HttpSession session = request.getSession();
                        session.setAttribute(USERNAME, user.getUsername());
                        session.setMaxInactiveInterval(60*24);
                        request.setAttribute(USERNAME, user.getUsername());
                        addRequestContent(request, user);
                        page = Config.getInstance().getValue(Config.PROFILE);
                    }
                    else{
                        page = redirectToLoginPage(request);
                    }
                }
                else{
                    page = redirectToLoginPage(request);
                }
            }
            else{
                page = redirectToLoginPage(request);
            }
        }
        else{
            page = redirectToLoginPage(request);
        }
        return page;
    }

    /*
     * Error handler
     */
    private String redirectToLoginPage(HttpServletRequest request){
        request.setAttribute("error", ERROR_LOGIN);
        logger.error(ERROR_LOGIN);
        return Config.getInstance().getValue(Config.LOGIN);
    }

    private void addRequestContent(HttpServletRequest request, Human user){
        request.setAttribute("user", user);
        request.setAttribute("role", user.getRole().name());
        CourseDAO courseDAO = new CourseDAO();
        if (user.getRole() == Human.Role.STUDENT){
            List<Course> enrolledCourses = courseDAO.getAllCoursesFor(user);
            Map<String, String> markAndFeedback = courseDAO.collectFeedbackAndMarksFor(user);
            for (Map.Entry<String, String> entry : markAndFeedback.entrySet()){
                if (entry.getKey() == null){
                    markAndFeedback.remove(entry.getKey());
                }
            }
            request.setAttribute("courses", enrolledCourses);
            request.setAttribute("markAndFeedback", markAndFeedback);
        }
        else{
            List<Course> courses = courseDAO.getAllCoursesOf(user);
            request.setAttribute("courses", courses);
        }
    }
}
