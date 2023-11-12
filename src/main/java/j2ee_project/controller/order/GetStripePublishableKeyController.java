package j2ee_project.controller;

import com.stripe.Stripe;
import j2ee_project.service.MailManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * This class is a servlet used to get the context path. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/get-stripe-publishable-key")
public class GetStripePublishableKeyController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String credentialsFilePath = "/credentials.json";
        InputStream inputStream = MailManager.class.getResourceAsStream(credentialsFilePath);
        if (inputStream == null) {
            throw new NullPointerException("Cannot find the credentials file");
        }
        JSONTokener tokener = new JSONTokener(inputStream);
        JSONObject credentials = new JSONObject(tokener);

        // Check if the credentials object contains the required fields

        if(!credentials.has("stripe") || !credentials.getJSONObject("stripe").has("publishable-key")) {
            throw new RuntimeException("Invalid credentials");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stripe-publishable-key",credentials.getJSONObject("stripe").getString("publishable-key"));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
    }
}

