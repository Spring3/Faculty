<%@ page import="com.kpi.faculty.dao.CourseDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <%
      request.setCharacterEncoding("UTF-8");
       String courseName = (String)request.getAttribute("course");
    %>
    <title><%= courseName%></title>
  </head>
  <body>

  <h3><%= courseName%></h3>
  <h3>Teacher: <%= new CourseDAO().get(courseName).getTeacher()%></h3>
  <h4>Welcome to the course room.</h4>
  <p>Here you can find all the material we will need while going through this course.</p>

  </body>
</html>
