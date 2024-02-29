<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="mybean.data.EmailMessage"%>

<html>

    <title>Email Inbox</title>
    <link rel="stylesheet" type="text/css" href="emailbox.css">
    <%@ include file="head.txt"%>

<body>
    <h1>Email Inbox</h1>
    <div class="new-email-button">
        <button class="button" onclick="location.href='sendMessage.jsp'"><span>New Email</span></button>
    </div>
    <table>
        <tr>
            <th>Sender</th>
            <th>Recipient</th>
            <th>Topic</th>
            <th>Message</th>
            <th>Send Time</th>
            <th>Action</th>
        </tr>
        <% 
            List<mybean.data.EmailMessage> emailList = (List<mybean.data.EmailMessage>) request.getAttribute("emailList");
            if (emailList != null) {
                for (mybean.data.EmailMessage email : emailList) {
        %>
            <tr>
                <td><%= email.getSender() %></td>
                <td><%= email.getRecipient() %></td>
                <td><%= email.getTopic() %></td>
                <td><%= email.getMessage() %></td>
                <td><%= email.getSendTime() %></td>
                <td><a href="#">Revoke</a></td>
            </tr>
        <% 
                }
            }
        %>
    </table>
</body>
</html>
