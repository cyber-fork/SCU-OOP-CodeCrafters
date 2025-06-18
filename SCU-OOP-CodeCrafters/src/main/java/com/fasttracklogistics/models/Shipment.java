package com.fasttracklogistics.models;

public class Shipment {
    private String id;
    private String sender;
    private String receiver;
    private String contents;
    private String status;

    public Shipment(String id, String sender, String receiver, String contents, String status) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.contents = contents;
        this.status = status;
    }

    public String getId() { return id; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getContents() { return contents; }
    public String getStatus() { return status; }

    public void setSender(String sender) { this.sender = sender; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public void setContents(String contents) { this.contents = contents; }
    public void setStatus(String status) { this.status = status; }
}
