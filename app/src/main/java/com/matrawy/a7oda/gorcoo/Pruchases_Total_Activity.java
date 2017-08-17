package com.matrawy.a7oda.gorcoo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by 7oda on 3/21/2017.
 */

public class Pruchases_Total_Activity extends AppCompatActivity {
    Pruchases_Total_Fragment pruchases_total_fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pruchases_total_fragment = new Pruchases_Total_Fragment();
        setContentView(R.layout.activity_purchasetotal);
        getSupportActionBar().setIcon(R.drawable.total_icon);
        getSupportActionBar().setTitle(" Total");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        getSupportActionBar().show();

        if(savedInstanceState!=null)
        {

        }
        else
        {
            getSupportFragmentManager().beginTransaction().add(R.id.totall_container,pruchases_total_fragment,"pruchases_total_fragment").commit();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            super.onBackPressed();
        }
        return true;
    }
}
