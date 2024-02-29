<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="mybean.data.UploadFile"%> 
<jsp:useBean id="upFile" type="mybean.data.UploadFile" scope="request" />
<html>
      <head>
            <%@ include file="head.txt" %>
            <style>
                  body{background-color:rgba(223, 229, 206, 0.964); text-align: center; font-size: 4px; color:blue}
            </style>
      </head>
<body>
       <BR> <jsp:getProperty name="upFile"  property="backNews"  />
<br><p>上传的文件名字：</p> <jsp:getProperty name="upFile" property="fileName" />
      保存后的文件名字：<jsp:getProperty name="upFile" property="savedFileName" />
<br> <img src=member_img/<jsp:getProperty name="upFile" property="savedFileName" />
      width=100 height=100>图像效果
     </img>
</body></html>