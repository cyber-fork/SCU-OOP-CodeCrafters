package com.fasttracklogistics.models;

public class Assignment {
    private int id;
    private int shipmentId;
    private int driverId;

    // Constructor for new assignment (no ID yet)
    public Assignment(int shipmentId, int driverId) {
        this.shipmentId = shipmentId;
        this.driverId = driverId;
    }

    // Constructor with ID for fetched assignments
    public Assignment(int id, int shipmentId, int driverId) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.driverId = driverId;
    }

    public int getId() {
        return id;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
}
