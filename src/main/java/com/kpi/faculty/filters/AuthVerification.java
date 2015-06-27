package com.kpi.faculty.filters;

import com.kpi.faculty.util.Config;
import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Filter for authentication verification
 */
public class AuthVerification implements Filter {

    private static final Logger logger = Logger.getLogger(AuthVerification.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        String userName = (String)((HttpServletRequest)servletRequest).getSession().getAttribute("username");
        String url = ((HttpServletRequest)servletRequest).getRequestURL().toString();

        if (userName != null){
            filterChain.doFilter(servletRequest, servletResponse);
            logger.info(String.format("Received a request from user %s to %s", userName, url));
        }
        else
        {
            if (url.contains("/reg.jsp") || servletRequest.getParameter("username") != null){
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else {
                ((HttpServletResponse) servletResponse).sendRedirect(Config.getInstance().getValue(Config.LOGIN));
                logger.info(String.format("Unauthorized access to %s. Denied. Redirecting to login page...", url));
            }
        }
    }

    public void destroy() {

    }
}
