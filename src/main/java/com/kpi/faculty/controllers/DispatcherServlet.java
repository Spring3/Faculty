package com.kpi.faculty.controllers;

import com.kpi.faculty.commands.ICommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Main Servlet. Gets all requests and then routs them to the right commands via DispatcherServletHelper.
*/
public class DispatcherServlet extends HttpServlet {

    private static final DispatcherHelper helper = DispatcherHelper.getInstance();
    private static final Logger logger = Logger.getLogger(DispatcherServlet.class);

    public DispatcherServlet(){
        super();
    }

    /*
        Gets all requests and passes them to the DispatcherServletHelper, which decides which page's URI to return
     */
    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String pageURL = null;
        try{
            ICommand command = helper.getCommand(request);
            pageURL = command.execute(request, response);
        }
        catch (ServletException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        catch (IOException ioex){
            ioex.printStackTrace();
            logger.error(ioex.getMessage());
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pageURL);
        dispatcher.forward(request, response);
    }

    @Override
    /*
        'GET' - type request handler
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    /*
        'POST' - type request handler
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
