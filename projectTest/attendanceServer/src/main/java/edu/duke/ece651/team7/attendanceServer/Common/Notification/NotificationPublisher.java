package edu.duke.ece651.team7.attendanceServer.Common.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationPublisher {
    private List<NotificationSubscriber> subscribers = new ArrayList<>();

    public void addSubscriber(NotificationSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(NotificationSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscriber(AbstractNotification notification, NotificationSubscriber subscriber) {
        try {
            subscriber.notify(notification);
        } catch (Exception e) {
            // System.err.println(e.getMessage());
        }
    }

    public void notifySubscribers(AbstractNotification notification) {
        for (NotificationSubscriber subscriber : subscribers) {
            try {
                subscriber.notify(notification);
            } catch (Exception e) {
                // System.err.println(e.getMessage());
            }
        }
    }

    public int getNumSubscribers() {
        return subscribers.size();
    }
}
