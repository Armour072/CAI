<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="mybean.data.MemberInform"%>
<jsp:useBean id="inform" type="mybean.data.MemberInform" scope="request"/>
<html>
    <body bgcolor="pink">
        <head><%@ include file="head.txt" %></head>
        <center><font size="4" color="blue"><br>
            <table border="2">
                <tr>
                    <th>会员名</th><th>性别</th><th>年龄</th><th>电话</th>
                    <th>email</th><th>简历</th><th>照片</th>
                </tr>
                <tr>
                    <td><jsp:getProperty name="inform" property="logname"/></td>
                    <td><jsp:getProperty name="inform" property="sex"/></td>
                    <td><jsp:getProperty name="inform" property="age"/></td>
                    <td><jsp:getProperty name="inform" property="phone"/></td>
                    <td><jsp:getProperty name="inform" property="email"/></td>
                    <td><jsp:getProperty name="inform" property="message"/></td>
                    <!-- <td><img width="100px" src=<%="./member_img/"+inform.getPic()%> ></td> -->
                    <td><img width="100px" src=./member_img/<jsp:getProperty name="inform" property="pic"/> alt="照片没有上传" ></td>
                    
                </tr>
            </table>
        </font></center>
    </body>
</html>