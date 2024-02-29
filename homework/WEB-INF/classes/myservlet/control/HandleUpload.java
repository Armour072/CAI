package myservlet.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import mybean.data.Login;
import mybean.data.UploadFile;

public class HandleUpload extends HttpServlet {
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
      Login login = (Login) session.getAttribute("login"); // 获取用户登录时的Javabean
      boolean ok = true;
      if (login == null) {
         ok = false;
         response.sendRedirect("login.jsp"); // 重定向到登录页面
      }
      if (ok) {
         String logname = login.getLogname();
         uploadFileMethod(request, response, logname); // 接受上传文件
      }
   }

   public void uploadFileMethod(HttpServletRequest request, HttpServletResponse response,
         String logname) throws ServletException, IOException {
      UploadFile upFile = new UploadFile();
      String backNews = "";
      try {
         HttpSession session = request.getSession(true);
         request.setAttribute("upFile", upFile);
         String tempFileName = (String) session.getId();
         File f1 = new File(tempFileName);
         FileOutputStream o = new FileOutputStream(f1);
         InputStream in = request.getInputStream();
         byte b[] = new byte[10000];
         int n;
         while ((n = in.read(b)) != -1) {
            o.write(b, 0, n);
         }
         o.close();
         in.close();
         RandomAccessFile random = new RandomAccessFile(f1, "r");
         int second = 1; // 读出f1的第2行，析取出上传文件的名字：
         String secondLine = null;
         while (second <= 2) {
            secondLine = random.readLine();
            second++;
         }
         // 获取第2行中目录符号'='最后出现的位置：
         int position = secondLine.lastIndexOf('=');
         // 截取文件名：
         String fileName = secondLine.substring(position + 2, secondLine.length() - 1);
         // byte cc[] = fileName.getBytes("utf-8");
         // fileName = new String(cc);
         // fileName = fileName.replaceAll(" ", "");
         fileName = fileName.replace(" ", "");

         // 文件是否由字母或数字组成判断名字
         String checkedStr = fileName.substring(0, fileName.indexOf("."));
         boolean isLetterOrDigit = true;
         for (int i = 0; i < checkedStr.length(); i++) {
            char c = checkedStr.charAt(i);
            if (!((c <= 'z' && c >= 'a') || (c <= 'Z' && c >= 'A') || (c <= '9' && c >= '0'))) {
               isLetterOrDigit = false;
               break;
            }
         }
         if (!isLetterOrDigit) {
            response.sendRedirect("upload.jsp"); // 重定向到upload.jsp页面
         }
         // 保存文件名是上传文件名加会员名为前缀：
         String savedFileName = logname.concat(fileName);
         random.seek(0);
         long forthEndPosition = 0; // 获取第4行回车符号的位置
         int forth = 1;
         while ((n = random.readByte()) != -1 && (forth <= 4)) {
            if (n == '\n') {
               forthEndPosition = random.getFilePointer();
               forth++;
            }
         }
         // 根据客户上传文件的名字，将该文件存入磁盘：
         File dir = new File("D:/JspDemo/Chapter10/member_img");
         dir.mkdir();
         // 首先删除用户曾上传过的图像文件：
         File file[] = dir.listFiles();
         for (int k = 0; k < file.length; k++) {
            if (file[k].getName().startsWith(logname))
               file[k].delete();
         }
         File savingFile = new File(dir, savedFileName); // 需要新保存的上传文件
         RandomAccessFile random2 = new RandomAccessFile(savingFile, "rw");
         random.seek(random.length());
         long endPosition = random.getFilePointer();
         long mark = endPosition;
         int j = 1;
         // 确定出文件f1中包含客户上传的文件的内容的最后位置，即倒数第6行:
         while ((mark >= 0) && (j <= 6)) {
            mark--;
            random.seek(mark);
            n = random.readByte();
            if (n == '\n') {
               endPosition = random.getFilePointer();
               j++;
            }
         }
         random.seek(forthEndPosition);
         long startPoint = random.getFilePointer();
         while (startPoint < endPosition - 1) {
            n = random.readByte();
            random2.write(n);
            startPoint = random.getFilePointer();
         }
         random2.close();
         random.close();
         String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
         Connection con = DriverManager.getConnection(uri, "postgres", "xinguzhang01LINK");
         Statement sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
         ResultSet rs = sql.executeQuery("SELECT * FROM member where logname = '" + logname + "'");
         if (rs.next()) {
            if (isLetterOrDigit) {
               rs.updateString(8, savedFileName);
               int index = rs.getRow();
               rs.absolute(index);
               rs.updateRow();
               backNews = fileName + "成功上传";
               upFile.setFileName(fileName);
               upFile.setSavedFileName(savedFileName);
               upFile.setBackNews(backNews);
            }
         }
         con.close();
         f1.delete();
      } catch (Exception exp) {
         backNews = "" + exp;
         upFile.setBackNews(backNews);
      }
      try {
         RequestDispatcher dispatcher = request.getRequestDispatcher("showUploadMess.jsp");
         dispatcher.forward(request, response);
      } catch (Exception ee) {
      }
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doPost(request, response);
   }
}
