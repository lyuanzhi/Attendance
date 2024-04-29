package edu.duke.ece651.team7.attendanceServer.Common.Notification;

public interface EmailService {
    void sendMessage(AbstractNotification notification, String destEmail) throws Exception;
}
