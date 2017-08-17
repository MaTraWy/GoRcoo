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
import com.matrawy.a7oda.gorcoo.adapters.History_Adapter;

import java.util.ArrayList;

import static com.matrawy.a7oda.gorcoo.R.id.history_search;
import static com.matrawy.a7oda.gorcoo.R.id.row_list;

/**
 * Created by 7oda on 3/15/2017.
 */

public class Log_History_Fragment extends Fragment {
    private View root;
    private SearchView search;
    private DB db;
    private SQLiteDatabase db_heleper;
    private Person user;
    private History_Adapter adapter;
    private ListView list;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frament_history, container, false);
        init();
        list.setAdapter(adapter);
        listner();
        return root;
    }

    private void init() {
        //table = (TableLayout) root.findViewById(history_table);
        search = (SearchView) root.findViewById(history_search);
        list = (ListView) root.findViewById(row_list);
    }

    private void Filling_Table() {
        ArrayList<Converge_Data_Class> test = new ArrayList<>();
        Cursor M = db.Get_History(db_heleper,String.valueOf(user.getId()));//String.valueOf(user.getId()));
        if (M == null)
            return;
        while (M.moveToNext()) {
            Converge_Data_Class dude = new Converge_Data_Class();
            dude.setNum(M.getString(0));
            dude.setCode(M.getString(8));
            dude.setName(M.getString(9));
            dude.setAddress(M.getString(18));
            dude.setPayment(Double.parseDouble(M.getString(14)));
            dude.setRemain(Double.parseDouble(M.getString(13)));
            dude.setDate(M.getString(5));
            dude.setUser(db.Get_User_Name(db_heleper, M.getString(1)));
            test.add(dude);
        }
        adapter = new History_Adapter(getActivity(), test);

    }

    private void listner() {
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
        savedInstanceState.putSerializable("User", user); //puting the saved instance state
    }

}
