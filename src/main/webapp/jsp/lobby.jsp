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
    <title>Authorization</title>
  </head>
  <h2>Welcome to our service. Please, sign in to proceed.</h2>
  <body>

    <form action="/" method="post">
      <input type="text" name="username" placeholder="Username"/>
      <input type="password" name="password" placeholder="Password"/>
      <input type="hidden" name="command" value="login"/>
      <input type="submit" value="Sign in"/>
    </form>

    <form action="/" method="get">
      <input type="hidden" name="command" value="register"/>
      <input type="submit" value="Sign up"/>
    </form>

  </body>
</html>
