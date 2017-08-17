package com.matrawy.a7oda.gorcoo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.Data.DB;
import com.matrawy.a7oda.gorcoo.Data.Item;
import com.matrawy.a7oda.gorcoo.adapters.Address_Adapter;
import com.matrawy.a7oda.gorcoo.adapters.Item_Adapter;

import java.util.ArrayList;

/**
 * Created by 7oda on 3/21/2017.
 */

public class Pruchases_Customer_Fragment extends Fragment {
    private TextView Cust_add;
    private TextView Cust_name;
    private TextView Cust_number;
    private TextView Cust_const;
    private EditText Cust_var;
    private TextView Cust_item;
    private ImageView Cust_img;
    private Person_Customer Customer_data;
    private AlertDialog alertDialog;
    private Address_Adapter Person_Array_Adapter;
    private Item_Adapter Item_Array_Adapter;
    private ListView Addres_List;
    private SearchView searchView;
    private ArrayList<String> Per_Arr_Item;
    private ArrayList<Item> items;
    private Person user;
    private ArrayList<Person_Customer> Cust_arr;
    private View Root;
    private DB db;
    private String Cust_pos;
    private String Item_pos;
    private SQLiteDatabase db_helper;
    private AlertDialog.Builder alertDialogBuilder;
    private ArrayList<String> data_bundle;
    private Item Current_Item;
    private Person_Customer Current_Customer;
    private boolean Flag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Cust_arr = (ArrayList<Person_Customer>) getArguments().getSerializable("Cust_arr");
            user = (Person) getArguments().getSerializable("User");
            data_bundle = new ArrayList<>();
            for (int i = 0; i < 6; i++)
                data_bundle.add("Empty");
        } else {
            Cust_arr = (ArrayList<Person_Customer>) savedInstanceState.getSerializable("Cust_arr");
            user = (Person) savedInstanceState.getSerializable("User");
            data_bundle = savedInstanceState.getStringArrayList("ArrayList");
            if (data_bundle != null) {
                Cust_add.setText(data_bundle.get(0));
                Cust_name.setText(data_bundle.get(1));
                Cust_number.setText(data_bundle.get(2));
                Cust_const.setText(data_bundle.get(3));
                Cust_var.setText(data_bundle.get(4));
                Cust_item.setText(data_bundle.get(5));
            }
        }
        Root = inflater.inflate(R.layout.fragment_pruchasecustomer1, container, false);
        init();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View dialogView = inflater.inflate(R.layout.searchable_list_dialog, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();


        get_Items();
        user = (Person) getArguments().getSerializable("User");
        Addres_List = (ListView) dialogView.findViewById(R.id.listItems);
        searchView = (SearchView) dialogView.findViewById(R.id.search1);
        Adapter_Filters_Instances();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (Flag)
                    Person_Array_Adapter.getFilter().filter(newText);
                else
                    Item_Array_Adapter.getFilter().filter(newText);
                return false;
            }
        });
        Cust_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addres_List.setAdapter(Person_Array_Adapter);
                Flag = true;
                //Person_Array_Adapter.clear();
                // Person_Array_Adapter.addAll(Per_Arr_List);
                Addres_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Current_Customer = Person_Array_Adapter.getItem(position);
                        if (Current_Customer == null)
                            return;
                        Cust_add.setText(Current_Customer.getAddress());
                        Cust_name.setText(Current_Customer.getName());//Current_Customer.get("NAME"));
                        Cust_number.setText(Current_Customer.getPhone()); //Current_Customer.get("PHONE"));
                        data_bundle.remove(0);
                        data_bundle.add(0, Current_Customer.getAddress());
                        data_bundle.remove(1);
                        data_bundle.add(1, Current_Customer.getName());
                        data_bundle.remove(2);
                        data_bundle.add(2, String.valueOf(Current_Customer.getPhone()));

                        alertDialog.dismiss();

                    }
                });
                alertDialog.show();
            }
        });
        Cust_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag = false;
                Addres_List.setAdapter(Item_Array_Adapter);
                Addres_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Current_Item = Item_Array_Adapter.getItem(position);
                        if (Current_Item == null)
                            return;
                        Cust_item.setText(Current_Item.getName());
                        Cust_const.setText(Current_Item.getPrice());

                        data_bundle.remove(3);
                        data_bundle.add(3, Current_Item.getPrice());
                        data_bundle.remove(4);
                        data_bundle.add(4, Current_Item.getName());
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        Cust_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Print_Module();
            }
        });
        return Root;

    }

    public void init() {
        Cust_add = (TextView) Root.findViewById(R.id.Cust_add);
        Cust_name = (TextView) Root.findViewById(R.id.Cust_name);
        Cust_number = (TextView) Root.findViewById(R.id.Cust_phone);
        Cust_const = (TextView) Root.findViewById(R.id.Cust_const);
        Cust_var = (EditText) Root.findViewById(R.id.Cust_var);
        Cust_var.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Chonburi-Regular.ttf"));
        Cust_item = (TextView) Root.findViewById(R.id.Cust_item);
        Cust_img = (ImageView) Root.findViewById(R.id.Cust_image);
        items = new ArrayList<>();
        db = new DB(getActivity());
        Per_Arr_Item = new ArrayList<>();
        db_helper = db.getReadableDatabase();
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setPositiveButton("Oky", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    private void get_Items() {
        Cursor m = db.Get_Items(db_helper);
        while (m.moveToNext()) {
            Per_Arr_Item.add(m.getString(1));
            Item item = new Item();
            item.setID(m.getString(0));
            item.setName(m.getString(1));
            item.setPrice(m.getString(2));
            items.add(item);
        }
    }

    private void Print_Module() {
        alertDialogBuilder.setPositiveButton("Oky", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if(Cust_item.getText().length()!=0&&Cust_name.getText().length()!=0) {
            if (Cust_var.getText().length() != 0) {
                String Status_Code = db.Insert_Item_Sold(db_helper, user.getId() + "",
                        Current_Item.getID(), 1, Current_Customer, Double.parseDouble(Cust_var.getText().toString()), Double.parseDouble(Cust_const.getText().toString()) - Double.parseDouble(Cust_var.getText().toString()));
                if (Status_Code.equalsIgnoreCase("Done")) {
                    alertDialogBuilder.setTitle("Successful");
                    alertDialogBuilder.setMessage("User " + Current_Customer.getName() + " Successfully bought " + Current_Item.getName());
                    alertDialogBuilder.show();
                    return;
                } else {
                    alertDialogBuilder.setTitle("Error");
                    alertDialogBuilder.setMessage(Status_Code);
                    alertDialogBuilder.show();
                    return;
                }
            } else {
                alertDialogBuilder.setTitle("Missing data");
                alertDialogBuilder.setMessage("Please fill variable price");
                alertDialogBuilder.show();
            }
        }
        else
        {
            alertDialogBuilder.setTitle("Missing data");
            alertDialogBuilder.setMessage("Please Choose Customer & Item");
            alertDialogBuilder.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("User", user); //User
        savedInstanceState.putSerializable("Cust_arr", Cust_arr);//Person Array
        savedInstanceState.putStringArrayList("ArrayList", data_bundle); //data_bundle
    }

    private void Adapter_Filters_Instances() {
        Person_Array_Adapter = new Address_Adapter(getActivity(), Cust_arr);

        Item_Array_Adapter = new Item_Adapter(getActivity(), items);
    }
}