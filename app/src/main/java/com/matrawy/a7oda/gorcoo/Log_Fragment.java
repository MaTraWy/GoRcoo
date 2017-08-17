package com.matrawy.a7oda.gorcoo;

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
import static com.matrawy.a7oda.gorcoo.R.id.Log_history;
import static com.matrawy.a7oda.gorcoo.R.id.Log_nonpayment;
import static com.matrawy.a7oda.gorcoo.R.id.Log_total;

/**
 * Created by 7oda on 3/7/2017.
 */

public class Log_Fragment extends Fragment {
    private ImageView log_history;
    private ImageView log_total;
    private ImageView non_payment;
    private View root;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private android.support.v7.app.ActionBar m;
    private Bundle bundle;
    private Person User;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            User = (Person) savedInstanceState.getSerializable("User");
        } else {
            User = (Person) getArguments().getSerializable("User");
        }
        bundle = new Bundle();
        bundle.putSerializable("User", User);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_log, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        m = ((AppCompatActivity) getActivity()).getSupportActionBar();

        init();
        listner();
        return root;
    }

    public void init() {
        log_history = (ImageView) root.findViewById(Log_history);
        log_total = (ImageView) root.findViewById(Log_total);
        non_payment = (ImageView) root.findViewById(Log_nonpayment);
    }

    private void listner() {
        log_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log_History_Fragment mm = new Log_History_Fragment();
                mm.setArguments(bundle);
                fragmentTransaction.replace(R.id.log_container, mm, "History").addToBackStack("History").commit();
                m.setBackgroundDrawable(new ColorDrawable(Color.rgb(37, 178, 143)));
                m.setTitle(" History");
                m.setIcon(R.drawable.history_icon);
                m.setDisplayShowHomeEnabled(true);
                m.show();
            }
        });
        log_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log_Totalpage_Fragment mm = new Log_Totalpage_Fragment();
                mm.setArguments(bundle);
                mm.setArguments(bundle);
                fragmentTransaction.replace(R.id.log_container, mm, "Total").addToBackStack("Total").commit();
                m.setIcon(R.drawable.total_icon);
                //m.setDisplayShowCustomEnabled(true);
                m.setDisplayShowTitleEnabled(true);
                m.setTitle("Total");
                m.setDisplayShowHomeEnabled(true);
                //m.setTitle(s);
                // m.setCustomView(m);
                m.setBackgroundDrawable(new ColorDrawable(Color.rgb(51, 255, 254)));
                m.show();
            }
        });
        non_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log_Nonoayment_Fragment mm = new Log_Nonoayment_Fragment();
                mm.setArguments(bundle);
                fragmentTransaction.replace(R.id.log_container, mm, "NonPayment").addToBackStack("NonPayment").commit();
                m.setBackgroundDrawable(new ColorDrawable(Color.rgb(37, 178, 143)));
                m.setTitle(" Nonpayments");
                m.setIcon(R.drawable.nonpayment_icon);
                m.setDisplayShowHomeEnabled(true);
                m.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("User", user);
    }
}
