package com.matrawy.a7oda.gorcoo.Data;

import android.provider.BaseColumns;

/**
 * Created by 7oda on 4/14/2017.
 */

public class DB_Contract {
    public static final class Customer implements BaseColumns {
        public static final String TABLE_NAME = "Customer";
        public static final String NAME = "name";
        public static final String USER_NAME = "user_name";
        public static final String PASSWORD = "password";
        public static final String SPEED = "speed";
        public static final String PAYMENT = "Payment";
        public static final String Remain = "remian";
        public static final String NOTE = "note";
        public static final String DISCOUNTS = "discounts";
        public static final String STATUS = "status";
        public static final String ADDRESS = "address";
        public static final String PHONE = "phone";
        public static final String CODE = "code";
        //floor & flat

    }

    public static final class Users implements BaseColumns {
        public static final String TABLE_NAME = "Users";
        public static final String NAME = "name";
        public static final String USER_NAME = "user_name";
        public static final String PASSWORD = "password";
    }

    public static final class Item implements BaseColumns {
        public static final String TABLE_NAME = "Item";
        public static final String NAME = "Item_Name";
        public static final String PRICE = "Item_Price";
    }
    public static final class Sold_Item implements BaseColumns
    {
        public static final String TABLE_NAME = "Sold_Item";
        public static final String ITEM_ID = "Item_ID";
        public static final String USER_ID = "User_ID";
        public static final String Buyer_ID = "Buyer_ID";
        public static final String Trans_Date = "Date";
        public static final String Quantity = "Quantity";
    }
    public static final class History implements BaseColumns
    {
        public static final String TABLE_NAME = "History";
        public static final String USER_ID = "User_ID";
        public static final String CUST_ID = "Cust_ID";
        public static final String DAY_CONST = "Day_Const";
        public static final String DAY_VAR = "Day_Var";
        public static final String HISTORY_DATE = "HISTORY_DATE";
        public static final String MONTH_OF_DAY = "MONTH_OF_DAY";
    }
    public static final class Maintain implements BaseColumns
    {
        public static final String TABLE_NAME = "Maintain";
        public static final String CUST_ID = "Cust_ID";
        public static final String Maintain_DATE = "Maintain_DATE";
        public static final String Maintain_Done = "Maintain_Done";
        public static final String Maintain_Note = "Maintain_Note";

    }
    public static final class Remainder implements BaseColumns
    {
        public static final String TABLE_NAME = "Remainder";
        public static final String CUST_ID = "Cust_ID";
        public static final String Remainder_DATE = "Remainder_DATE";
        public static final String Remainder_Done = "Remainder_Done";
        public static final String Remainder_Note = "Remainder_Note";
    }
    public static final class Cache implements BaseColumns
    {
        public static final String TABLE_NAME = "Cache";
        public static final String TABLE = "Table_Name";
        public static final String QUERY = "Query";
        public static final String OPERATION = "Operation";
        public static final String DONE = "Done";
        public static final String DATE = "Date";

    }
    //usless table
   /* public static final class Cache_Update implements BaseColumns
    {
        public static final String TABLE_NAME = "Cache_Update";
        public static final String TABLE = "Table_Name";
        public static final String DATE = "Date";
        public static final class Cache_Update_Values implements BaseColumns
        {
            public static final String TABLE_NAME = "Cache_Update_Values";
            public static final String ID = "Id";
            public static final String KEY = "Key";
            public static final String VALUE = "Value";
        }
    }*/
}
