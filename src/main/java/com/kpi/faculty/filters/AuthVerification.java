package com.kpi.faculty.filters;

import com.kpi.faculty.util.Config;
import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 6/26/2015.
 */
public class AuthVerification implements Filter {

    private static final Logger logger = Logger.getLogger(AuthVerification.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String userName = (String)servletRequest.getAttribute("username");
        String url = ((HttpServletRequest)servletRequest).getRequestURL().toString();

        if (userName != null){
            filterChain.doFilter(servletRequest, servletResponse);
            logger.info(String.format("Received a request from user %s", userName));
        }
        else
        {
            if (servletRequest.getParameter("username") != null){
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else {
                ((HttpServletResponse) servletResponse).sendRedirect(Config.getInstance().getValue(Config.LOGIN));
                logger.info("Unauthorized access. Abandoned. Redirecting to login page...");
            }
        }
    }

    public void destroy() {

    }
}
