<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="mybean.data.Register"%>
<jsp:useBean id="register" type="mybean.data.Register" scope="request"/>
<html>
    <head><%@ include file="head.txt"%></head>
    <body bgcolor="red"><center>
        <p><font color="blue" size="4">
            以下是您（<jsp:getProperty name="register" property="logname"/>）曾注册的信息，
            你可以修改这些信息。
        </font></p>
        <font size="2"><form action="helpModifyMess" name="form">
            <table>
                <tr><td>性别：<jsp:getProperty name="register" property="sex"/></td>
                    <td><input type="radio" name="newSex" checked="o" value="男">男</td>
                    <td><input type="radio" name="newSex" value="女">女</td>
                </tr>
                <tr><td>会员年龄：</td>
                    <td><input type="text" name="newAge" value="<jsp:getProperty name="register" property="age"/>"></td>
                </tr>
                <tr><td>电子邮件：</td>
                    <td><input type="text" name="newEmail" value="<jsp:getProperty name="register" property="email"/>"></td>
                </tr>
                <tr><td>联系电话：</td>
                    <td><input type="text" name="newPhone" value="<jsp:getProperty name="register" property="phone"/>"></td>
                </tr>
            </table>
            <table>
                <tr><td>请输入您的个人简介</td></tr>
                <tr>
                    <td>
                        <textarea name="newMessage" rows="6" cols="30">
                            <jsp:getProperty name="register" property="message"/>
                        </textarea>
                    </td>
                </tr>
                <tr><td><input type="submit" name="g" value="提交修改"></td></tr>
                <tr><td><input type="reset" value="重置"></td></tr>
            </table>
        </form></font>
    </center></body>
</html>