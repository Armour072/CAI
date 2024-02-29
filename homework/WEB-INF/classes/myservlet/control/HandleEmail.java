package myservlet.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mybean.data.EmailMessage;
import mybean.data.Login;

public class HandleEmail extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    Connection con;
    PreparedStatement sql;
    HttpSession session = request.getSession(true);
    Login loginBean = (Login) session.getAttribute("loginBean");

    if (loginBean == null || !loginBean.getSuccess()) {
        // 用户未登录，重定向到登录页面
        response.sendRedirect("login.jsp");
        return;
    }

    String user = loginBean.getLogname();
    System.out.println("User: " + user); // 打印用户名称进行调试
    List<EmailMessage> emailList = new ArrayList<>();

    try {
        // 连接数据库
        Class.forName("org.postgresql.Driver");
        String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
        con = DriverManager.getConnection(uri, "postgres", "");

        // 查询收件箱中的邮件（发件人或接收人是登录用户）
        String condition = "SELECT * FROM mailbox WHERE (sender = ? OR recipient = ?) ORDER BY sendtime DESC";
        sql = con.prepareStatement(condition);
        sql.setString(1, user);
        sql.setString(2, user);
        ResultSet rs = sql.executeQuery();

        while (rs.next()) {
            EmailMessage email = new EmailMessage();
            email.setSender(rs.getString(1));
            email.setRecipient(rs.getString(2));
            email.setTopic(rs.getString(3));
            email.setMessage(rs.getString(4));
            email.setMailSerial(rs.getInt(5));
            email.setSendTime(rs.getTimestamp(6));
            email.setCancelSign(rs.getBoolean(7));

            emailList.add(email);
        }

        con.close();
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        // 处理异常情况
    }

    // 将邮件列表存储在请求属性中，供JSP页面使用
    request.setAttribute("emailList", emailList);

    // 转发到邮箱界面的JSP页面
    RequestDispatcher dispatcher = request.getRequestDispatcher("emailbox.jsp");
    dispatcher.forward(request, response);
}

}
