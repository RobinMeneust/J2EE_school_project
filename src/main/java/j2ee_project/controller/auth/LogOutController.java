package j2ee_project.controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogOutController", value = "/logout-controller")
public class LogOutController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}