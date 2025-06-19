package com.fasttracklogistics.service;

import com.fasttracklogistics.models.DriverNotification;
import com.fasttracklogistics.dao.DriverNotificationDao;

import java.util.List;

public class DriverNotificationService {
    private final DriverNotificationDao dao = new DriverNotificationDao();

    public void sendNotification(DriverNotification notification) {
        dao.saveNotification(notification);
        EmailUtil.sendEmail(notification.getEmail(), "Driver Notification", notification.getMessage());
    }

    public void deleteNotification(String driverId, String email) {
        dao.deleteNotification(driverId, email);
    }

    public List<DriverNotification> getAllNotifications() {
        return dao.fetchAllNotifications();
    }
}
