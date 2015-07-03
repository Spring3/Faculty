<%@ page import="com.kpi.faculty.util.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
    <title>Registration</title>
    <link rel="stylesheet" href="../css/reg.css">
  </head>
  <body>
    <div class="formReg">
      <p class="title">Registration</p>
      <hr>
    <%
      if (session.getAttribute("username") != null){
        response.sendRedirect(Config.getInstance().getValue(Config.PROFILE));
      }
      String error = (String)request.getAttribute("error");
      if (error != null) {
    %>
      <p class="error"><%= error %></p>
    <%
      }
    %>
    <form action="/dispatcher" method="post">
      <div class="row">
        <label>Username: </label>
        <input type="text" name="username" placeholder="Username"/>
      </div>
      <div class="row">
        <label>Password: </label>
        <input type="password" name="password1" placeholder="Password"/>
      </div>
      <div class="row">
        <label>Password repeat: </label>
        <input type="password" name="password2" placeholder=""/>
      </div>
      <div class="row">
        <label>Name: </label>
        <input type="text" name="name" placeholder="Name"/>
      </div>
      <div class="row">
        <label>Last name: </label>
        <input type="text" name="lastname" placeholder="Last Name"/>
      </div>
      <div class="row inlined">
        <label class="teacher" for="teacher">I am a teacher</label>
        <input type="checkbox" name="teacher" value="teacher"/>
      </div>
      <div class="row buttons">
        <input type="hidden" name="command" value="register"/>
        <input class="btn" type="submit" value="Sign up"/>
      </div>
    </form>

    </div>
    <script src="../js/jquery-2.1.4.min.js"></script>
    <script src="../js/reg.js"></script>
  </body>
</html>
