package edu.duke.ece651.team7.attendanceServer.Common.Notification;

public abstract class AbstractNotification {
    private String subject;
    private String message; // to be changed to an object

    public AbstractNotification(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }
}
