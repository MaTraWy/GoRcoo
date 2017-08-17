package com.matrawy.a7oda.gorcoo;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.matrawy.a7oda.gorcoo.Data.DB;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 7oda on 7/6/2017.
 */

public class Person_Array implements Serializable, Runnable{
    private ArrayList<Person_Customer> Customer_List;
    private ArrayList<String> add;
    private ArrayList<String> name;
     private DB db ;
     private SQLiteDatabase db_heleper ;
     Context context;
    ProgressDialog l;
    @Override
    public void run()
    {
        Open_Connection();
        Get_All_Customer();
        Close_Connection();
        for(Person_Customer M:Customer_List) {
            add.add(M.getAddress());
            name.add(M.getName());
        }
        l.dismiss();
    }
    Person_Array(Context context,ProgressDialog l)
    {
        this.l = l;
        Customer_List = new ArrayList<>();
        add = new ArrayList<>();
        name = new ArrayList<>();
        this.context = context;
    }
    private boolean Get_All_Customer()
    {
        Cursor cu = db.Get_Customer(db_heleper);
        if(cu==null)
            return false;
        while(cu.moveToNext())
        {
            Person_Customer Customer = new Person_Customer();
            Customer.setId(cu.getInt(0));
            Customer.setName(cu.getString(2));
            Customer.setUsername(cu.getString(3));
            Customer.setPassword(cu.getString(4));
            Customer.setSpeed(cu.getString(5));
            Customer.setRemain(cu.getString(6));
            Customer.setPrice(cu.getString(7));
            Customer.setNotes(cu.getString(8));
            Customer.setDiscounts(cu.getString(9));
            Customer.setStatus(cu.getString(10));
            Customer.setAddress(cu.getString(11));
            Customer.setPhone(cu.getString(12));
            Customer_List.add(Customer);
        }
        return true;
    }
    public void ReGet()
    {
        Customer_List.clear();
        Get_All_Customer();
    }

    public ArrayList<Person_Customer> getCustomer_List() {
        return Customer_List;
    }
    public void Close_Connection()
    {
        db_heleper.close();
        db.close();
    }
    public void Open_Connection()
    {
        db = new DB(context);
        db_heleper = db.getReadableDatabase();
    }
    public ArrayList<String> Get_Add()
    {
        return add;
    }
    public Person_Customer get_id(int id)
    {
        return Customer_List.get(id);
        /*if(id>Customer_List.size())
            return null;
        Person_Customer M = Customer_List.get(id);
        Map m1 = new HashMap();
        m1.put("ID",M.getId());
        m1.put("NAME", M.getName());
        m1.put("USER_NAME", M.getUsername());
        m1.put("PASSWORD", M.getPassword());
        m1.put("SPEED", M.getSpeed());
        m1.put("PAYMENT", M.getPrice());
        m1.put("NOTE", M.getNotes());
        m1.put("DISCOUNTS", M.getDiscounts());
        m1.put("STATUS", M.getStatus());
        m1.put("ADDRESS", M.getAddress());
        m1.put("PHONE", M.getPhone());
        return m1;*/
    }
    public ArrayList<String>Get_Names()
    {
        ArrayList<String> Names = new ArrayList<>();
        for(Person_Customer Name:Customer_List)
            Names.add(Name.getName());
        return Names;
    }
}
