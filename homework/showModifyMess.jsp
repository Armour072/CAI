<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="mybean.data.ModifyMessage"%>
<jsp:useBean id="modify" type="mybean.data.ModifyMessage" scope="request"/>
<html>
    <head><%@ include file="head.txt"%></head>
    <body bgcolor="pink">
        <font><center>
            <jsp:getProperty name="modify" property="backNews"/>
            <br>您修改的信息如下：
            <br>新性别：<jsp:getProperty name="modify" property="newSex"/>
            <br>新年龄：<jsp:getProperty name="modify" property="newAge"/>
            <br>新电话：<jsp:getProperty name="modify" property="newPhone"/>
            <br>新email：<jsp:getProperty name="modify" property="newEmail"/>
            <br>新简历：<jsp:getProperty name="modify" property="newMessage"/>
        </center></font>
    </body>
</html>