<%@ page contentType="text/html;charset=utf-8"%>
<html>
    <head><%@ include file="head.txt"%>
    <style>
        body{background:cornsilk; text-align: center;}
    </style>    
    </head>
    <body >
        <p>文件将被上传到E:/JspDemo/Chapter10/member_img中</p>
        <p>选择要上传的图像照片文件（名字不可以含有非ASCII码字符，比如汉字等：）</p>
        <br>
        <form action="helpUpload" method="post" enctype="multipart/form-data">
            <input type="file" name="fileName" size="40">
            <br><input type="submit" name="g" value="提交">
        </form>
    </body>
</html>  