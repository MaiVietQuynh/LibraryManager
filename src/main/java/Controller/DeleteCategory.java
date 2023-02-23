package Controller;

import Model.BO.CategoryBO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteCategory", value = "/DeleteCategory")
public class DeleteCategory extends HttpServlet {
    private CategoryBO categoryBO = new CategoryBO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getParameter("id");
        boolean result;
        try {
            result = categoryBO.deleteCategory(id);
            System.out.println("Ket qua"+result);
            if (result == true) {
                request.setAttribute("errorString", "Đã xóa thành công");
            } else {
                request.setAttribute("errorString", "Lỗi cơ sở dữ liệu");
            }
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ManageCategory");
//		dispatcher.forward(request, response);
        response.sendRedirect(request.getContextPath() + "/ManageCategory");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
