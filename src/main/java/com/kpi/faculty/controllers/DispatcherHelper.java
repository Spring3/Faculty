package com.kpi.faculty.controllers;

import com.kpi.faculty.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/*
   Main Servlet helper. Routs received requests to the right commands.
*/
public final class DispatcherHelper {

    private static DispatcherHelper instance;
    private HashMap<String, ICommand> commands;

    private DispatcherHelper(){
        //putting all commands variants here
        commands = new HashMap<String, ICommand>();
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
        commands.put("enroll", new EnrollCommand());
        commands.put("unenroll", new UnenrollCommand());
        commands.put("addCourse", new AddCourseCommand());
        commands.put("logout", new LogoutCommand());

    }

    //thread-safe singleton
    public static DispatcherHelper getInstance(){
        if (instance == null){
            synchronized (DispatcherHelper.class){
                if (instance == null){
                    instance = new DispatcherHelper();
                }
            }
        }
        return instance;
    }

    //get command implementation according to the 'command' parameter from HttpRequest
    public ICommand getCommand(HttpServletRequest request){
        ICommand resultCommand = commands.get(request.getParameter("command"));
        if (resultCommand == null){
            resultCommand = new UnknownCommand();
        }
        return resultCommand;
    }
}
