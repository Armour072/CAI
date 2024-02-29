<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="mybean.data.Register"%>
<jsp:useBean id="register" type="mybean.data.Register" scope="request"/>
<html>
    <head>
        <%@ include file="head.txt"%>
    </head>
    <body bgcolor="red">
        <center>
            <font size="4" color="blue">
                <br><jsp:getProperty name="register" property="backNews"/>
            </font>
            <font size="2">
                <table>
                    <tr><td>注册的会员名称：</td><td><jsp:getProperty name="register" property="logname"/></td></tr>
                    <tr><td>注册的性别：</td><td><jsp:getProperty name="register" property="sex"/></td></tr>
                    <tr><td>注册的会员年龄：</td><td><jsp:getProperty name="register" property="age"/></td></tr>
                    <tr><td>注册的电子邮件：</td><td><jsp:getProperty name="register" property="email"/></td></tr>
                    <tr><td>注册的联系电话：</td><td><jsp:getProperty name="register" property="phone"/></td></tr>
                </table>
                <table>
                    <tr><td>您输入的个人简介：</td></tr>
                    <tr><td><textarea name="message" rows="6" cols="30"><jsp:getProperty name="register" property="message"/></textarea></td></tr>
                </table>
            </font>
        </center>
    </body>
</html>