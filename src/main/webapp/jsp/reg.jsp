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
    <title>Registration</title>
  </head>
  <body>

    <%
      String error = (String)request.getAttribute("error");
      if (error != null) {
    %>
    <p style="color: red"><%= error %></p>
    <%
      }
    %>
    <form action="/dispatcher" method="post">
      <br>
      <label>Username: </label>
      <input type="text" name="username" placeholder="Username"/>
      <br>
      <label>Password: </label>
      <input type="password" name="password1" placeholder="Password"/>
      <br>
      <label>Password repeat: </label>
      <input type="password" name="password2" placeholder=""/>
      <br>
      <label>Name: </label>
      <input type="text" name="name" placeholder="Name"/>
      <br>
      <label>Last name: </label>
      <input type="text" name="lastname" placeholder="Last Name"/>
      <br>
      <label for="teacher">I am a teacher</label>
      <input type="checkbox" name="teacher" value="teacher" />

      <br>
      <input type="hidden" name="command" value="register"/>
      <br>
      <input type="submit" value="Sign up"/>
    </form>


  </body>
</html>
