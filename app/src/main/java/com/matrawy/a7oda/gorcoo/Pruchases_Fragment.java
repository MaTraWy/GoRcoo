package com.matrawy.a7oda.gorcoo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.matrawy.a7oda.gorcoo.R.drawable.user;
import static com.matrawy.a7oda.gorcoo.R.id.pruchase_customer;
import static com.matrawy.a7oda.gorcoo.R.id.pruchase_item;
import static com.matrawy.a7oda.gorcoo.R.id.pruchase_total;

/**
 * Created by 7oda on 3/21/2017.
 */

public class Pruchases_Fragment extends Fragment {
    private ImageView jitem;
    private ImageView customer;
    private ImageView total;
    private android.support.v7.app.ActionBar m;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private View Root;
    private Person_Array Per_Arr;
    private Bundle Per_Arr_Bundle;
    private Person User;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            User = (Person) savedInstanceState.getSerializable("User");
        } else {
            User = (Person) getArguments().getSerializable("User");
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        m = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Per_Arr = (Person_Array) getArguments().getSerializable("Per_Arr");
        User = (Person) getArguments().getSerializable("User");

        Per_Arr = new Person_Array(getActivity(), new ProgressDialog(getContext()));
        Thread m = new Thread(Per_Arr);
        m.start();
        Per_Arr_Bundle = new Bundle();
        Per_Arr_Bundle.putSerializable("Cust_arr", Per_Arr.getCustomer_List());
        Per_Arr_Bundle.putSerializable("User", User);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Root = inflater.inflate(R.layout.fragment_purchase, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        init();
        listner();
        return Root;
    }

    private void listner() {
        jitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Pruchases_Item_Fragment item = new Pruchases_Item_Fragment();
                fragmentTransaction.replace(R.id.purchase_container, item, "Item");
                fragmentTransaction.addToBackStack("Item");
                fragmentTransaction.commit();
                m.setIcon(R.drawable.item_icon);
                m.setTitle(" Jitem");
                m.setDisplayShowHomeEnabled(true);
                m.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
                m.show();
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Pruchases_Customer_Fragment Customer = new Pruchases_Customer_Fragment();
                Customer.setArguments(Per_Arr_Bundle);
                fragmentTransaction.replace(R.id.purchase_container, Customer, "Customer");
                fragmentTransaction.addToBackStack("Customer");
                fragmentTransaction.commit();
                m.setIcon(R.drawable.cutomer_icon);
                m.setTitle(" Customer");
                m.setDisplayShowHomeEnabled(true);
                m.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
                m.show();
            }
        });
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getActivity(), Pruchases_Total_Activity.class);
                startActivity(m);
            }
        });
    }

    public void init() {
        jitem = (ImageView) Root.findViewById(pruchase_item);
        customer = (ImageView) Root.findViewById(pruchase_customer);
        total = (ImageView) Root.findViewById(pruchase_total);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("User", user); //puting the saved instance state
    }
}
