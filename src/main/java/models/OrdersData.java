package models;

import java.util.Date;
import java.util.List;

public class OrdersData {
    private int id;
    private Object courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private Date deliveryDate;
    private int track;
    private List<String> color;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
    private int status;

    public int getId() {
        return id;
    }

    public Object getCourierId() {
        return courierId;
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

    public List<String> getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public int getStatus() {
        return status;
    }
}
