package com.kpi.faculty.controllers;

import com.kpi.faculty.commands.ICommand;
import com.kpi.faculty.commands.LoginCommand;
import com.kpi.faculty.commands.RegisterCommand;
import com.kpi.faculty.commands.UnknownCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by user on 6/26/2015.
 */
public final class DispatcherHelper {

    private static DispatcherHelper instance;
    private HashMap<String, ICommand> commands;

    private DispatcherHelper(){
        //putting all commands variants here
        commands = new HashMap<String, ICommand>();
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());

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

    //get command implementation according to the parameter from HttpRequest
    public ICommand getCommand(HttpServletRequest request){
        ICommand resultCommand = commands.get(request.getParameter("command"));
        if (resultCommand == null){
            resultCommand = new UnknownCommand();
        }
        return resultCommand;
    }
}
