package com.example.scorpio.backupsms.entities;

/**
 * Created by Scorpio on 16/2/2.
 */
public class SmsInfo {
    private int id;
    private String address;

    public SmsInfo(int id, String address, long date, int type, String body) {
        super();
        this.id = id;
        this.address = address;
        this.date = date;
        this.type = type;
        this.body = body;
    }

    private long date;
    private int type;
    private String body;

    public String getAddress() {
        return address;
    }

    public SmsInfo setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getBody() {
        return body;
    }

    public SmsInfo setBody(String body) {
        this.body = body;
        return this;
    }

    public long getDate() {
        return date;
    }

    public SmsInfo setDate(long date) {
        this.date = date;
        return this;
    }

    public int getId() {
        return id;
    }

    public SmsInfo setId(int id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public SmsInfo setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "SmsInfo{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", body='" + body + '\'' +
                '}';
    }
    

    public SmsInfo() {
        super();
    }

    
}
