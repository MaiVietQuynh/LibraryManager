package Controller;

import Model.BO.GetCookie;
import Model.BO.UserBO;
import Model.Bean.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    private UserBO userBO = new UserBO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("User") == null) {
            // TODO Auto-generated method stub
            String errorString = null;
            if (request.getAttribute("errorString") != null) {
                errorString = (String) request.getAttribute("errorString");
            }
            request.getSession().removeAttribute("Check");
            // Lưu thông tin vào request attribute trước khi forward sang views.
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/UserManual");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeStr);
        System.out.println("Day" + remember);
        String errorString = null;
        User user = new User();

        try {
            user = userBO.getAccount(username, password);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            user = null;
            errorString = "Lỗi kết nối cơ sở dữ liệu";
            e.printStackTrace();
        }
        if (user != null) {
            request.getSession().setAttribute("User", user);
            if (remember) {

                GetCookie.storeUserCookie(response, user);
            }
            // Ngược lại xóa Cookie
            else {
                GetCookie.deleteUserCookie(response);
            }
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/UserManual");
            dispatcher.forward(request, response);
        } else {
            if (errorString == null)
                errorString = "Sai tên tài khoản hoặc mật khẩu";
            request.setAttribute("errorString", errorString);
            doGet(request, response);
        }
    }
}
