package myservlet.control;
import java.io.IOException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mybean.data.Login;

public class HandleExit extends HttpServlet{
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){}
    }
    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        HttpSession session=request.getSession(true);
        Login login=(Login)session.getAttribute("login");
        boolean ok=true;
        if(login==null){
            ok=false;
            response.sendRedirect("login.jsp");
        }
        if(ok==true){
            continueDoPost(request,response);
        }
    }
    public void continueDoPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        HttpSession session=request.getSession(true);
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        doPost(request, response);
    }
}