<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="mybean.data.ShowProducts" %>
<%@ page import="java.util.List" %>

<html>
    <link rel="stylesheet" type="text/css" href="showProducts.css">
    <%@ include file="head.txt" %>
    <body bgcolor="pink">

        <center>
            <p>显示商品信息</p>
            <br>当前显示的内容是：

            <table border="2">
                <tr>
                    <th>商品名</th>
                    <th>图片</th>
                    <th>价格</th>
                </tr>
                <%= request.getAttribute("presentPageResult") %>
            </table>


        </center>
    </body>
</html>
