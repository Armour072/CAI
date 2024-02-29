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
import mybean.data.ModifyMessage;

public class HandleModifyMess extends HttpServlet {
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
    Login login = (Login) session.getAttribute("login");// 获取用户登录时的Javabean
    boolean ok = true;
    if (login == null) {
      ok = false;
      response.sendRedirect("login.jsp"); // 重定向到登录页面
    }
    if (ok == true) {
      continueDoPost(request, response);
    }
  }

  public void continueDoPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    Login login = (Login) session.getAttribute("login");
    String logname = login.getLogname();
    Connection con;
    PreparedStatement sql;
    ModifyMessage modify = new ModifyMessage();
    request.setAttribute("modify", modify);
    String sex = request.getParameter("newSex").trim();
    String email = request.getParameter("newEmail").trim();
    String phone = request.getParameter("newPhone").trim();
    String message = request.getParameter("newMessage").trim();
    int age = Integer.parseInt(request.getParameter("newAge").trim());
    String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
    String backNews = "";
    try {
      con = DriverManager.getConnection(uri, "postgres", "xinguzhang01LINK");
      String updateCondition = "UPDATE member SET sex=?,age=?,phone=?,email=?,message=? where logname=?";
      sql = con.prepareStatement(updateCondition);
      sql.setString(1, sex);
      sql.setInt(2, age);
      sql.setString(3, phone);
      sql.setString(4, email);
      sql.setString(5, message);
      sql.setString(6, logname);
      int m = sql.executeUpdate();
      if (m == 1) {
        backNews = "修改信息成功";
        modify.setBackNews(backNews);
        modify.setLogname(logname);
        modify.setNewAge(age);
        modify.setNewSex(sex);
        modify.setNewEmail(email);
        modify.setNewPhone(phone);
        modify.setNewMessage(message);
      } else {
        backNews = "信息填写不完整或信息中有非法字符";
        modify.setBackNews(backNews);
      }
      con.close();
    } catch (SQLException exp) {
      modify.setBackNews(exp.getMessage());
    }
    RequestDispatcher dispatcher = request.getRequestDispatcher("/showModifyMess.jsp");// 转发
    dispatcher.forward(request, response);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }
}
