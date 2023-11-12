package j2ee_project.controller.user.moderator;

import j2ee_project.dao.user.ModeratorDAO;
import j2ee_project.model.user.Moderator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/add-moderator")
public class AddModeratorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/addModerator.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Moderator moderator = new Moderator();

        moderator.setLastName(request.getParameter("last-name"));
        moderator.setFirstName(request.getParameter("first-name"));
        moderator.setPassword(request.getParameter("password"));

        moderator.setEmail(request.getParameter("email"));
        moderator.setPhoneNumber(request.getParameter("phone-number"));

        ModeratorDAO.addModerator(moderator);

        try {
            response.sendRedirect("dashboard?tab=moderators");
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}