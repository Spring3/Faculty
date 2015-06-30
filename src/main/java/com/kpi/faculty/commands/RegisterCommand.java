package com.kpi.faculty.commands;

import com.kpi.faculty.dao.HumanDAO;
import com.kpi.faculty.models.Human;
import com.kpi.faculty.models.Student;
import com.kpi.faculty.models.Teacher;
import com.kpi.faculty.util.Config;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
    Registration command handler
 */
public class RegisterCommand extends RedirectToProfileExtention implements ICommand {

    private static final Logger logger = Logger.getLogger(RegisterCommand.class);
    private HumanDAO humanDAO = new HumanDAO();

    private static final String NULLVALUE_ERROR = "Please, fill in all the fields.";
    private static final String USER_EXISTS = "Such user already exists.";
    private static final String PASS_NOT_MATCH = "Passwords don't match.";

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastname");
        boolean isTeacher = request.getParameter("teacher") != null;

        if (username != null && password1 != null && password2 != null && name != null && lastName != null)
        {
            if (!username.isEmpty() && !password1.isEmpty() && !password2.isEmpty() && !name.isEmpty() && !lastName.isEmpty()) {

                if (password1.equals(password2)) {
                    Human user = humanDAO.get(username);
                    if (user == null) {
                        user = isTeacher ? new Teacher(username, password1, name, lastName) : new Student(username, password1, name, lastName);
                        humanDAO.create(user);
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        page = Config.getInstance().getValue(Config.PROFILE);
                        addRequestContent(request, user);
                        logger.info("User successfully registered");
                    } else {
                        request.setAttribute("error", USER_EXISTS);
                        page = Config.getInstance().getValue(Config.REGISTER);
                        logger.error(String.format("Filter: error detected -> %s", USER_EXISTS));
                    }
                } else {
                    request.setAttribute("error", PASS_NOT_MATCH);
                    page = Config.getInstance().getValue(Config.REGISTER);
                    logger.error(String.format("Filter: error detected -> %s", PASS_NOT_MATCH));
                }
            }
            else{
                request.setAttribute("error", NULLVALUE_ERROR);
                page = Config.getInstance().getValue(Config.REGISTER);
                logger.error(String.format("Filter: error detected -> %s", NULLVALUE_ERROR));
            }
        }
        else{
            request.setAttribute("error", NULLVALUE_ERROR);
            page = Config.getInstance().getValue(Config.REGISTER);
            logger.error(String.format("Filter: error detected -> %s", NULLVALUE_ERROR));
        }

        return page;
    }


}
