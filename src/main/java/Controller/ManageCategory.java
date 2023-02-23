package Controller;

import Model.BO.CategoryBO;
import Model.Bean.Category;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ManageCategory", value = "/ManageCategory")
public class ManageCategory extends HttpServlet {
    private CategoryBO categoryBO = new CategoryBO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("User") == null) {
            String errorString = "Bạn cần đăng nhập trước";
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            String errorString = null;
            ArrayList<Category> list = null;

            try {
                list = categoryBO.listCategory();
            } catch (Exception e) {
                e.printStackTrace();
                errorString = e.getMessage();
            }
            if (request.getAttribute("errorString") != null) {
                errorString = (String) request.getAttribute("errorString");
            }
            // Lưu thông tin vào request attribute trước khi forward sang views.
            request.setAttribute("errorString", errorString);
            request.setAttribute("categoryList", list);
            request.getSession().setAttribute("Check", "ManageCategory");
            // Forward sang /WEB-INF/views/productListView.jsp
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/manage_category.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
