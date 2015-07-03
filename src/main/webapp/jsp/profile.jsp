<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.kpi.faculty.util.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <title>Profile</title>
    <link rel="stylesheet" href="../css/profile.css">
  </head>

  <%
    if (session.getAttribute("username") == null){
      response.sendRedirect(Config.getInstance().getValue(Config.LOGIN));
    }
  %>
  <body>
  <div class="row leftSide">
    <div class="info">
      <h2>${requestScope.get("user")}</h2>
      <h4>Username: ${requestScope.get("username")}</h4>
      <h4>Role: ${requestScope.get("role")}</h4>
    </div>

    <form action="/dispatcher" method="post">
      <input class="btn"type="submit" value="Log out"/>
      <input type="hidden" name="command" value="logout"/>
    </form>
    <hr>
  </div>

  <c:if test="${role == 'STUDENT'}">
    <div class="row rightSide">
      <div class="courses">
        <h3>Courses you have enrolled on</h3>
        <div class="enrolled">
          <c:forEach var="course" items="${courses}">
          <div class="row">
            <form class="inlined" action="/dispatcher" method="get">
              <input type="hidden" name="course" value="${course}"/>
              <input type="hidden" name="command" value="redirectToLobby">
              <input class="url" type="${course.name == null ? 'hidden' : 'submit'}" value="${course}"/>
            </form>
            <form class="inlined" action="/dispatcher" method="post">
              <input type="hidden" name="course" value="${course}"/>
              <input type="hidden" name="command" value="unenroll"/>
              <input class="btn" type="submit" value="Unenroll"/>
            </form>
          </div>
          </c:forEach>
        </div>
        <div class="row">
          <form action="/dispatcher" method="get">
            <input type="hidden" name="command" value="redirectToCourses">
            <input class="btn" type="submit" value="Find more"/>
          </form>
        </div>
      </div>

    <div class="row">
      <div class="feedback">
      <h3>Feedback</h3>
        <c:forEach var="student" items="${studentInfos}">
          <c:if test="${student.course != null}">
          <div class="row">
            <h4>Feedback from: ${student.course.teacher} on course ${student.course.name}</h4>
            <h5>Mark: ${student.mark}</h5>
            <p>${student.feedback}</p>
          </div>
          </c:if>
        </c:forEach>
      </div>
    </div>
  </div>
    </c:if>
  <c:if test="${role == 'TEACHER'}">
  <div class="row rightSide">
    <c:forEach var="course" items="${courses}">
    <div class="row">
      <form action="/dispatcher" method="get">
        <input type="hidden" name="course" value="${course}"/>
        <input type="hidden" name="command" value="redirectToLobby">
        <input class="url" type="submit" value="${course}"/>
      </form>
    </div>
    </c:forEach>
    <div class="row">
      <form action="/dispatcher" method="post">
        <h3>Add new course</h3>
        <label>Course name</label>
        <input type="text" placeholder="Course name" name="name"/>
        <input type="hidden" name="command" value="addCourse"/>
        <input class="btn" type="submit" value="Add"/>
      </form>
    </div>
  </div>
</div>
  </c:if>

    <script src="../js/jquery-2.1.4.min.js"></script>
    <script src="../js/profile.js"></script>
  </body>
</html>
