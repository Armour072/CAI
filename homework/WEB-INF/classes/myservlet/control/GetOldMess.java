package myservlet.control;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mybean.data.Login;
import mybean.data.Register;

public class GetOldMess extends HttpServlet {
   @Override
   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      try {
         Class.forName("org.postgresql.Driver");
      } catch (Exception e) {
      }
   }
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      HttpSession session = request.getSession(true);
      Login login = (Login)session.getAttribute("login");
      Connection con = null;
      String logname = login.getLogname();
      Register register = new Register();
      request.setAttribute("register", register);
      String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
          try {
         con = DriverManager.getConnection(uri, "postgres","xinguzhang01LINK");
         Statement sql = con.createStatement();
         ResultSet rs = sql.executeQuery("SELECT * FROM member WHERE logname='" + logname + "'");
         if (rs.next()) {
            register.setLogname(rs.getString(1));
            register.setSex(rs.getString(3));
            register.setAge(rs.getInt(4));
            register.setPhone(rs.getString(5));
            register.setEmail(rs.getString(6));
            register.setMessage(rs.getString(7));
            register.setBackNews("您原来的注册信息:");
         }
      } catch (SQLException exp) {
         register.setBackNews(exp.getMessage());
      }
      RequestDispatcher dispatcher = request.getRequestDispatcher("/inputModifyMess.jsp");// 转发
      dispatcher.forward(request, response);
   }
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doPost(request, response);
   }
}
