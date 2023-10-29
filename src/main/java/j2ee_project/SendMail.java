package j2ee_project;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SendMail {

    public static void main(String[] args) {

        // Recipient's email ID needs to be mentioned.
        String to = "jeewebproject@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "jeewebproject@gmail.com";

        // Assuming you are sending email from through gmail smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                String credentialsFilePath = "/credentials.json";
                InputStream inputStream = SendMail.class.getResourceAsStream(credentialsFilePath);
                if (inputStream == null) {
                    throw new NullPointerException("Cannot find the credentials file");
                }
                JSONTokener tokener = new JSONTokener(inputStream);
                JSONObject credentials = new JSONObject(tokener);

                if(!credentials.has("username") || !credentials.has("password")) {
                    throw new RuntimeException("Invalid credentials");
                }
                System.out.println("test: "+credentials.getString("username") + " " + credentials.getString("password"));
                return new PasswordAuthentication(credentials.getString("username"), credentials.getString("password"));
            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}