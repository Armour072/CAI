<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.List" %>

<html>
    <%@ include file="head.txt"%>

    <title>Email Inbox</title>
    <link rel="stylesheet" type="text/css" href="emailform.css">


<body>
    <h1>Send Email</h1>
    <div class="form-container">
        <form action="helpSendMessage" method="post">
            <label for="recipient">Recipient:</label>
            <input type="text" id="recipient" name="recipient" required><br>
            <label for="topic">Topic:</label>
            <input type="text" id="topic" name="topic" required><br>
            <label for="message">Message:</label>
            <textarea id="message" name="message" rows="5" required></textarea><br>
            <input type="submit" value="Send">
        </form>
    </div>
</body>
</html>
