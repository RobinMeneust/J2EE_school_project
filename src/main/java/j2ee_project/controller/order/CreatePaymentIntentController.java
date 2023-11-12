package j2ee_project.controller.order;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import j2ee_project.service.MailManager;
import jakarta.mail.PasswordAuthentication;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/create-payment-intent")
public class CreatePaymentIntentController extends HttpServlet {

    private static Gson gson = new Gson();

    static class CreatePaymentResponse {
        private String clientSecret;
        public CreatePaymentResponse(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }

    static int calculateOrderAmount(Object[] items) {
        // Replace this constant with a calculation of the order's amount
        // Calculate the order total on the server to prevent
        // people from directly manipulating the amount on the client
        return 1400;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Set your Stripe secret key
            if(Stripe.apiKey == null) {
                String credentialsFilePath = "/credentials.json";
                InputStream inputStream = MailManager.class.getResourceAsStream(credentialsFilePath);
                if (inputStream == null) {
                    throw new NullPointerException("Cannot find the credentials file");
                }
                JSONTokener tokener = new JSONTokener(inputStream);
                JSONObject credentials = new JSONObject(tokener);

                // Check if the credentials object contains the required fields

                if(!credentials.has("stripe") || !credentials.getJSONObject("stripe").has("secret_key")) {
                    throw new RuntimeException("Invalid credentials");
                }
                Stripe.apiKey = credentials.getJSONObject("stripe").getString("secret_key");
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                    .setAmount(10L)
                    .setCurrency("eur")
                    // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
                    .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                            .builder()
                            .setEnabled(true)
                            .build()
                    )
                    .build();

            // Create a PaymentIntent with the order amount and currency
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(paymentResponse));
            out.flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
