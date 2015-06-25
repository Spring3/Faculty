package com.kpi.faculty.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 6/26/2015.
 */
public interface ICommand {
    String execute (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
