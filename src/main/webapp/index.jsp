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
  </head>
  <h2>Welcome to our service. Please, sign in to proceed.</h2>
  <body style="text-align:center;">
    <br>
    <%
      String errorMessage = (String)request.getAttribute("error");
      if (errorMessage != null){
    %>
      <p style="color:red"><%= errorMessage %></p>
    <%
      }
    %>
    <form action="/dispatcher" method="post">
      <label>Username: </label>
      <input type="text" name="username" placeholder="Username"/>
      <br>
      <label>Password: </label>
      <input type="password" name="password" placeholder="Password"/>
      <br>
      <input type="hidden" name="command" value="login"/>
      <br>
      <input type="submit" value="Sign in"/>
      <a href=""><input type="button" value="Sign up"/></a>
    </form>



  </body>
</html>
