// models/Driver.java
package com.fasttracklogistics.models;

public class Driver {
    private String driverId;
    private String name;
    private String email;
    private String phone;
    private String licenseNo;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Driver(String driverId, String name, String email, String phone, String licenseNo) {
        this.driverId = driverId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.licenseNo = licenseNo;
    }

}
