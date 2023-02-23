package Controller;

import Model.BO.ReaderBO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ConfirmReader", value = "/ConfirmReader")
public class ConfirmReader extends HttpServlet {
    private ReaderBO readerBO = new ReaderBO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getParameter("id");
        try {
            readerBO.changeStatus(id);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		response.sendRedirect("/QuanLyThuVien/ManageReader");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ManageReader");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
