// package myservlet.control;

// import java.io.IOException;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.List;

// import jakarta.servlet.RequestDispatcher;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.HttpSession;
// import mybean.data.ShowProducts;



// public class HandleProducts extends HttpServlet {
    // private static final long serialVersionUID = 1L;

    // @Override
    // public void init() throws ServletException {
    //     super.init();
    //     try {
    //         Class.forName("org.postgresql.Driver");
    //     } catch (ClassNotFoundException e) {
    //         e.printStackTrace();
    //     }
    // }

    // @Override
    // protected void doGet(HttpServletRequest request, HttpServletResponse response)
    //         throws ServletException, IOException {
    //     HttpSession session = request.getSession(true);
    //     Connection con = null;
    //     List<ShowProducts> productList = new ArrayList<>();

    //     try {
    //         con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/comehere", "postgres", "");
    //         Statement stmt = con.createStatement();
    //         ResultSet rs = stmt.executeQuery("SELECT * FROM goods");

    //         while (rs.next()) {
    //             ShowProducts product = new ShowProducts();
    //             product.setProductName(rs.getString("gname"));
    //             product.setImage(rs.getString("gpic"));
    //             product.setPrice(rs.getDouble("price"));

    //             productList.add(product);
    //         }

    //         stmt.close(); // 关闭查询结果的 Statement
    //         con.close();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         System.out.println("Database connection error: " + e.getMessage());
    //     }

    //     request.setAttribute("productList", productList);

    //     RequestDispatcher dispatcher = request.getRequestDispatcher("/showProducts.jsp");
    //     dispatcher.forward(request, response);
    // }


    // @Override
    // protected void doPost(HttpServletRequest request, HttpServletResponse response)
    //         throws ServletException, IOException {
    //     doGet(request, response);
    // }

  
// }

// }

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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HandleProducts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = null;
        Statement stmt = null;

        try {
            // 连接数据库
            Class.forName("org.postgresql.Driver");
            String uri = "jdbc:postgresql://127.0.0.1:5432/comehere";
            con = DriverManager.getConnection(uri, "postgres", "");

            // 查询所有商品信息
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM goods");
            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.populate(rs);

            // 获取当前页码和每页显示的商品数量
            int page = 1;
            int pageSize = 10;

            // 使用 showProducts 方法获取指定页码的商品信息
            StringBuffer presentPageResult = showProducts(page, pageSize, rowSet);

            // 将商品列表存储在请求属性中，供JSP页面使用
            request.setAttribute("presentPageResult", presentPageResult);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 转发到显示商品信息的JSP页面
        RequestDispatcher dispatcher = request.getRequestDispatcher("showProducts.jsp");
        dispatcher.forward(request, response);
    }

    public StringBuffer showProducts(int page, int pageSize, CachedRowSet rowSet) {
        StringBuffer str = new StringBuffer();
        try {
            rowSet.absolute((page - 1) * pageSize + 1);
            for (int i = 1; i <= pageSize; i++) {
                str.append("<tr>");
                str.append("<td>" + rowSet.getString("gname") + "</td>");
                str.append("<td><img src='" + rowSet.getString("gpic") + "'></td>");
                str.append("<td>" + rowSet.getDouble("price") + "</td>");
                str.append("</tr>");
                rowSet.next();
            }
        } catch (SQLException exp) {
            str.append(exp.getMessage());
        }
        return str;
    }
}
