<%@ page contentType="text/html;charset=utf-8"%>
<html>
    <head><%@ include file="head.txt"%></head>
    <body bgcolor="red"><font size="2"><center>
        <table border=2>
            <tr> <th>输入你的密码：</th></tr>
            <FORM action="helpModifyPassword" Method="post">
            <tr><td>当前密码：<input type="text" name="oldPassword"></td></tr>
            <tr><td>&nbsp;&nbsp;新密码：&nbsp;<input type="text" name="newPassword"></td></tr>
            </table>
            <BR><Input type=submit name="g" value="提交">
            </Form>
    </center></font></body>
</html>
    