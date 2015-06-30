package com.kpi.faculty.commands;

import com.kpi.faculty.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements ICommand {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("username");
        request.getSession().invalidate();
        return Config.getInstance().getValue(Config.LOGIN);
    }
}
