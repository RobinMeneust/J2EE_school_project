package j2ee_project.controller.user.moderator;

import j2ee_project.dao.user.ModeratorDAO;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.dom4j.rule.Mode;

import java.io.IOException;
import java.util.List;

import static j2ee_project.dao.user.PermissionDAO.getPermission;

/**
 * This class is a servlet used to get a list of the moderators. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/get-moderators")
public class GetModeratorController extends HttpServlet {
    /**
     * Get the moderators list
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("user");
            if (obj instanceof Moderator moderator
                    && moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_MODERATOR))) {
                List<Moderator> moderators = ModeratorDAO.getModerators();
                request.setAttribute("moderators", moderators);
            } else {
                response.sendRedirect("dashboard");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}