<%@ page import="com.kpi.faculty.dao.CourseDAO" %>
<%@ page import="com.kpi.faculty.dao.HumanDAO" %>
<%@ page import="com.kpi.faculty.models.Human" %>
<%@ page import="com.kpi.faculty.models.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.kpi.faculty.util.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <title>Profile</title>
  </head>

  <%
    CourseDAO courseDAO = new CourseDAO();
    HumanDAO humanDAO = new HumanDAO();
    Human currentUser = humanDAO.get((String) session.getAttribute("username"));
  %>
  <body>
  <br>
    <form action="/dispatcher" method="post">
      <input type="submit" value="Log out"/>
      <input type="hidden" name="command" value="logout"/>
    </form>

  <%
    if (currentUser.getRole() == Human.Role.STUDENT){
  %>
    <div class="info">
      <h2><%= currentUser%></h2>
      <h4><%= currentUser.getUsername()%></h4>
      <br>
    </div>

    <div class="feedback">
    <h3>Feedback</h3>
    <%
      int index = 0;
      List<Course> enrolledCourses = courseDAO.getAllCoursesFor(currentUser);
      Map<String, String> markAndFeedback = courseDAO.collectFeedbackAndMarksFor(currentUser);
      for (Map.Entry<String, String> feedback : markAndFeedback.entrySet()){
        if (feedback.getKey() != null){
    %>
    <h4>Feedback from: <%= enrolledCourses.get(index).getTeacher() + " on course " + enrolledCourses.get(index).getName()%>></h4>
    <h5>Mark: <%= feedback.getKey()%></h5>
    <p><%= feedback.getValue()%></p>
    <br>
    <%
          index ++;
        }
      }
    %>
    </div>

    <div class="courses">
      <h3>Courses you have enrolled</h3>
      <div class="enrolled">
        <%
          for (Course course : enrolledCourses){
        %>
          <a href="<%=Config.getInstance().getValue(Config.LOBBY)%>?course=<%=course.getName()%>"><%= course.getName() + " (" + course.getTeacher() + ") "%></a>
          <br>
        <%
          }
        %>
      </div>
      <br>
      <a href="<%=Config.getInstance().getValue(Config.COURSES)%>"><input type="button" value="Find more"/></a>
    </div>

  <%
    }
    else{
      List<Course> courses = courseDAO.getAllCoursesOf(currentUser);
      for (Course course : courses){
  %>

  <a href="<%=Config.getInstance().getValue(Config.LOBBY)%>?course=<%=course.getName()%>"><%= course.getName()%></a>
  <br>

  <%
      }
  %>
    <form action="/dispatcher" method="post">
      <h3>Add new course</h3>
      <label>Course name</label>
      <input type="text" placeholder="Course name" name="name"/>
      <br>
      <input type="hidden" name="command" value="addCourse"/>
      <input type="submit" value="Add"/>
    </form>
  <%
    }
  %>
  </body>
</html>
