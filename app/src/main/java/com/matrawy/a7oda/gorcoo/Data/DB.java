package com.matrawy.a7oda.gorcoo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import com.matrawy.a7oda.gorcoo.Person_Customer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

import static com.matrawy.a7oda.gorcoo.Data.DB_Contract.Sold_Item.Buyer_ID;

/**
 * Created by 7oda on 4/14/2017.
 */

public class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1; //for now i fixed the version for 1

    private static final String DATABASE_NAME = "gorcoo.db";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CUSTOMER_TABLE = "CREATE TABLE " + DB_Contract.Customer.TABLE_NAME + " (" +
                DB_Contract.Customer._ID + " INTEGER PRIMARY KEY," +
                DB_Contract.Customer.CODE + " INTEGER ," +
                DB_Contract.Customer.NAME + " TEXT NOT NULL, " +
                DB_Contract.Customer.USER_NAME + " TEXT  NULL, " +
                DB_Contract.Customer.PASSWORD + " TEXT NOT NULL, " +
                DB_Contract.Customer.SPEED + " INTEGER  NOT NULL, " +
                DB_Contract.Customer.Remain + " INTEGER  NULL, " +
                DB_Contract.Customer.PAYMENT + " TEXT NOT NULL, " +
                DB_Contract.Customer.NOTE + " TEXT  NULL, " +
                DB_Contract.Customer.DISCOUNTS + " TEXT NOT NULL, " +
                DB_Contract.Customer.STATUS + " TEXT NOT NULL, " +
                DB_Contract.Customer.ADDRESS + " TEXT NOT NULL, " +
                DB_Contract.Customer.PHONE + " TEXT NOT NULL" +
                " );";
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + DB_Contract.Users.TABLE_NAME + " (" +
                DB_Contract.Users._ID + " INTEGER PRIMARY KEY," +
                DB_Contract.Users.NAME + " TEXT NOT NULL, " +
                DB_Contract.Users.USER_NAME + " TEXT NOT NULL, " +
                DB_Contract.Users.PASSWORD + " TEXT NOT NULL " +
                " );";
        final String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + DB_Contract.History.TABLE_NAME + " (" +
                DB_Contract.History._ID + " INTEGER PRIMARY KEY," +
                DB_Contract.History.USER_ID + " INTEGER NOT NULL, " +
                DB_Contract.History.CUST_ID + " INTEGER NOT NULL, " +
                DB_Contract.History.DAY_CONST + " TEXT NOT NULL ," +
                DB_Contract.History.DAY_VAR + " TEXT NOT NULL ," +
                DB_Contract.History.HISTORY_DATE + " DATE NOT NULL ," +
                DB_Contract.History.MONTH_OF_DAY + " TEXT NULL ," +
                "FOREIGN KEY(" + DB_Contract.History.USER_ID + ")REFERENCES " + DB_Contract.Users.TABLE_NAME + "(" + DB_Contract.Users._ID + ") ," +
                "FOREIGN KEY(" + DB_Contract.History.CUST_ID + ")REFERENCES " + DB_Contract.Customer.TABLE_NAME + "(" + DB_Contract.Customer._ID + ")" +
                " );";
        final String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + DB_Contract.Item.TABLE_NAME + " (" +
                DB_Contract.Item._ID + " INTEGER PRIMARY KEY," +
                DB_Contract.Item.NAME + " TEXT NOT NULL, " +
                DB_Contract.Item.PRICE + " TEXT NOT NULL" +
                " );";
        final String SQL_CREATE_Sold_Item_TABLE = "CREATE TABLE " + DB_Contract.Sold_Item.TABLE_NAME + " (" +
                DB_Contract.Item._ID + " INTEGER PRIMARY KEY , " +
                DB_Contract.Sold_Item.ITEM_ID + " TEXT NOT NULL, " +
                Buyer_ID + " TEXT NOT NULL, " +
                DB_Contract.Sold_Item.Quantity + " INTEGER NOT NULL, " +
                DB_Contract.Sold_Item.USER_ID + " TEXT NOT NULL, " +
                DB_Contract.Sold_Item.Trans_Date + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + DB_Contract.Sold_Item.USER_ID + ")REFERENCES " + DB_Contract.Users.TABLE_NAME + "(" + DB_Contract.Users._ID + ") ," +
                "FOREIGN KEY(" + Buyer_ID + ")REFERENCES " + DB_Contract.Customer.TABLE_NAME + "(" + DB_Contract.Customer._ID + ")" +
                " );";
        final String SQL_CREATE_Maintain_TABLE = "CREATE TABLE " + DB_Contract.Maintain.TABLE_NAME + " (" +
                DB_Contract.Maintain._ID + " INTEGER PRIMARY KEY," +
                DB_Contract.Maintain.CUST_ID + " TEXT NOT NULL, " +
                DB_Contract.Maintain.Maintain_DATE + " TEXT NOT NULL, " +
                DB_Contract.Maintain.Maintain_Done + " TEXT NOT NULL, " +
                DB_Contract.Maintain.Maintain_Note + " TEXT  NULL, " +
                "FOREIGN KEY(" + DB_Contract.Maintain.CUST_ID + ")REFERENCES " + DB_Contract.Customer.TABLE_NAME + "(" + DB_Contract.Customer._ID + ")" +
                " );";
        final String SQL_CREATE_Remainder_TABLE = "CREATE TABLE " + DB_Contract.Remainder.TABLE_NAME + " (" +
                DB_Contract.Remainder._ID + " INTEGER PRIMARY KEY," +
                DB_Contract.Remainder.CUST_ID + " TEXT NOT NULL, " +
                DB_Contract.Remainder.Remainder_DATE + " TEXT NOT NULL, " +
                DB_Contract.Remainder.Remainder_Done + " TEXT NOT NULL, " +
                DB_Contract.Remainder.Remainder_Note + " TEXT  NULL, " +
                "FOREIGN KEY(" + DB_Contract.Remainder.CUST_ID + ")REFERENCES " + DB_Contract.Customer.TABLE_NAME + "(" + DB_Contract.Customer._ID + ")" +
                " );";
        final String SQL_CREATE_CACHE = "CREATE TABLE IF NOT EXISTS " + DB_Contract.Cache.TABLE_NAME + " (" +
                DB_Contract.Cache._ID + " INTEGER PRIMARY KEY," +
                DB_Contract.Cache.TABLE + " TEXT NOT NULL, " +
                DB_Contract.Cache.OPERATION + " TEXT NOT NULL, " +
                DB_Contract.Cache.QUERY + " TEXT NOT NULL, " +
                DB_Contract.Cache.DONE + " INTEGER NOT NULL, " +
                DB_Contract.Cache.DATE + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_CUSTOMER_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_HISTORY_TABLE);
        db.execSQL(SQL_CREATE_ITEM_TABLE);
        db.execSQL(SQL_CREATE_Sold_Item_TABLE);
        db.execSQL(SQL_CREATE_Maintain_TABLE);
        db.execSQL(SQL_CREATE_Remainder_TABLE);
        db.execSQL(SQL_CREATE_CACHE);
    }

    @Override //to do CHeck for version
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_Contract.Customer.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Contract.Users.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Contract.Sold_Item.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Contract.Remainder.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Contract.History.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Contract.Item.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Contract.Maintain.TABLE_NAME);
        onCreate(db);
    }

    public Cursor Login(SQLiteDatabase db, String Username, String Password) {
        Cursor cursor = db.rawQuery("select * from " + DB_Contract.Users.TABLE_NAME
                        + " where " + DB_Contract.Users.USER_NAME + " = ? and " +
                        DB_Contract.Users.PASSWORD + "= ?",
                new String[]{Username, Password});
        return cursor.getCount() == 0 ? null : cursor;
    }

    public Cursor Get_Customer(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from " + DB_Contract.Customer.TABLE_NAME, null);
        return cursor.getCount() == 0 ? null : cursor;
    }

    public Cursor Get_Cache(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from " + DB_Contract.Cache.TABLE_NAME, null);
        return cursor.getCount() == 0 ? null : cursor;
    }

    public Cursor Get_Remainder(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from " + DB_Contract.Remainder.TABLE_NAME
                        + " as K JOIN " +
                        DB_Contract.Customer.TABLE_NAME + " as lol on " +
                        DB_Contract.History.CUST_ID + " = lol." + DB_Contract.Customer._ID
                , null);
        return cursor.getCount() == 0 ? null : cursor;
    }

    public boolean Set_Customers(JSONObject Customer_list, SQLiteDatabase db) throws org.json.JSONException {
        JSONArray Cust_result = Customer_list.getJSONArray("customers");
        ContentValues Contentvalues = new ContentValues();
        for (int i = 0; i < Cust_result.length(); i++) {
            JSONObject Num = Cust_result.getJSONObject(i);
            if (Num.getString("customer_id") != null) {
                Log.e("Customer" + i, Num.getString("customer_name"));
                Contentvalues.put(DB_Contract.Customer._ID, Num.getString("customer_id"));
                Contentvalues.put(DB_Contract.Customer.CODE, Num.getString("customer_code"));
                Contentvalues.put(DB_Contract.Customer.ADDRESS, Num.getString("customer_address"));
                Contentvalues.put(DB_Contract.Customer.PHONE, Num.getString("customer_phone"));
                Contentvalues.put(DB_Contract.Customer.NAME, Num.getString("customer_name"));
                Contentvalues.put(DB_Contract.Customer.STATUS, Num.getString("customer_status"));
                Contentvalues.put(DB_Contract.Customer.NOTE, Num.getString("customer_note"));
                Contentvalues.put(DB_Contract.Customer.PAYMENT, Num.getString("customer_price_payment"));
                Contentvalues.put(DB_Contract.Customer.Remain, Num.getString("customer_price_remain"));
                Contentvalues.put(DB_Contract.Customer.SPEED, Num.getString("customer_speed"));
                Contentvalues.put(DB_Contract.Customer.PASSWORD, "123");
                Contentvalues.put(DB_Contract.Customer.USER_NAME, Num.getString("customer_username"));
                Contentvalues.put(DB_Contract.Customer.DISCOUNTS, Num.getString("customer_discount"));
                if (db.insert(DB_Contract.Customer.TABLE_NAME, null, Contentvalues) == -1)
                    return false;
                Contentvalues.clear();
            }

        }
        Log.e("Customer", "Done set data");
        return true;
    }

    public boolean Set_Users(JSONObject Users_List, SQLiteDatabase db) throws org.json.JSONException {
        JSONArray User_Result = Users_List.getJSONArray("user");
        ContentValues Contentvalues = new ContentValues();
        for (int i = 0; i < User_Result.length(); i++) {
            JSONObject Num = User_Result.getJSONObject(i);
            Contentvalues.put(DB_Contract.Users._ID, Num.getString("user_id"));
            Contentvalues.put(DB_Contract.Users.NAME, Num.getString("user_name"));
            Contentvalues.put(DB_Contract.Users.PASSWORD, Num.getString("user_password"));
            Contentvalues.put(DB_Contract.Users.USER_NAME, Num.getString("user_name"));
            if (db.insert(DB_Contract.Users.TABLE_NAME, null, Contentvalues) == -1)
                return false;
            Contentvalues.clear();
        }
        Log.e("User", "Done set data");
        return true;
    }

    public boolean Set_History(JSONObject History_List, SQLiteDatabase db) throws org.json.JSONException {
        JSONArray History_Result = History_List.getJSONArray("history");
        ContentValues Contentvalues = new ContentValues();
        for (int i = 0; i < History_Result.length(); i++) {
            JSONObject Num = History_Result.getJSONObject(i);
            Contentvalues.put(DB_Contract.History._ID, Num.getString("history_id"));
            Contentvalues.put(DB_Contract.History.HISTORY_DATE, Num.getString("history_date"));
            Contentvalues.put(DB_Contract.History.CUST_ID, Num.getString("customer_id"));
            Contentvalues.put(DB_Contract.History.USER_ID, Num.getString("user_id"));
            Contentvalues.put(DB_Contract.History.DAY_CONST, Num.getString("day_constant"));
            Contentvalues.put(DB_Contract.History.DAY_VAR, Num.getString("day_variable"));
            Contentvalues.put(DB_Contract.History.MONTH_OF_DAY, Num.getString("monthOfDay"));
            if (db.insert(DB_Contract.History.TABLE_NAME, null, Contentvalues) == -1)
                return false;
            Contentvalues.clear();
        }
        Log.e("History", "Done set data");
        return true;
    }

    public boolean Set_Item(JSONObject Item_List, SQLiteDatabase db) throws org.json.JSONException {
        JSONArray Item_Result = Item_List.getJSONArray("item");
        ContentValues Contentvalues = new ContentValues();
        for (int i = 0; i < Item_Result.length(); i++) {
            JSONObject Num = Item_Result.getJSONObject(i);
            Contentvalues.put(DB_Contract.Item._ID, Num.getString("item_id"));
            Contentvalues.put(DB_Contract.Item.NAME, Num.getString("item_name"));
            Contentvalues.put(DB_Contract.Item.PRICE, Num.getString("item_price"));
            if (db.insert(DB_Contract.Item.TABLE_NAME, null, Contentvalues) == -1)
                return false;
            Contentvalues.clear();
        }
        Log.e("Item", "Done set data");
        return true;
    }

    public boolean Set_Remainder(JSONObject Remainder_List, SQLiteDatabase db) throws org.json.JSONException {
        JSONArray Remainder_Result = Remainder_List.getJSONArray("reminder");
        ContentValues Contentvalues = new ContentValues();
        for (int i = 0; i < Remainder_Result.length(); i++) {
            JSONObject Num = Remainder_Result.getJSONObject(i);
            Contentvalues.put(DB_Contract.Remainder._ID, Num.getString("reminder_id"));
            Contentvalues.put(DB_Contract.Remainder.CUST_ID, Num.getString("customer_id"));
            Contentvalues.put(DB_Contract.Remainder.Remainder_DATE, Num.getString("date"));
            // here
            Contentvalues.put(DB_Contract.Remainder.Remainder_Note, Num.getString("note"));
            Contentvalues.put(DB_Contract.Remainder.Remainder_Done, Num.getString("status"));
            if (db.insert(DB_Contract.Remainder.TABLE_NAME, null, Contentvalues) == -1)
                return false;
            Contentvalues.clear();
        }
        Log.e("Remainder", "Done set data");
        return true;
    }

    public boolean Set_ItemSold(JSONObject ItemSold_List, SQLiteDatabase db) throws org.json.JSONException {
        JSONArray ItemSold_Result = ItemSold_List.getJSONArray("sold_items");
        ContentValues Contentvalues = new ContentValues();
        for (int i = 0; i < ItemSold_Result.length(); i++) {
            JSONObject Num = ItemSold_Result.getJSONObject(i);
            Contentvalues.put(DB_Contract.Sold_Item.ITEM_ID, Num.getString("item_id"));
            Contentvalues.put(DB_Contract.Sold_Item.USER_ID, Num.getString("item_user"));
            Contentvalues.put(DB_Contract.Sold_Item.Trans_Date, Num.getString("item_date"));
            Contentvalues.put(Buyer_ID, Num.getString("item_buyer"));
            Contentvalues.put(DB_Contract.Sold_Item.Quantity, Num.getString("price_bought"));
            if (db.insert(DB_Contract.Sold_Item.TABLE_NAME, null, Contentvalues) == -1)
                return false;
            Contentvalues.clear();
        }
        Log.e("ItemSold", "Done set data");
        return true;
    }

    public boolean Set_Maintain(JSONObject Maintain_List, SQLiteDatabase db) throws org.json.JSONException {
        JSONArray Maintain_Result = Maintain_List.getJSONArray("maintain");
        ContentValues Contentvalues = new ContentValues();
        for (int i = 0; i < Maintain_Result.length(); i++) {
            JSONObject Num = Maintain_Result.getJSONObject(i);
            Contentvalues.put(DB_Contract.Maintain._ID, Num.getString("maintain_id"));
            Contentvalues.put(DB_Contract.Maintain.CUST_ID, Num.getString("customer_id"));
            Contentvalues.put(DB_Contract.Maintain.Maintain_DATE, Num.getString("date"));
            // here
            Contentvalues.put(DB_Contract.Maintain.Maintain_Note, Num.getString("note"));
            Contentvalues.put(DB_Contract.Maintain.Maintain_Done, Num.getString("status"));
            if (db.insert(DB_Contract.Maintain.TABLE_NAME, null, Contentvalues) == -1)
                return false;
            Contentvalues.clear();
        }
        Log.e("Maintain", "Done set data");
        return true;
    }


    public String Insert_History(SQLiteDatabase db, String User_ID, Person_Customer customer, Double payment) {
        String Date = DateFormat.format("yyyy-MM-dd hh-mm-ss", new Date().getTime()).toString();
        ContentValues Contentvalues = new ContentValues();
        Contentvalues.put(DB_Contract.History.CUST_ID, customer.getId());
        Contentvalues.put(DB_Contract.History.USER_ID, User_ID);
        Log.e("String",customer.getId()+" "+User_ID);
        Contentvalues.put(DB_Contract.History.DAY_CONST, customer.getRemain());
        Contentvalues.put(DB_Contract.History.DAY_VAR, payment);
        Contentvalues.put(DB_Contract.History.HISTORY_DATE, Date);
        if (db.insert(DB_Contract.History.TABLE_NAME, null, Contentvalues) != -1) {
            if (db.rawQuery("update " + DB_Contract.Customer.TABLE_NAME + " set " + DB_Contract.Customer.PAYMENT + " = " + DB_Contract.Customer.PAYMENT + "+? and " + DB_Contract.Customer.Remain +
                            "= " + DB_Contract.Customer.Remain + "-? where " + DB_Contract.Customer._ID + " =?"
                    , new String[]{String.valueOf(payment), String.valueOf(payment), String.valueOf(customer.getId())}) != null) {
                Contentvalues.clear();
                Contentvalues.put(DB_Contract.Cache.TABLE, "history");
                Contentvalues.put(DB_Contract.Cache.OPERATION, "INSERT");
                Contentvalues.put(DB_Contract.Cache.DONE, 0);
                Contentvalues.put(DB_Contract.Cache.DATE, Date);
                Contentvalues.put(DB_Contract.Cache.QUERY, "INSERT INTO history (customer_id," +
                        "user_id,day_constant,day_variable) VALUES " +
                        "('" + customer.getId() + "', '" + User_ID + "', '" + customer.getRemain() + "', '" + payment + "')");
                if (db.insert(DB_Contract.Cache.TABLE_NAME, null, Contentvalues) != -1) {
                    Contentvalues.clear();
                    Contentvalues.put(DB_Contract.Cache.TABLE, "customer");
                    Contentvalues.put(DB_Contract.Cache.OPERATION, "UPDATE");
                    Contentvalues.put(DB_Contract.Cache.DONE, 0);
                    Contentvalues.put(DB_Contract.Cache.DATE, Date);
                    Contentvalues.put(DB_Contract.Cache.QUERY, "update customer set customer_price_payment = customer_price_payment+"
                            + payment + " and customer_price_remain = customer_price_remain-" + payment + " where customer_id = " +
                            customer.getId());
                    Log.e("Update", "update customer set customer_price_payment = customer_price_payment+"
                            + payment + "and customer_price_remain = customer_price_remain-" + payment + " where customer_id = " +
                            customer.getId());
                    if (db.insert(DB_Contract.Cache.TABLE_NAME, null, Contentvalues) != -1) {
                        return "Done";
                    } else {
                        return "Error in updating Cache \"Update query\"";
                    }
                } else {
                    return "Error in updating Cache \"Insert query\"";
                }
            } else {
                return "Error in update customer data";
            }
        } else {
            return "Error in inserting history";
        }
    }

    public boolean Insert_Maintain(SQLiteDatabase db, String Cust_ID, String Date, String Note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_Contract.Maintain.CUST_ID, Cust_ID);
        contentValues.put(DB_Contract.Maintain.Maintain_DATE, Date);
        contentValues.put(DB_Contract.Maintain.Maintain_Note, Note);
        contentValues.put(DB_Contract.Maintain.Maintain_Done, "0");
        if (db.insert(DB_Contract.Maintain.TABLE_NAME, null, contentValues) != -1) {
            contentValues.clear();
            contentValues.put(DB_Contract.Cache.TABLE, "maintain");
            contentValues.put(DB_Contract.Cache.OPERATION, "INSERT");
            contentValues.put(DB_Contract.Cache.DONE, 0);
            String instDate = DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString();
            contentValues.put(DB_Contract.Cache.DATE, instDate);
            contentValues.put(DB_Contract.Cache.QUERY, "INSERT INTO maintain (customer_id,date,status,note)" +
                    " VALUES ('" + Cust_ID + "', '" + Date + "','0','" + Note + "' )");
            return (db.insert(DB_Contract.Cache.TABLE_NAME, null, contentValues) != -1);
        } else
            return false;
    }
    public boolean Insert_Remainder(SQLiteDatabase db, String Cust_ID, String Date, String Note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_Contract.Remainder.CUST_ID, Cust_ID);
        contentValues.put(DB_Contract.Remainder.Remainder_DATE, Date);
        contentValues.put(DB_Contract.Remainder.Remainder_Note, Note);
        contentValues.put(DB_Contract.Remainder.Remainder_Done, "0");
        if (db.insert(DB_Contract.Remainder.TABLE_NAME, null, contentValues) != -1) {
            contentValues.clear();
            contentValues.put(DB_Contract.Cache.TABLE, "reminder");
            contentValues.put(DB_Contract.Cache.OPERATION, "INSERT");
            contentValues.put(DB_Contract.Cache.DONE, 0);
            String instDate = DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString();
            contentValues.put(DB_Contract.Cache.DATE, instDate);
            contentValues.put(DB_Contract.Cache.QUERY, "INSERT INTO reminder (customer_id,date,status,note)" +
                    " VALUES ('" + Cust_ID + "', '" + Date + "','0','" + Note + "' )");
            return (db.insert(DB_Contract.Cache.TABLE_NAME, null, contentValues) != -1);
        } else
            return false;
    }

    public boolean Insert_Item(SQLiteDatabase db, String Price, String Item) {
        ContentValues Contentvalues = new ContentValues();
        Contentvalues.put(DB_Contract.Item.NAME, Item);
        Contentvalues.put(DB_Contract.Item.PRICE, Price);
        if (db.insert(DB_Contract.Item.TABLE_NAME, null, Contentvalues) == -1) {
            return false;
        } else {
            Contentvalues.clear();
            Contentvalues.put(DB_Contract.Cache.TABLE, "item");
            Contentvalues.put(DB_Contract.Cache.OPERATION, "INSERT");
            Contentvalues.put(DB_Contract.Cache.DONE, 0);
            String Date = DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString();
            Contentvalues.put(DB_Contract.Cache.DATE, Date);
            Contentvalues.put(DB_Contract.Cache.QUERY, "INSERT INTO item (item_name,item_price)" +
                    " VALUES ('" + Item + "', '" + Price + "')");
            return (db.insert(DB_Contract.Cache.TABLE_NAME, null, Contentvalues) != -1);
        }
    }

    //fe desktop (ID)
    public String Insert_Item_Sold(SQLiteDatabase db, String User_Id, String Item_id
            , int Quantity, Person_Customer person, Double payment, Double remain) {
        Double new_payment = Double.parseDouble(person.getPrice()) + payment;
        Double new_remain = Double.parseDouble(person.getRemain()) + remain;
        String Date = DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString();
        ContentValues Contentvalues = new ContentValues();
        Contentvalues.put(Buyer_ID, person.getId());
        Contentvalues.put(DB_Contract.Sold_Item.USER_ID, User_Id);
        Contentvalues.put(DB_Contract.Sold_Item.Quantity, Quantity);
        Contentvalues.put(DB_Contract.Sold_Item.ITEM_ID, Item_id);
        Contentvalues.put(DB_Contract.Sold_Item.Trans_Date, Date);
        if (db.insert(DB_Contract.Sold_Item.TABLE_NAME, null, Contentvalues) != -1) {
            Contentvalues.clear();
            Contentvalues.put(DB_Contract.Customer.Remain, new_payment);
            Contentvalues.put(DB_Contract.Customer.PAYMENT, new_remain);
            if (db.update(DB_Contract.Customer.TABLE_NAME, Contentvalues, DB_Contract.Customer._ID + " = ?"
                    , new String[]{person.getId() + ""}) != 0) {
                Contentvalues.clear();
                Contentvalues.put(DB_Contract.Cache.TABLE, "sold_items");
                Contentvalues.put(DB_Contract.Cache.OPERATION, "INSERT");
                Contentvalues.put(DB_Contract.Cache.DONE, 0);
                Contentvalues.put(DB_Contract.Cache.DATE, Date);
                Contentvalues.put(DB_Contract.Cache.QUERY, "INSERT INTO sold_items (item_id,item_user,item_date,item_buyer)" +
                        " VALUES ('" + Item_id + "', '" + User_Id + "', '" + Date + "', '" + person.getId() + "')");
                if (db.insert(DB_Contract.Cache.TABLE_NAME, null, Contentvalues) != -1) {
                    Contentvalues.clear();
                    Contentvalues.put(DB_Contract.Cache.TABLE, "customer");
                    Contentvalues.put(DB_Contract.Cache.OPERATION, "UPDATE");
                    Contentvalues.put(DB_Contract.Cache.DONE, 0);
                    Contentvalues.put(DB_Contract.Cache.DATE, Date);
                    Contentvalues.put(DB_Contract.Cache.QUERY, "update customer set customer_price_payment = " + new_payment + " and customer_price_remain =" + new_remain + " where customer_id = " +
                            person.getId());
                    Log.e("Update", "update customer set customer_price_payment = " + new_payment + " and customer_price_remain =" + new_remain + " where customer_id = " +
                            person.getId());
                    if (db.insert(DB_Contract.Cache.TABLE_NAME, null, Contentvalues) != -1) {
                        return "Done";
                    } else {
                        return "Error in updating Cache \"Update query\"";
                    }
                } else {
                    return "Error in updating Cache \"Insert query\"";
                }
            } else {
                return "Error in update customer data";
            }
        } else {
            return "Error in inserting item sold";
        }
    }

    public boolean update_Maintaine_Status(SQLiteDatabase db, String Main_id) {
        ContentValues Contentvalues = new ContentValues();
        Contentvalues.put(DB_Contract.Maintain.Maintain_Done, "1");
        if (db.update(DB_Contract.Maintain.TABLE_NAME, Contentvalues,
                DB_Contract.Maintain._ID + "=?", new String[]{Main_id}) == 0)
            return false;
        else {
            String Date = DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString();
            Contentvalues.clear();
            Contentvalues.put(DB_Contract.Cache.TABLE, "maintain");
            Contentvalues.put(DB_Contract.Cache.OPERATION, "UPDATE");
            Contentvalues.put(DB_Contract.Cache.DONE, 0);
            Contentvalues.put(DB_Contract.Cache.DATE, Date);
            Contentvalues.put(DB_Contract.Cache.QUERY, "update maintain set status = 1 where maintain_id = " +
                    Main_id);
            return (db.insert(DB_Contract.Cache.TABLE_NAME, null, Contentvalues) != -1);

        }
    }

    public boolean update_Maintaine_Note(SQLiteDatabase db, String Main_id, String txt) {
        ContentValues Contentvalues = new ContentValues();
        Contentvalues.put(DB_Contract.Maintain.Maintain_Note, txt);
        if (db.update(DB_Contract.Maintain.TABLE_NAME, Contentvalues,
                DB_Contract.Maintain._ID + "=?", new String[]{Main_id}) == 0)
            return false;
        else {
            String Date = DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString();
            Contentvalues.clear();
            Contentvalues.put(DB_Contract.Cache.TABLE, "maintain");
            Contentvalues.put(DB_Contract.Cache.OPERATION, "UPDATE");
            Contentvalues.put(DB_Contract.Cache.DONE, 0);
            Contentvalues.put(DB_Contract.Cache.DATE, Date);
            Contentvalues.put(DB_Contract.Cache.QUERY, "update maintain set note = " + txt + " where maintain_id = " +
                    Main_id);
            return (db.insert(DB_Contract.Cache.TABLE_NAME, null, Contentvalues) != -1);

        }
    }

    public boolean update_Remainder_Status(SQLiteDatabase db, String Remain_id) {
        ContentValues Contentvalues = new ContentValues();
        Contentvalues.put(DB_Contract.Remainder.Remainder_Done, "1");
        if (db.update(DB_Contract.Remainder.TABLE_NAME, Contentvalues,
                DB_Contract.Remainder._ID + "=?", new String[]{Remain_id}) == 0)
            return false;
        else {
            String Date = DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString();
            Contentvalues.clear();
            Contentvalues.put(DB_Contract.Cache.TABLE, "maintain");
            Contentvalues.put(DB_Contract.Cache.OPERATION, "UPDATE");
            Contentvalues.put(DB_Contract.Cache.DONE, 0);
            Contentvalues.put(DB_Contract.Cache.DATE, Date);
            Contentvalues.put(DB_Contract.Cache.QUERY, "update reminder set status = 1 where reminder_id = " +
                    Remain_id);
            return (db.insert(DB_Contract.Cache.TABLE_NAME, null, Contentvalues) != -1);

        }
    }

    public boolean update_Remainder_Note(SQLiteDatabase db, String Remain_id, String txt) {
        ContentValues Contentvalues = new ContentValues();
        Contentvalues.put(DB_Contract.Remainder.Remainder_Note, txt);
        if (db.update(DB_Contract.Remainder.TABLE_NAME, Contentvalues,
                DB_Contract.Remainder._ID + "=?", new String[]{Remain_id}) == 0)
            return false;
        else {
            String Date = DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString();
            Contentvalues.clear();
            Contentvalues.put(DB_Contract.Cache.TABLE, "maintain");
            Contentvalues.put(DB_Contract.Cache.OPERATION, "UPDATE");
            Contentvalues.put(DB_Contract.Cache.DONE, 0);
            Contentvalues.put(DB_Contract.Cache.DATE, Date);
            Contentvalues.put(DB_Contract.Cache.QUERY, "update reminder set note = " + txt + " where reminder_id = " +
                    Remain_id);
            return (db.insert(DB_Contract.Cache.TABLE_NAME, null, Contentvalues) != -1);

        }
    }

    public Cursor Get_History(SQLiteDatabase db, String User_ID) {
        Cursor cursor = db.rawQuery("select * from " + DB_Contract.History.TABLE_NAME
                        + " as K JOIN " +
                        DB_Contract.Customer.TABLE_NAME + " as lol on " +
                        DB_Contract.History.CUST_ID + " = lol." + DB_Contract.Customer._ID
                        + " and K." + DB_Contract.History.USER_ID + " = ? ",
                new String[]{User_ID});
        Log.e(cursor.getCount()+"","asas");
        return cursor.getCount() == 0 ? null : cursor;
    }

    public boolean Is_Empty(SQLiteDatabase db) {
        if (!db.isOpen()) {
            db = this.getReadableDatabase();
        }
        Cursor m = db.rawQuery("select * from " + DB_Contract.Users.TABLE_NAME, null);
        int count = m.getCount();
        m.close();
        return count == 0;
    }

    public Cursor Get_Items(SQLiteDatabase db) {
        Cursor cursor;
        cursor = db.rawQuery("Select * from " + DB_Contract.Item.TABLE_NAME, null);
        return cursor.getCount() == 0 ? null : cursor;
    }

    public Cursor Get_SoldItems(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from " + DB_Contract.Sold_Item.TABLE_NAME
                        + " as K JOIN " +
                        DB_Contract.Item.TABLE_NAME + " as lol on " +
                        "K." + DB_Contract.Sold_Item.ITEM_ID + " = lol." + DB_Contract.Item._ID
                , null);
        return cursor.getCount() == 0 ? null : cursor;
    }

    public String Get_Cust_Name(SQLiteDatabase db, String id) {
        Cursor cursor = db.rawQuery("Select " + DB_Contract.Customer.NAME + " from " + DB_Contract.Customer.TABLE_NAME +
                " where " + DB_Contract.Customer._ID + " = ?", new String[]{id});
        cursor.moveToFirst();
        String return_val = cursor.getCount() == 0 ? null : cursor.getString(0);
        cursor.close();
        return return_val;
    }

    public String Get_User_Name(SQLiteDatabase db, String id) {
        Cursor cursor = db.rawQuery("Select " + DB_Contract.Users.NAME + " from " + DB_Contract.Users.TABLE_NAME +
                " where " + DB_Contract.Users._ID + " = ?", new String[]{id});
        cursor.moveToFirst();
        String return_val = cursor.getCount() == 0 ? null : cursor.getString(0);
        cursor.close();
        return return_val;
    }

    public Cursor Get_Maintainace(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from " + DB_Contract.Maintain.TABLE_NAME
                        + " as K JOIN " +
                        DB_Contract.Customer.TABLE_NAME + " as lol on " +
                        DB_Contract.History.CUST_ID + " = lol." + DB_Contract.Customer._ID
                , null);
        if (cursor.getCount() != 0) {
            return cursor;
        } else {
            Log.e("No Data", "No");
            return null;
        }
    }

    public String Get_Transaction_Date(SQLiteDatabase db, String Cust_ID) {
        Cursor cursor = db.rawQuery("select " + DB_Contract.History.HISTORY_DATE + " from " + DB_Contract.History.TABLE_NAME
                        + " where " + DB_Contract.History.CUST_ID + " =?",
                new String[]{Cust_ID});
        String return_val;
        if (cursor.getCount() == 0)
            return_val = null;
        else {
            cursor.moveToLast();
            return_val = cursor.getString(0);
        }
        cursor.close();
        return return_val;
    }

    public boolean Update_Chace(String num, SQLiteDatabase db) {
        ContentValues m = new ContentValues();
        m.put(DB_Contract.Cache.DONE, "1");
        return (db.update(DB_Contract.Cache.TABLE_NAME, m,
                DB_Contract.Cache._ID + " = ?", new String[]{num})) != 0;
    }

}