package Controller;

import Model.BO.CategoryBO;
import Model.Bean.Category;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AddCategory", value = "/AddCategory")
public class AddCategory extends HttpServlet {
    private CategoryBO categoryBO = new CategoryBO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("User") == null) {
            String errorString = "Bạn cần đăng nhập trước";
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/add_category.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name_category = request.getParameter("name_category");
        Category category = new Category();
        System.out.println(name_category);
        category.setName(name_category);
        try {
            int result = categoryBO.insertCategory(category);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/ManageCategory");
//		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ManageCategory");
//		dispatcher.forward(request, response);
    }
}
