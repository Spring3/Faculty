<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

  <c:if test="${role == 'STUDENT'}">
    <div class="feedback">
    <h3>Feedback</h3>
      <c:forEach var="student" items="${studentInfos}">
        <c:if test="${student.course != null}">
          <h4>Feedback from: ${student.course.teacher} on course ${student.course.name}</h4>
          <h5>Mark: ${student.mark}</h5>
          <p>${student.feedback}</p>
          <br>
        </c:if>
      </c:forEach>
    </div>

    <div class="courses">
      <h3>Courses you have enrolled on</h3>
      <div class="enrolled">
        <c:forEach var="course" items="${courses}">
          <form action="/dispatcher" method="get">
            <input type="hidden" name="course" value="${course}"/>
            <input type="hidden" name="command" value="redirectToLobby">
            <input type="${course.name == null ? 'hidden' : 'submit'}" value="${course}"/>
          </form>
          <form action="/dispatcher" method="post">
            <input type="hidden" name="course" value="${course}"/>
            <input type="hidden" name="command" value="unenroll"/>
            <input type="submit" value="Unenroll"/>
          </form>
          <br>
        </c:forEach>
      </div>
      <br>
      <form action="/dispatcher" method="get">
        <input type="hidden" name="command" value="redirectToCourses">
        <input type="submit" value="Find more"/>
      </form>
    </div>
  </c:if>
  <c:if test="${role == 'TEACHER'}">
    <c:forEach var="course" items="${courses}">
    <form action="/dispatcher" method="get">
      <input type="hidden" name="course" value="${course}"/>
      <input type="hidden" name="command" value="redirectToLobby">
      <input type="submit" value="${course}"/>
    </form>
    <br>
    </c:forEach>
      <form action="/dispatcher" method="post">
        <h3>Add new course</h3>
        <label>Course name</label>
        <input type="text" placeholder="Course name" name="name"/>
        <br>
        <input type="hidden" name="command" value="addCourse"/>
        <input type="submit" value="Add"/>
      </form>
  </c:if>
  </body>
</html>
