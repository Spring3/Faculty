package com.kpi.faculty.commands;

import com.kpi.faculty.dao.HumanDAO;
import com.kpi.faculty.models.Human;
import com.kpi.faculty.util.Config;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by user on 6/26/2015.
 */
public class LoginCommand implements ICommand {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ERROR_LOGIN = "Wrong username or password";
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

    private String redirectToLoginPage(HttpServletRequest request){
        request.setAttribute("error", ERROR_LOGIN);
        return Config.getInstance().getValue(Config.LOGIN);
    }
}
