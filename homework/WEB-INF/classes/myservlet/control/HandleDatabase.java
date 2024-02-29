package myservlet.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mybean.data.Login;
import mybean.data.MemberInform;
import mybean.data.ShowByPage;

public class HandleDatabase extends HttpServlet {
   // CachedRowSetImpl rowSet = null;
   CachedRowSet rowSet = null;

   @Override
   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      try {
         ClassLoader.getSystemClassLoader().loadClass("org.postgresql.Driver");
         // Class.forName("org.postgresql.Driver");
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }

   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      HttpSession session = request.getSession(true);
      Login login = (Login) session.getAttribute("login");
      boolean ok = true;
      if (login == null) {
         ok = false;
         response.sendRedirect("login.jsp");
      }
      if (ok == true) {
         continueDoPost(request, response);
      }
   }

   public void continueDoPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      HttpSession session = request.getSession(true);
      Connection con = null;
      StringBuffer presentPageResult = new StringBuffer();
      ShowByPage showBean = null;
      try {
         showBean = (ShowByPage) session.getAttribute("show");
         if (showBean == null) {
            showBean = new ShowByPage();
            session.setAttribute("show", showBean);
         }
      } catch (Exception exp) {
         showBean = new ShowByPage();
         session.setAttribute("show", showBean);
      }
      showBean.setPageSize(3);

      int showPage = Integer.parseInt(request.getParameter("showPage"));
      if (showPage > showBean.getPageAllCount()) {
         showPage = 1;
      }
      if (showPage <= 0) {
         showPage = showBean.getPageAllCount();
      }
      showBean.setShowPage(showPage);
      int pageSize = showBean.getPageSize();
      String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
      try {
         con = DriverManager.getConnection(uri, "postgres", "");
         Statement sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         ResultSet rs = sql.executeQuery("SELECT * FROM member");
         // rowSet = new CachedRowSetImpl();
         rowSet = RowSetProvider.newFactory().createCachedRowSet();
         rowSet.populate(rs);
         con.close();

         rowSet.last();
         int m = rowSet.getRow();

         int n = pageSize;
         int pageAllCount = ((m % n) == 0) ? (m / n) : (m / n + 1);
         showBean.setPageAllCount(pageAllCount);

         presentPageResult = show(showPage, pageSize, rowSet);
         showBean.setPresentPageResult(presentPageResult);
      } catch (SQLException exp) {
      }
      RequestDispatcher dispatcher = request.getRequestDispatcher("/showAllMember.jsp");
      dispatcher.forward(request, response);
   }

   // public StringBuffer show(int page, int pageSize, CachedRowSetImpl rowSet) {
   public StringBuffer show(int page, int pageSize, CachedRowSet rowSet) {
      StringBuffer str = new StringBuffer();
      try {
         rowSet.absolute((page - 1) * pageSize + 1);
         for (int i = 1; i <= pageSize; i++) {
            str.append("<tr>");
            str.append("<td>" + rowSet.getString(1) + "</td>");
            str.append("<td>" + rowSet.getString(3) + "</td>");
            str.append("<td>" + rowSet.getString(4) + "</td>");
            str.append("<td>" + rowSet.getString(5) + "</td>");
            str.append("<td>" + rowSet.getString(6) + "</td>");
            str.append("<td><textarea>" + rowSet.getString(7) + "</textarea></td>");
            String s = "<img src='member_img/" + rowSet.getString(8) + " 'width='100' height='100'/>";
            str.append("<td>" + s + "</td>");
            str.append("</tr>");
            rowSet.next();
         }
      } catch (SQLException exp) {
         str.append(exp.getMessage());
      }
      return str;
   }

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      HttpSession session = request.getSession(true);
      Login login = (Login) session.getAttribute("login");

      boolean ok = true;
      if (login == null) {
         ok = false;
         response.sendRedirect("login.jsp");
      }
      if (ok == true) {
         continueDoGet(request, response);
      }
   }

   public void continueDoGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      MemberInform inform = new MemberInform();
      request.setAttribute("inform", inform);
      String logname = request.getParameter("logname");
      Connection con = null;
      String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
      try {
         con = DriverManager.getConnection(uri, "postgres", "");
         Statement sql = con.createStatement();
         ResultSet rs = sql.executeQuery("SELECT * FROM member WHERE logname = '" + logname + "'");
         if (rs.next()) {
            inform.setLogname(rs.getString(1));
            inform.setSex(rs.getString(3));
            inform.setAge(rs.getInt(4));
            inform.setPhone(rs.getString(5));
            inform.setEmail(rs.getString(6));
            inform.setMessage(rs.getString(7));
            inform.setPic(rs.getString(8));
            inform.setBackNews("The member's information are as followed:");
         }
         con.close();
         RequestDispatcher dispatcher = request.getRequestDispatcher("/showLookedMember.jsp");
         dispatcher.forward(request, response);
      } catch (SQLException exp) {
         inform.setBackNews(exp.getMessage());
         System.out.println("ok1" + exp.getMessage());
      }
   }
}
