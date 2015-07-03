<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.kpi.faculty.util.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <title>Courses</title>
    <link rel="stylesheet" href="../css/courses.css">
  </head>
  <body>
  <h3>Available courses</h3>
  <%
    if (session.getAttribute("username") == null){
      response.sendRedirect(Config.getInstance().getValue(Config.LOGIN));
    }
  %>

  <c:forEach var="course" items="${availableCourses}">

    <div class="row">
      <form class="inlined" action="/dispatcher" method="get">
        <input type="hidden" name="command" value="redirectToLobby"/>
        <input type="hidden" name="course" value="${course}"/>
        <input class="url" type="submit" value="${course} (${course.teacher.name})"/>
      </form>
      <form class="inlined" action="/dispatcher" method="post">
        <input type="hidden" name="name" value="${course}"/>
        <input type="hidden" name="command" value="enroll"/>
        <input class="btn" type="submit" value="Enroll"/>
      </form>
    </div>

  </c:forEach>


    <script src="../js/jqeury-2.1.4.min.js"></script>
    <script src="../js/courses.js"></script>
  </body>
</html>
