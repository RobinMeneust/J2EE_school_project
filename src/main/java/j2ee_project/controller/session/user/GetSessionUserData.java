package j2ee_project.controller.session.user;

import j2ee_project.model.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/session/customer")
public class GetSessionUserData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject responseObj = new JSONObject();
        response.setStatus(HttpServletResponse.SC_OK);

        Object obj = session.getAttribute("user");
        if(!(obj instanceof User)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("There is not user in the curretn session");
            return;
        }

        User user = (User) obj;

        PrintWriter out = response.getWriter();
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put("email", user.getEmail());
        userJsonObj.put("first-name", user.getFirstName());
        userJsonObj.put("last-name", user.getLastName());
        responseObj.put("user", userJsonObj);

        out.print(responseObj);
        out.flush();
    }
}