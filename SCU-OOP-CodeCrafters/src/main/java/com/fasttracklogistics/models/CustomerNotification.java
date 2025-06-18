package com.fasttracklogistics.models;

public class CustomerNotification {
    private String customerId;
    private String customerName;
    private String email;
    private String message;

    public CustomerNotification(String customerId, String customerName, String email, String message) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.message = message;
    }

    // Getters
    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public String getMessage() { return message; }
}