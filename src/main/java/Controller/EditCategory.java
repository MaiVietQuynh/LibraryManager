package Controller;

import Model.BO.CategoryBO;
import Model.Bean.Category;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EditCategory", value = "/EditCategory")
public class EditCategory extends HttpServlet {
    private CategoryBO categoryBO = new CategoryBO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("User") == null) {
            String errorString = "Bạn cần đăng nhập trước";
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            String id = (String) request.getParameter("id");

            Category category = null;

            String errorString = null;

            try {
                category = categoryBO.findCategory(id);
            } catch (SQLException e) {
                e.printStackTrace();
                errorString = e.getMessage();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Không có lỗi.
            // Sản phẩm không tồn tại để edit.
            // Redirect sang trang danh sách sản phẩm.
            if (errorString != null && category == null) {
                response.sendRedirect(request.getServletPath() + "/ManageCategory");
                return;
            }

            // Lưu thông tin vào request attribute trước khi forward sang views.
            request.setAttribute("errorString", errorString);
            request.setAttribute("category", category);

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/edit_category.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String name = (String) request.getParameter("name_category");
        Category category = new Category(id, name);
        String errorString = null;
        int result = 0;
        try {
            result = categoryBO.updateCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (result == 0 && errorString == null) {
            errorString = "Chỉnh sửa thất bại";
        }
        if (result == 1)
            errorString = "Chỉnh sửa thành công";
        // Lưu thông tin vào request attribute trước khi forward sang views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("category", category);
        // Nếu có lỗi forward sang trang edit.
        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/edit_category.jsp");
            dispatcher.forward(request, response);
        }
        // Nếu mọi thứ tốt đẹp.
        // Redirect sang trang danh sách sản phẩm.
        else {
            response.sendRedirect(request.getContextPath() + "/ManageCategory");
        }
    }
}
