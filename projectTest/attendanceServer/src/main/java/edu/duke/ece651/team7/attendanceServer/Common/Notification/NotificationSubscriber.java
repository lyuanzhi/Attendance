package edu.duke.ece651.team7.attendanceServer.Common.Notification;

public interface NotificationSubscriber {
    void notify(AbstractNotification notification) throws Exception;
}
