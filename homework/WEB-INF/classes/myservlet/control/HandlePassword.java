package myservlet.control;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mybean.data.Login;
import mybean.data.Password;

public class HandlePassword extends HttpServlet {
   @Override
   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      try {
         Class.forName("org.postgresql.Driver");
      } catch (Exception e) {
      }
   }
@Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      HttpSession session = request.getSession(true);
      Login login = (Login) session.getAttribute("login"); // 获取用户登录时的Javabean
      boolean ok = true;
      if (login == null) {
         ok = false;
         response.sendRedirect("login.jsp"); // 重定向到登录页面
      }
      if (ok == true) {
         continueWork(request, response);
      }
   }

   public void continueWork(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
          {
      HttpSession session = request.getSession(true);
      Login login = (Login) session.getAttribute("login");
      Connection con = null;
      String logname = login.getLogname();
      Password passwordBean = new Password();
      request.setAttribute("password", passwordBean);
      String oldPassword = request.getParameter("oldPassword");
      String newPassword = request.getParameter("newPassword");
      String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
      String sqlstr="SELECT logname,password FROM member WHERE logname=? AND password=MD5(?)";
      String updateString="UPDATE member SET password=MD5(?) WHERE logname=? AND password=MD5(?)";
      try {
              con = DriverManager.getConnection(uri, "postgres", "xinguzhang01LINK");
         //    PreparedStatement pst=con.prepareStatement(sqlstr);
         //   pst.setString(1, logname);
         //   pst.setString(2, oldPassword);
         //   ResultSet rs=pst.executeQuery();
         //    if (rs.next()) {
            PreparedStatement pst1=con.prepareStatement(updateString);
                 pst1.setString(1,newPassword);
                 pst1.setString(2,logname);
                 pst1.setString(3,oldPassword);
             int m = pst1.executeUpdate();
           // rs.close();
           //  con.close();
             if (m == 1) {
               passwordBean.setBackNews("密码更新成功");
               passwordBean.setOldPassword(oldPassword);
               passwordBean.setNewPassword(newPassword);
            // } else {
            //    passwordBean.setBackNews("密码更新失败");
            // }
         } else {
            passwordBean.setBackNews("原密码不对，不能更新");
         }
         con.close();
      } 
      catch (SQLException exp) {
         passwordBean.setBackNews("密码更新失败" + exp.getMessage());
         System.out.println(exp.getMessage());
      }
      RequestDispatcher dispatcher = request.getRequestDispatcher("/showNewPassword.jsp");// 转发
      dispatcher.forward(request, response);
   }
@Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doPost(request, response);
   }
}
