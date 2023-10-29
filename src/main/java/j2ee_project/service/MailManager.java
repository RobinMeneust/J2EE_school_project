package j2ee_project.service;

import j2ee_project.dao.SendMailFailureException;
import j2ee_project.model.Mail;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.Properties;

public class MailManager {
    private static MailManager instance;
    private final Session session;

    public static MailManager getInstance() {
        if(instance == null) {
            instance = new MailManager();
        }
        return instance;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private MailManager() {
        String host = "smtp.gmail.com";

        // Setup mail server
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        session = Session.getInstance(properties, new jakarta.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // Read the credentials file

                String credentialsFilePath = "/credentials.json";
                InputStream inputStream = MailManager.class.getResourceAsStream(credentialsFilePath);
                if (inputStream == null) {
                    throw new NullPointerException("Cannot find the credentials file");
                }
                JSONTokener tokener = new JSONTokener(inputStream);
                JSONObject credentials = new JSONObject(tokener);

                // Check if the credentials object contains the required fields

                if(!credentials.has("username") || !credentials.has("password")) {
                    throw new RuntimeException("Invalid credentials");
                }

                return new PasswordAuthentication(credentials.getString("username"), credentials.getString("password"));
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);
    }


    public void send(Mail mail) throws SendMailFailureException {
        // Recipient's email ID needs to be mentioned.
        if(isEmpty(mail.getToAddress()) || isEmpty(mail.getFromAddress())) {
            throw new SendMailFailureException("'From' or 'To' email addresses are empty");
        }

        // Set the date to current time if it's null
        if(mail.getDate() == null) {
            mail.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        }

        // Set the subject and body to an empty String if it's null to prevent an Exception from being thrown when it's used
        if(mail.getSubject() == null) {
            mail.setSubject("");
        }
        if(mail.getBody() == null) {
            mail.setBody("");
        }


        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(mail.getFromAddress()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getToAddress()));
            message.setSubject(mail.getSubject());
            message.setText(mail.getBody());

            Transport.send(message);
        } catch (Exception e) {
            throw new SendMailFailureException(e.getMessage());
        }
    }
}