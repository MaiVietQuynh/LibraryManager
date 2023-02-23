package Controller;

import Model.BO.BookBO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteBook", value = "/DeleteBook")
public class DeleteBook extends HttpServlet {
    private BookBO bookBO = new BookBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getParameter("id");

        if (id == null) {
            boolean result;
            try {
                result = bookBO.deleteAllBook();
            } catch (ClassNotFoundException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            boolean result;
            try {
                result = bookBO.deleteBook(id);
            } catch (ClassNotFoundException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        request.setAttribute("errorString", "Đã xóa thành công");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ManageBook");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
