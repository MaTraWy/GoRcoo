package com.matrawy.a7oda.gorcoo;

import java.io.Serializable;

/**
 * Created by 7oda on 4/28/2017.
 */

public class Person_Customer extends Person implements Serializable{
    private String phone;
    private  String address;
    private String status;
    private String discounts;
    private String notes;
    private String Payment;
    private String speed;
    private String remain;
    private int code;

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrice() {
        return Payment;
    }

    public void setPrice(String price) {
        this.Payment = price;
    }

    public String getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String discounts) {
        this.discounts = discounts;
    }

    public String getSpeed() {
        return speed;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
