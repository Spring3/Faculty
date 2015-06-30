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
    if (session.getAttribute("username") == null){
      response.sendRedirect(Config.getInstance().getValue(Config.LOGIN));
    }
  %>
  <body style="text-align:center;">
  <br>
    <form action="/dispatcher" method="post">
      <input type="submit" value="Log out"/>
      <input type="hidden" name="command" value="logout"/>
    </form>

  <div class="info">
    <h2>${requestScope.get("user")}</h2>
    <h4>Username: ${requestScope.get("username")}</h4>
    <h4>Role: ${requestScope.get("role")}</h4>
    <br>
  </div>

  <%
    if (request.getAttribute("role").equals("STUDENT")){
  %>
    <div class="feedback">
    <h3>Feedback</h3>
    <%
      int i = 0;
      for (Map.Entry<String, String> feedback : ((Map<String, String>) request.getAttribute("markAndFeedback")).entrySet()){
    %>
    <h4>Feedback from: <%= ((List<Course>)request.getAttribute("courses")).get(i).getTeacher() + " on course " + ((List<Course>)request.getAttribute("courses")).get(i).getName()%></h4>
    <h5>Mark: <%= feedback.getKey()%></h5>
    <p><%= feedback.getValue()%></p>
    <br>
    <%
          i ++;
      }
    %>
    </div>

    <div class="courses">
      <h3>Courses you have enrolled on</h3>
      <div class="enrolled">
        <%
          for (Course course : ((List<Course>)request.getAttribute("courses"))){
        %>
          <form action="/dispatcher" method="get">
            <input type="hidden" name="course" value="<%=course.getName()%>"/>
            <input type="hidden" name="command" value="redirectToLobby">
            <input type="submit" value="<%=course.getName()%>"/>
          </form>
          <form action="/dispatcher" method="post">
            <input type="hidden" name="course" value="<%=course.getName()%>"/>
            <input type="hidden" name="command" value="unenroll"/>
            <input type="submit" value="Unenroll"/>
          </form>
          <br>
        <%
          }
        %>
      </div>
      <br>
      <form action="/dispatcher" method="get">
        <input type="hidden" name="command" value="redirectToCourses">
        <input type="submit" value="Find more"/>
      </form>
    </div>

  <%
    }
    else{
      for (Course course : (List<Course>) request.getAttribute("courses")){
  %>

  <form action="/dispatcher" method="get">
    <input type="hidden" name="course" value="<%=course.getName()%>"/>
    <input type="hidden" name="command" value="redirectToLobby">
    <input type="submit" value="<%=course.getName()%>"/>
  </form>
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
