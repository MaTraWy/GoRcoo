package com.matrawy.a7oda.gorcoo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.matrawy.a7oda.gorcoo.R.id.total_customer;
import static com.matrawy.a7oda.gorcoo.R.id.total_user;

/**
 * Created by 7oda on 3/21/2017.
 */

public class Pruchases_Total_Fragment extends Fragment {
    ImageView user;
    ImageView costomer;
    View root;
    private android.support.v7.app.ActionBar m;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = ((AppCompatActivity)getActivity()).getSupportActionBar();
        m.setShowHideAnimationEnabled(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_purchasetotall,container,false);
        m.setTitle("  Total");
        m.setIcon(R.drawable.total_icon);
        m.setDisplayShowHomeEnabled(true);
        m.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        m.show();
        init();

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.totall_container,new Pruchase_Total_UserPage_Fragment());
                fragmentTransaction.addToBackStack(null); //add the current fragment in fragment run stack.
                fragmentTransaction.commit();
                m.setTitle("  User");
                m.setIcon(R.drawable.use_icon);
                m.setDisplayShowHomeEnabled(true);
                m.setBackgroundDrawable(new ColorDrawable(Color.rgb(36, 178, 178)));
                m.show();
            }
        });
        costomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.totall_container,new Pruchase_Total_Customer_Fragment());
                fragmentTransaction.addToBackStack(null); //add the current fragment in fragment run stack.
                fragmentTransaction.commit();
                m.setTitle("  Customer");
                m.setIcon(R.drawable.cutomer_icon);
                m.setDisplayShowHomeEnabled(true);
                m.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
                m.show();

            }
        });
        return root;
    }
    void init()
    {
        user = (ImageView) root.findViewById(total_user);
        costomer = (ImageView) root.findViewById(total_customer);
    }
}
