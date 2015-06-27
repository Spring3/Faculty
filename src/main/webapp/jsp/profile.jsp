<%@ page import="com.kpi.faculty.dao.CourseDAO" %>
<%@ page import="com.kpi.faculty.dao.HumanDAO" %>
<%@ page import="com.kpi.faculty.models.Human" %>
<%@ page import="com.kpi.faculty.models.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <title>Profile</title>
  </head>

  <%
    request.setCharacterEncoding("UTF-8");
    CourseDAO courseDAO = new CourseDAO();
    HumanDAO humanDAO = new HumanDAO();
    Human currentUser = humanDAO.get((String)session.getAttribute("username"));
    List<Course> enrolledCourses = courseDAO.getAllCoursesFor(currentUser);
    Map<String, String> markAndFeedback = courseDAO.collectFeedbackAndMarksFor(currentUser);
  %>
  <body>
  <br>
    <form action="/dispatcher" method="post">
      <input type="submit" value="Log out"/>
      <input type="hidden" name="command" value="logout"/>
    </form>

    <div class="info">
      <h2><%= currentUser.toString()%></h2>
      <h4><%= currentUser.getUsername()%></h4>
      <br>
      <div class="feedback">
        <h3>Feedback</h3>
        <%
          int index = 0;
          for (Map.Entry<String, String> feedback : markAndFeedback.entrySet()){
        %>
          <h3>Feedback from: <%= enrolledCourses.get(index).getName() + " on course " + enrolledCourses.get(index).getName()%>></h3>
          <h4>Mark: <%= feedback.getKey()%></h4>
          <p><%= feedback.getValue()%></p>
          <br>
        <%
          }
        %>
      </div>
    </div>

    <div class="courses">
      <h3>Courses you have enrolled</h3>
      <div class="enrolled">
        <%
          for (Course course : enrolledCourses){
        %>
          <a href="jsp/lobby.jsp?course=<%=course.getName()%>"><%= course.getName() + " (" + course.getTeacher() + ") "%></a>
        <%
          }
        %>
      </div>
      <br>
      <a href="jsp/courses.jsp"><input type="button" value="Find more"/></a>
    </div>


  </body>
</html>
