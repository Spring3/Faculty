<%@ page import="com.kpi.faculty.models.Course" %>
<%@ page import="com.kpi.faculty.models.Human" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kpi.faculty.dao.HumanDAO" %>
<%@ page import="com.kpi.faculty.dao.CourseDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <title>Courses</title>
  </head>
  <body>
  <h3>Available courses</h3>
  <%
    request.setCharacterEncoding("UTF-8");
    CourseDAO courseDAO = new CourseDAO();
    HumanDAO humanDAO = new HumanDAO();
    Human currentUser = humanDAO.get((String) session.getAttribute("username"));
    if (currentUser != null){
      List<Course> availableCourses = courseDAO.getAllAvailableCoursesFor(currentUser);
      for (Course course : availableCourses){
  %>
    <form action="/dispatcher" method="post">
      <a href="/course"><%= course.getName() + " (" + course.getTeacher() + ") "%></a>
      <input type="submit" value="Enroll"/>
      <input type="hidden" name="command" value="enroll"/>
      <br>
    </form>

  <%
      }
    }
  %>
  </body>
</html>
