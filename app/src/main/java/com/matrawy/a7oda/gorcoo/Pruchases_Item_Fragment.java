package com.matrawy.a7oda.gorcoo;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.matrawy.a7oda.gorcoo.Data.DB;

import static com.matrawy.a7oda.gorcoo.R.id.item_add;
import static com.matrawy.a7oda.gorcoo.R.id.item_item;
import static com.matrawy.a7oda.gorcoo.R.id.item_price;

/**
 * Created by 7oda on 3/21/2017.
 */

public class Pruchases_Item_Fragment extends Fragment {
    private View Root;
    private EditText price;
    private EditText item;
    private ImageView add;
    private DB db;
    private SQLiteDatabase db_helepr;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Root = inflater.inflate(R.layout.fragment_item,container,false);
        init();
        return Root;
    }
    private void init()
    {
        price =(EditText) Root.findViewById(item_price);
        item =(EditText) Root.findViewById(item_item);
        add = (ImageView) Root.findViewById(item_add);
        db = new DB(getActivity());
        db_helepr = db.getWritableDatabase();
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setPositiveButton("Oky", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_data();
            }
        });
    }
    private void insert_data()
    {
        if (item.getText().length() == 0 || price.getText().length() == 0) {
            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder.setMessage("Please fill the free area");
            alertDialogBuilder.show();
            return;
        }
        if(db.Insert_Item(db_helepr,price.getText().toString(),item.getText().toString()))
        {
            alertDialogBuilder.setTitle("Successful");
            alertDialogBuilder.setMessage("Item "+item.getText().toString()+" Successfuly added");
            alertDialogBuilder.show();
            return;
        }
        else
        {
            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder.setMessage("Error While adding Item "+item.getText().toString());
            alertDialogBuilder.show();
            return;
        }
    }
}
