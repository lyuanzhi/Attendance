package edu.duke.ece651.team7.attendanceServer.Common.Notification;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import org.apache.commons.codec.binary.Base64;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

public class GmailService implements EmailService {
    private final Gmail service;
    private static final String sourceEmail = "sideghigs@gmail.com";
    // private static final String destEmail = "jlarry0398@gmail.com";
    // //"jones.larry@duke.edu";//"jlarry0398@gmail.com";
    // private static final String bbc1Email = "Jlarry0398@gmail.com";
    // //"xinyi.li@duke.edu";
    // private static final String bbc2Email = "xinyue.li@duke.edu";
    // private static final String bbc3Email = "yuanzhi.lou@duke.edu";

    public GmailService(Gmail service) {
        this.service = service;
    }

    @Override
    public void sendMessage(AbstractNotification notification, String destEmail) throws Exception {

        try {
            Message msg = createMessage(notification, destEmail);
            msg = service.users().messages().send("me", msg).execute();
            // System.out.println("Message id: " + msg.getId());
            // System.out.println(msg.toPrettyString());
        } catch (GoogleJsonResponseException e) {
            // System.err.println("Unable to send message" + e.getDetails());
        } catch (Exception e) {
            // System.err.println("Couldn't send email" + e.getMessage());
        }
    }

    private Message createMessage(AbstractNotification notification, String destEmail) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(sourceEmail));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(destEmail));
        email.addRecipient(javax.mail.Message.RecipientType.BCC, new InternetAddress(destEmail));
        email.setSubject(notification.getSubject());
        email.setText(notification.getMessage());
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);

        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodedEmail);
        return msg;
    }
}
