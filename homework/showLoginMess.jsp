<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="mybean.data.Login"%>
<jsp:useBean id="login" type="mybean.data.Login" scope="session"/>
<html>
    <head><%@ include file="head.txt"%></head>
    <body bgcolor="pink">
        <center><font>
            <br><jsp:getProperty name="login" property="backNews"/>
        </font>
        <% if(login.getSuccess()==true){
        %>
            <br>登录会员名称：<jsp:getProperty name="login" property="logname"/>
        <%}
            else{
        %><br>登录会员名称：<jsp:getProperty name="login" property="logname"/>
          <br>登录会员密码：<jsp:getProperty name="login" property="password"/>
        <% }
        %>
            </font>
    </center>
    </body>
</html>
