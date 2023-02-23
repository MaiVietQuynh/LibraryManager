package Controller;

import Model.BO.GetCookie;
import Model.Bean.User;
import Model.DAO.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebFilter(filterName = "CookieFilter")
public class CookieFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("User");
        //
        if (user != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            chain.doFilter(request, response);
            return;
        }

        String checked = (String) session.getAttribute("COOKIE_CHECKED");
        if (checked == null) {
            String username = GetCookie.getUserNameInCookie(req);
            try {
                UserDAO userDAO = new UserDAO();
                User user_find = userDAO.findUser(username);
                session.setAttribute("User", user_find);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
        }

        chain.doFilter(request, response);
    }
}
