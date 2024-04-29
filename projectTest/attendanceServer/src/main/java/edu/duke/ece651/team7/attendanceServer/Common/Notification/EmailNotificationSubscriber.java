package edu.duke.ece651.team7.attendanceServer.Common.Notification;

import com.google.api.services.gmail.Gmail;

public class EmailNotificationSubscriber implements NotificationSubscriber {
    private String emailAddress;

    public EmailNotificationSubscriber(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void notify(AbstractNotification notification) throws Exception {
        GmailOAuth2 gmailOAuth = new GmailOAuth2();
        Gmail service = gmailOAuth.getGmailService();
        GmailService gmailService = new GmailService(service);
        gmailService.sendMessage(notification, emailAddress);
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
