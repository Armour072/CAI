<%@ page contentType="text/html;charset=utf-8"%>
<html>
    <head>
        <%@ include file="head.txt"%>
    </head>
    <body bgcolor="pink">
        <font size="2"><center>
            
            <table border=2>
                <tr> <th>请您登录</th></tr>
                <FORM action="helpLogin" Method="post">
                <tr><td>登录名称：<Input type=text name="logname"></td></tr>
                <tr><td>输入密码：<Input type=password name="password"></td></tr>
                </table>
                <BR><Input type=submit name="g" value="提交">
                </Form>
            </center>
        </font>
    </body>
</html>