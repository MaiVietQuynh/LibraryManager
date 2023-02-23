package Controller;

import Model.BO.ReaderBO;
import Model.Bean.Reader;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ManageReader", value = "/ManageReader")
public class ManageReader extends HttpServlet {
    private ReaderBO readerBO = new ReaderBO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("User") == null) {
            String errorString = "Bạn cần đăng nhập trước";
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            String status = (String) request.getParameter("status");
            if (status == null) {
                status = "0";
                request.getSession().setAttribute("Check", "ManageReader_0");
            } else {
                status = "1";
                request.getSession().setAttribute("Check", "ManageReader_1");
            }
            System.out.println(status);
            String errorString = null;
            ArrayList<Reader> list = null;
//		if(status.equals("1")==false) {
//			status="0";
//		}
            try {
                list = readerBO.getListReader(status);
            } catch (Exception e) {
                e.printStackTrace();
                errorString = e.getMessage();
            }
            if (request.getAttribute("errorString") != null) {
                errorString = (String) request.getAttribute("errorString");
            }
            // Lưu thông tin vào request attribute trước khi forward sang views.
            request.setAttribute("readerList", list);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/manage_reader.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
