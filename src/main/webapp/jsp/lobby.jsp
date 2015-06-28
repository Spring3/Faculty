<%@ page import="com.kpi.faculty.dao.CourseDAO" %>
<%@ page import="com.kpi.faculty.models.Human" %>
<%@ page import="com.kpi.faculty.dao.HumanDAO" %>
<%@ page import="com.kpi.faculty.models.Course" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <%
      HumanDAO humanDAO = new HumanDAO();
      CourseDAO courseDAO = new CourseDAO();
      String courseName = request.getParameter("course");
      Human currentUser = humanDAO.get((String) session.getAttribute("username"));
      Course currentCourse = courseDAO.get(courseName);
    %>
    <title><%= courseName%></title>
  </head>
  <body style="text-align:center;">

  <h3><%= courseName%></h3>
  <h3>Teacher: <%= currentCourse.getTeacher()%></h3>
  <h4>Welcome to the course room.</h4>
  <p>Here you can find all the material we will need while going through this course.</p>

  <%
    if(currentUser.getRole() == Human.Role.TEACHER){
  %>
  <h3>Enrolled students:</h3>
  <%
      for(Human student: humanDAO.getAllStudentsFor(currentCourse)){
        String feedback = courseDAO.getFeedBackForStudent(student, currentCourse, null);
  %>

    <form action="/dispatcher" method="post">
      <label><b><%= student %></b>  </label>
      <input type="text" name="feedback" placeholder="Feedback" value="<%= feedback == null ? "" : feedback %>"/>
      <label>Mark: </label>
      <select name="mark" >
        <option value="A">A</option>
        <option value="B">B</option>
        <option value="C">C</option>
        <option value="D">D</option>
        <option value="E">E</option>
        <option value="F">F</option>
      </select>
      <input type="hidden" name="course" value="<%=currentCourse.getName()%>"/>
      <input type="hidden" name="student" value="<%=student.getUsername()%>"/>
      <input type="hidden" name="command" value="feedback"/>
      <input type="submit" value="Submit"/>
    </form>

  <%
      }
    }
  %>

  </body>
</html>
