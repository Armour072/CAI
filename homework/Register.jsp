<%@ page contentType="text/html;charset=utf-8" %>
<HTML><HEAD><%@ include file="head.txt" %></HEAD>
<BODY bgcolor="pink"><Font size=2>
<CENTER>
<FORM action="helpRegister" name=form>
   <BR>输入您的信息，会员名字必须由字母和数字组成，带*号项必须填写
<table>
   <tr><td>会员名称:</td><td><Input type=text name="logname">*</td></tr>
   <tr><td>设置密码:</td><td><Input type=password name="password">*</td></tr>
   <tr><td>性别:</td>
      <td><Input type=radio name="sex"  value="男">男
         <input type="radio" name="sex" value="女" checked="0">女
          </td>
    </tr>
   <tr><td>会员年龄:</td><td><Input type=text name="age" value="0"></td></tr>
   <tr><td>电子邮件:</td><td><Input type=text name="email"></td></tr>
   <tr><td>联系电话:</td><td><Input type=text name="phone"></td></tr>
 </table>
  <table>
    <tr><td>请输入你的个人简介：</td></tr>
    <tr><td><TextArea name="message" Rows="6" Cols="30"></TextArea></td></tr>
    <tr><td><Input type=submit name="g" value="提交"></td> </tr>
</table>
</Form></CENTER>
</Body></HTML>