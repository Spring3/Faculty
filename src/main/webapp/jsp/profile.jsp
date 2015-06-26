<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/25/2015
  Time: 11:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Profile</title>
  </head>
  <h2>Welcome, <%= request.getAttribute("username")%></h2>
  <body>
  <br>
    <form action="/dispatcher" method="post">
      <input type="submit" value="Log out"/>
      <input type="hidden" name="command" value="logout"/>
    </form>


  </body>
</html>
