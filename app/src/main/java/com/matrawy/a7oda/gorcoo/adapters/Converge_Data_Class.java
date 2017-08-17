package com.matrawy.a7oda.gorcoo.adapters;

/**
 * Created by 7oda on 7/14/2017.
 */

public class Converge_Data_Class {
    String Num;
    String Code;
    String Customer;
    String item;
    String Name;
    String Address;
    double Payment; //price
    double Remain;
    String Query;
    String Date;
    String User;
    String Note;
    String Metting_time;
    boolean Status;

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMetting_time() {
        return Metting_time;
    }

    public void setMetting_time(String metting_time) {
        Metting_time = metting_time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getPayment() {
        return Payment;
    }

    public void setPayment(double price) {
        this.Payment = price;
    }

    public double getRemain() {
        return Remain;
    }

    public void setRemain(double remain) {
        Remain = remain;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }
}
