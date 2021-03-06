<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.kpi.faculty.util.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <%
      if (session.getAttribute("username") == null){
        response.sendRedirect(Config.getInstance().getValue(Config.LOGIN));
      }
    %>
    <title>${course}</title>
    <link rel="stylesheet" href="../css/lobby.css">
  </head>
  <body>

  <div class="row centered">
    <h3>${course}</h3>
    <h3>Teacher: ${course.teacher}</h3>
    <h4>Welcome to the course room.</h4>
    <p>Here you can find all the material we will need while going through this course.</p>
  </div>

  <c:if test="${role == 'TEACHER'}">
  <h3 class="centered">Enrolled students:</h3>
    <c:forEach var="student" items="${studentInfos}">
    <div class="row centered">
        <form action="/dispatcher" method="post">
          <label><b>${student.username}</b></label>
          <input type="text" name="feedback" placeholder="Feedback" value="${student.feedback == null ? "" : student.feedback}"/>
          <label>Mark: </label>
          <select name="mark" >
            <option value="A" ${student.mark.equals('A') ? 'selected="selected"' : ''}>A</option>
            <option value="B" ${student.mark.equals('B') ? 'selected="selected"' : ''}>B</option>
            <option value="C" ${student.mark.equals('C') ? 'selected="selected"' : ''}>C</option>
            <option value="D" ${student.mark.equals('D') ? 'selected="selected"' : ''}>D</option>
            <option value="E" ${student.mark.equals('E') ? 'selected="selected"' : ''}>E</option>
            <option value="F" ${student.mark.equals('F') ? 'selected="selected"' : ''}>F</option>
          </select>
          <input type="hidden" name="course" value="${course}"/>
          <input type="hidden" name="student" value="${student.username}"/>
          <input type="hidden" name="command" value="feedback"/>
          <input class="btn" type="submit" value="Submit"/>
        </form>
      </div>
    </c:forEach>

  </c:if>

    <script src="../js/jqeury-2.1.4.min.js"></script>
    <script src="../js/lobby.js"></script>

  </body>
</html>
