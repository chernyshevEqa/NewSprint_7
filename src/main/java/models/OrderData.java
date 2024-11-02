package models;

import java.util.Date;
import java.util.List;

public class OrderData {

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private Date deliveryDate;
    private int track;
    private int status;
    private List<String> color;
    private String comment;
    private boolean cancelled;
    private boolean finished;
    private boolean inDelivery;
    private String courierFirstName;
    private Date createdAt;
    private Date updatedAt;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public int getTrack() {
        return track;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isInDelivery() {
        return inDelivery;
    }

    public String getCourierFirstName() {
        return courierFirstName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
