package myservlet.control;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mybean.data.Login;

public class HandleLogin extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        }
    }

    public String handleString(String s) {
        try {
            byte bb[] = s.getBytes("utf-8");
            s = new String(bb);
        } catch (Exception ee) {
        }
        return s;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con;
        PreparedStatement sql;
        Login loginBean = null;
        String backNews = "";
        HttpSession session = request.getSession(true);
        try {
            loginBean = (Login) session.getAttribute("login");
            if (loginBean == null) {
                loginBean = new Login();
                session.setAttribute("login", loginBean);
            }
        } catch (Exception ee) {
            loginBean = new Login();
            session.setAttribute("login", loginBean);
        }
        String logname = request.getParameter("logname").trim(),
                password = request.getParameter("password").trim();
        boolean ok = loginBean.getSuccess();
        logname = handleString(logname);
        password = handleString(password);
        if (ok == true && logname.equals(loginBean.getLogname())) {
            backNews = logname + "已经登录了";
            loginBean.setBackNews(backNews);
        } else {
            String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
            boolean boo = (logname.length() > 0) && (password.length() > 0);
            try {
                con = DriverManager.getConnection(uri, "postgres", "xinguzhang01LINK");
                String condition = "select * from member where logname =? and password =MD5(?)";
                sql = con.prepareStatement(condition);
                if (boo) {
                    sql.setString(1, logname);
                    sql.setString(2, password);
                    ResultSet rs = sql.executeQuery();
                    boolean m = rs.next();
                    if (m == true) {
                        backNews = "登录成功";
                        loginBean.setBackNews(backNews);
                        loginBean.setSuccess(true);
                        loginBean.setLogname(logname);
                        loginBean.setSuccess(true);
                        //添加登录状态到session中，用于邮件登录验证
                        session.setAttribute("loginBean", loginBean);
                    } else {
                        backNews = "您输入的用户名不存在，或密码不般配";
                        loginBean.setBackNews(backNews);
                        loginBean.setSuccess(false);
                        loginBean.setLogname(logname);
                        loginBean.setPassword(password);
                    }
                } else {
                    backNews = "您输入的用户名不存在，或密码不般配";
                    loginBean.setBackNews(backNews);
                    loginBean.setSuccess(false);
                    loginBean.setLogname(logname);
                    loginBean.setPassword(password);
                }
                con.close();
            } catch (SQLException exp) {
                backNews = "" + exp;
                loginBean.setBackNews(backNews);
                loginBean.setSuccess(false);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("showLoginMess.jsp");
        dispatcher.forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}

