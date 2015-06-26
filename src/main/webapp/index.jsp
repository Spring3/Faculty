<%@ page import="java.util.Locale" %>
<%@ page import="com.kpi.faculty.util.Config" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/25/2015
  Time: 11:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
  request.setCharacterEncoding("UTF-8");
  Locale.setDefault(Locale.ENGLISH);
  if (session.getAttribute("userEmail") == null){
  }
  else {
    response.sendRedirect(Config.getInstance().getValue(Config.PROFILE));
  }
%>
  <head>
    <title>Authorization</title>
  </head>
  <h2>Welcome to our service. Please, sign in to proceed.</h2>
  <body>
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
      <input type="text" name="username" placeholder="Username"/>
      <br>
      <input type="password" name="password" placeholder="Password"/>
      <br>
      <input type="hidden" name="command" value="login"/>
      <br>
      <input type="submit" value="Sign in"/>
      <a href="/jsp/reg.jsp"><input type="button" value="Sign up"/></a>
    </form>



  </body>
</html>
