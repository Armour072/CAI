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
import mybean.data.Register;
public class HandleRegister extends HttpServlet
 {
    public void init(ServletConfig config) throws ServletException
    { super.init(config);
      try {  Class.forName("org.postgresql.Driver");
          }
       catch(Exception e){} 
    }
  @Override
   public  void  doPost(HttpServletRequest request,HttpServletResponse response) 
                        throws ServletException,IOException
    {   Connection con; 
        PreparedStatement sql; 
        Register reg=new Register(); 
        request.setAttribute("register",reg);
         String logname = request.getParameter("logname").trim();
         String password = request.getParameter("password").trim();
         String sex = request.getParameter("sex").trim();
         String email = request.getParameter("email").trim();
         String phone = request.getParameter("phone").trim();
         String message = request.getParameter("message");
         int age = Integer.parseInt(request.getParameter("age").trim());
         String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
        if(logname==null)
           logname="";
        if(password==null)
           password="";
        boolean isLD=true;
        for(int i=0;i<logname.length();i++)
        {  char c=logname.charAt(i);
           if(!((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0'))) 
             isLD=false;
        } 
        boolean boo=logname.length()>0&&password.length()>0&&isLD;
        String backNews="";
        try{ 
         con = DriverManager.getConnection(uri, "postgres", "xinguzhang01LINK");
         String insertCondition = "INSERT INTO member VALUES (?,MD5(?),?,?,?,?,?,?)";
         sql = con.prepareStatement(insertCondition);
             if(boo)
             { 
               sql.setString(1, logname);
               sql.setString(2, password);
               sql.setString(3, sex);
               sql.setInt(4, age);
               sql.setString(5, phone);
               sql.setString(6, email);
               sql.setString(7, message);
               sql.setString(8, "pic.jpg");
               int m=sql.executeUpdate();
               if(m!=0)
                 { 
                  backNews = "注册成功";
                  reg.setBackNews(backNews);
                  reg.setLogname(logname);
                  reg.setPassword(password);
                  reg.setAge(age);
                  reg.setSex(sex);
                  reg.setEmail(email);
                  reg.setPhone(phone);
                  reg.setMessage(message);
                 }
              }
             else
             {
               backNews = "信息填写不完整或名字中有非法字符";
               reg.setBackNews(backNews);
            }
            con.close();
         } catch (SQLException exp) {
            backNews = "该会员名已被使用，请您更换名字" + exp;
            reg.setBackNews(backNews);
         }
         RequestDispatcher dispatcher = request.getRequestDispatcher("showRegisterMess.jsp");// 转发
         dispatcher.forward(request, response);
      }
   
      public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         doPost(request, response);
      }
}
