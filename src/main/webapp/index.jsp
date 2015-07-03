<%@ page import="java.util.Locale" %>
<%@ page import="com.kpi.faculty.util.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<%
  Locale.setDefault(Locale.ENGLISH);
  if (session.getAttribute("username") == null){
  }
  else {
    response.sendRedirect(Config.getInstance().getValue(Config.PROFILE));
  }
%>
  <head>
    <title>Authorization</title>
    <link href="css/index.css" rel="stylesheet">
  </head>
  <body>
    <div class="loginForm">
      <p class="title">Login</p>
      <hr>
    <%
      String errorMessage = (String)request.getAttribute("error");
      if (errorMessage != null){
    %>
      <p class="error"><%= errorMessage %></p>
    <%
      }
    %>
    <form action="/dispatcher" method="post">
      <div class="row">
        <label>Username: </label>
        <input type="text" name="username" placeholder="Username">
      </div>
      <div class="row">
        <label>Password: </label>
        <input type="password" name="password" placeholder="Password">
      </div>
      <div class="row buttons">
        <input class="btn" type="hidden" name="command" value="login">
        <input class="btn" type="submit" value="Sign in">
        <a href="./jsp/reg.jsp"><input class="btn" type="button" value="Sign up"/></a>
      </div>
    </form>
  </div>


  <script src="js/jquery-2.1.4.min.js"></script>
  <script src="js/index.js"></script>
  </body>
</html>
