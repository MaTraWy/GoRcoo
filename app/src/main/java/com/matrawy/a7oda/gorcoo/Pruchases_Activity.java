package com.matrawy.a7oda.gorcoo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by 7oda on 3/21/2017.
 */

public class Pruchases_Activity extends AppCompatActivity {
    private Pruchases_Fragment pruchases_fragment;
    Fragment TopOfStack;
    Person User;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        User = (Person) getIntent().getSerializableExtra("User");
        Bundle m = new Bundle();
        m.putSerializable("User", getIntent().getSerializableExtra("User"));
        pruchases_fragment = new Pruchases_Fragment();
        pruchases_fragment.setArguments(m);
        if (savedInstanceState != null) {
            pruchases_fragment = (Pruchases_Fragment) getSupportFragmentManager().getFragment(savedInstanceState, "pruchases_fragment");
            TopOfStack = getSupportFragmentManager().getFragment(savedInstanceState, "TopOfStack");
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.purchase_container, pruchases_fragment, "pruchases_fragment").commit();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            super.onBackPressed();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "pruchases_fragment", pruchases_fragment);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            Log.e("OnSave", fragmentTag);
            getSupportFragmentManager().putFragment(outState, "TopOfStack", getSupportFragmentManager().findFragmentByTag(fragmentTag));
        }
        Log.e("Save Instance", "Done");
        outState.putSerializable("User", User);
    }

}
