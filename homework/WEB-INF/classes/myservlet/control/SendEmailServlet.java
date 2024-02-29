package myservlet.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mybean.data.EmailMessage;
import mybean.data.Login;


public class SendEmailServlet extends HttpServlet {

    // Database connection details
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/comehere";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "xinguzhang01LINK";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Login loginBean = (Login) session.getAttribute("loginBean");

        // Check if the user is logged in
        if (loginBean != null && loginBean.getSuccess()) {
            String sender = loginBean.getLogname();
            String recipient = request.getParameter("recipient");
            String topic = request.getParameter("topic");
            String message = request.getParameter("message");

            // Set the send time and cancel sign
            Timestamp sendTime = new Timestamp(System.currentTimeMillis());
            boolean cancelSign = false;

            // Generate the mail serial (email ID)
            int mailSerial = generateMailSerial();

            // Create the EmailMessage object
            EmailMessage email = new EmailMessage(sender, recipient, topic, sendTime, message, mailSerial, cancelSign);

            // Add the email to the mailbox table in the database
            try {
                if (addEmailToMailbox(email)) {
                    response.sendRedirect("emailbox.jsp");
                } else {
                    // Handle database insertion error
                    response.getWriter().println("Failed to send email. Please try again.");
                }
            } catch (ParseException e) {
                // Handle the ParseException here
                e.printStackTrace(); // or log the exception
            }
        } else {
            // User is not logged in, redirect to login page
            response.sendRedirect("login.jsp");
        }
    }

    private int generateMailSerial() {
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();

        // 生成随机数（范围可根据需要进行调整）
        int random = (int) (Math.random() * 10000);

        // 组合时间戳和随机数来生成唯一的邮件编号
        int mailSerial = (int) (timestamp + random);

        return mailSerial;
    }


    private boolean addEmailToMailbox(EmailMessage email) throws ParseException {
        try {
            // Create a database connection
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Prepare the SQL statement to insert the email into the mailbox table
            String sql = "INSERT INTO mailbox (sender, recipient, topic, sendtime, message, mail_serial, cancel_siign) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email.getSender());
            statement.setString(2, email.getRecipient());
            statement.setString(3, email.getTopic());

            // Convert the sendTime string to a Timestamp object
            String sendTime = email.getSendTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(sendTime);
            Timestamp timestamp = new Timestamp(date.getTime());

            statement.setTimestamp(4, timestamp);
            statement.setString(5, email.getMessage());
            statement.setInt(6, email.getMailSerial());
            statement.setBoolean(7, email.isCancelSign());

            // Execute the SQL statement
            int rowsInserted = statement.executeUpdate();

            // Close the database connection
            statement.close();
            conn.close();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
