<%@ page import="com.kpi.faculty.models.Course" %>
<%@ page import="com.kpi.faculty.models.Human" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kpi.faculty.dao.HumanDAO" %>
<%@ page import="com.kpi.faculty.dao.CourseDAO" %>
<%@ page import="com.kpi.faculty.util.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <title>Courses</title>
  </head>
  <body style="text-align:center;">
  <h3>Available courses</h3>
  <%
    CourseDAO courseDAO = new CourseDAO();
    HumanDAO humanDAO = new HumanDAO();
    Human currentUser = humanDAO.get((String) session.getAttribute("username"));
    List<Course> availableCourses = courseDAO.getAllAvailableCoursesFor(currentUser);
    for (Course course : availableCourses){
  %>
    <form action="/dispatcher" method="post">
      <a href="<%= Config.getInstance().getValue(Config.LOBBY) + "?course=" + course.getName()%>"><%= course.getName() + " (" + course.getTeacher() + ") "%></a>
      <input type="hidden" name="name" value="<%=course.getName()%>"/>
      <input type="hidden" name="command" value="enroll"/>
      <input type="submit" value="Enroll"/>
      <br>
    </form>
  <%
    }
  %>
  </body>
</html>
