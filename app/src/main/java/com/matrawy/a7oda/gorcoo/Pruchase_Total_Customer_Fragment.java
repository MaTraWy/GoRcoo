package com.matrawy.a7oda.gorcoo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.Data.DB;
import com.matrawy.a7oda.gorcoo.adapters.Converge_Data_Class;
import com.matrawy.a7oda.gorcoo.adapters.Customer_Adapter;

import java.util.ArrayList;

/**
 * Created by 7oda on 3/23/2017.
 */

public class Pruchase_Total_Customer_Fragment extends Fragment {
    View Root;
    ListView list;
    private TextView price;
    private DB db;
    private SQLiteDatabase db_heleper;
    private Customer_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Root = inflater.inflate(R.layout.fragment_purchasecustomer, container, false);
        init();
        filling_table();
        list.setAdapter(adapter);
        return Root;
    }

    void init() {
        price = (TextView) Root.findViewById(R.id.cust_totall);
        list = (ListView) Root.findViewById(R.id.list_customer);
        db = new DB(getActivity());
        db_heleper = db.getReadableDatabase();
    }

    void filling_table() {
        ArrayList<Converge_Data_Class> test = new ArrayList<>();
        double totall=0.0;
        Cursor M = db.Get_SoldItems(db_heleper);
        if (M == null) {
            Log.e("SoldItems","Nulls");
            return;
        }
        while (M.moveToNext()) {
            Converge_Data_Class dude = new Converge_Data_Class();
            dude.setNum(M.getString(0));
            dude.setCustomer(db.Get_Cust_Name(db_heleper,M.getString(2)));
            dude.setItem(M.getString(7));
            dude.setPayment(Integer.parseInt(M.getString(8)));
            totall+=Double.parseDouble(M.getString(8));
            dude.setUser(db.Get_User_Name(db_heleper,M.getString(4)));
            dude.setDate(M.getString(5));
            test.add(dude);
        }
        adapter = new Customer_Adapter(getActivity(), test);
        price.setText(totall+"");

    }
}

