package j2ee_project.controller.profile;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile-informations")
public class ProfileInformationsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idUser = request.getParameter("idUser");
        String idAddress = request.getParameter("idAddress");

        // informations taken from form
        String userName = request.getParameter("userName");
        String userSurname = request.getParameter("userSurname");
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String userPhoneNumber = request.getParameter("userPhoneNumber");
        String userAddress = request.getParameter("userAddress");
        String userPostalCode = request.getParameter("userPostalCode");
        String userCity = request.getParameter("userCity");
        String userCountry = request.getParameter("userCountry");



        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
