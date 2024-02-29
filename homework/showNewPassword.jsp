<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="mybean.data.Password"%>
<jsp:useBean id="password" type="mybean.data.Password" scope="request"/>
<html>
    <head><%@ include file="head.txt"%>
    <style>
        body{background-color: pink;  font-size:4px; text-align: center;}
    </style>
    </head>
    <body >       
            <jsp:getProperty name="password" property="backNews"/>
            <br>你的新密码：<jsp:getProperty name="password" property="newPassword"/>
            <br>你的旧密码：<jsp:getProperty name="password" property="oldPassword"/>     
    </body>
</html>