package Controller;

import Model.BO.BookBO;
import Model.Bean.Book;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "SearchBook", value = "/SearchBook")
public class SearchBook extends HttpServlet {
    private BookBO bookBO = new BookBO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("User") == null) {
            String errorString = "Bạn cần đăng nhập trước";
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setCharacterEncoding("UTF-8");
            String data_search = request.getParameter("data_search");
            String errorString = null;
            ArrayList<Book> list = null;

            try {
                list = bookBO.searchBook(data_search);
            } catch (Exception e) {
                e.printStackTrace();
                errorString = e.getMessage();
            }
            if (request.getAttribute("errorString") != null) {
                errorString = (String) request.getAttribute("errorString");
            }
            errorString="Kết quả tìm kiếm cho từ khóa '"+data_search+ "'";
            // Lưu thông tin vào request attribute trước khi forward sang views.
            request.setAttribute("errorString", errorString);
            request.setAttribute("bookList", list);

            // Forward sang /WEB-INF/views/productListView.jsp
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/manage_book.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
