<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    if (session.getAttribute("username") == null){
      response.sendRedirect(Config.getInstance().getValue(Config.LOGIN));
    }
  %>

  <c:forEach var="course" items="${availableCourses}">

    <form action="/dispatcher" method="get">
      <input type="hidden" name="command" value="redirectToLobby"/>
      <input type="hidden" name="course" value="${course}"/>
      <input type="submit" value="${course} (${course.teacher.name})"/>
    </form>
    <form action="/dispatcher" method="post">
      <input type="hidden" name="name" value="${course}"/>
      <input type="hidden" name="command" value="enroll"/>
      <input type="submit" value="Enroll"/>
      <br>
    </form>

  </c:forEach>
  </body>
</html>
