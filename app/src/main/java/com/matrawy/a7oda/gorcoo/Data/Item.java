package com.matrawy.a7oda.gorcoo.Data;

/**
 * Created by 7oda on 7/28/2017.
 */

public class Item {
    private String ID;
    private String Name;
    private String price;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

