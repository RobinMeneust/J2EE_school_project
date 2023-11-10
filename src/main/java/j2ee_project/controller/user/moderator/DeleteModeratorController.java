package j2ee_project.controller.user.moderator;

import j2ee_project.dao.user.ModeratorDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/delete-moderator")
public class DeleteModeratorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String moderatorIdStr = request.getParameter("id");
        int moderatorId = -1;

        if(moderatorIdStr != null && !moderatorIdStr.trim().isEmpty()) {
            try {
                moderatorId = Integer.parseInt(moderatorIdStr);
            } catch(Exception ignore) {}
        }

        if(moderatorId<=0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Moderator ID must be positive");
        }
        ModeratorDAO.deleteModerator(moderatorId);
        try {
            response.sendRedirect("dashboard");
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}