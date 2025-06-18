package com.fasttracklogistics.service;

import com.fasttracklogistics.dao.CustomerNotificationDao;
import com.fasttracklogistics.models.CustomerNotification;

import java.util.List;

public class CustomerNotificationService {
    private CustomerNotificationDao dao = new CustomerNotificationDao();

    public void sendNotification(CustomerNotification cn) {
        dao.saveNotification(cn);
        EmailUtil.sendEmail(cn.getEmail(), "Notification from FastTrack Logistics", cn.getMessage());
    }

    public List<CustomerNotification> getAllNotifications() {
        return dao.getAllNotifications();
    }

    public void deleteNotification(String customerId, String email) {
        dao.deleteNotification(customerId, email);
    }
}