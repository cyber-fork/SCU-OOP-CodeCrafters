package com.fasttracklogistics.models;

public class DriverNotification {
    private String driverId;
    private String driverName;
    private String email;
    private String message;

    public DriverNotification(String driverId, String driverName, String email, String message) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.email = email;
        this.message = message;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
