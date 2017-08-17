package com.matrawy.a7oda.gorcoo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.matrawy.a7oda.gorcoo.Data.DB;
import com.matrawy.a7oda.gorcoo.adapters.Converge_Data_Class;
import com.matrawy.a7oda.gorcoo.adapters.Non_Payment_Adapter;

import java.util.ArrayList;

import static com.matrawy.a7oda.gorcoo.R.id.nonpay_search;
import static com.matrawy.a7oda.gorcoo.R.id.row_list_nonpay;

/**
 * Created by 7oda on 3/15/2017.
 */

public class Log_Nonoayment_Fragment extends Fragment{
    SearchView search;
    ListView list;
    View root;
    private DB db;
    private SQLiteDatabase db_heleper;
    private Person user;
    private Non_Payment_Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            user =(Person) getArguments().getSerializable("User");
        } else {
            user =(Person) savedInstanceState.getSerializable("User");
        }
        db = new DB(getActivity());
        db_heleper = db.getReadableDatabase();
        Filling_Table();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_nonpayment,container,false);
        init();
        list.setAdapter(adapter);
        listner();
        return root;
    }
    private void init()
    {
        search = (SearchView) root.findViewById(nonpay_search);
        list = (ListView) root.findViewById(row_list_nonpay);
        db = new DB(getActivity());
        db_heleper = db.getReadableDatabase();
    }
    private void Filling_Table() {
        ArrayList<Converge_Data_Class> test = new ArrayList<>();
        Cursor M = db.Get_Customer(db_heleper);
        if (M == null)
            return;
        while (M.moveToNext()) {
            if(Double.parseDouble(M.getString(6))!=0.0) {
                Converge_Data_Class dude = new Converge_Data_Class();
                dude.setNum(M.getString(0));
                dude.setCode(M.getString(1));
                dude.setName(M.getString(2));
                dude.setAddress(M.getString(11));
                dude.setPayment(Double.parseDouble(M.getString(7)));
                dude.setRemain(Double.parseDouble(M.getString(6)));
                String s = db.Get_Transaction_Date(db_heleper,dude.getNum());
                dude.setDate(s==null? "No transaction date":s);
                test.add(dude);
            }
        }
        M.close();
        adapter = new Non_Payment_Adapter(getActivity(), test);

    }
    void listner()
    {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("User", user);
    }
}
