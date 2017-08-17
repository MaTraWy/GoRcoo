package com.matrawy.a7oda.gorcoo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by 7oda on 3/14/2017.
 */

public class Home_Activity extends AppCompatActivity {
    private Home_Fragment main_fragment;
    Fragment TopOfStack;
    private Person User;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            main_fragment = (Home_Fragment) getSupportFragmentManager().getFragment(savedInstanceState, "main_fragment");
            TopOfStack = getSupportFragmentManager().getFragment(savedInstanceState, "TopOfStack");
        } else {
            User = new Person();
            User = (Person) getIntent().getSerializableExtra("User");
            main_fragment = new Home_Fragment();
            Bundle packge = new Bundle();
            packge.putSerializable("User", User);
            main_fragment.setArguments(packge);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, main_fragment, "main_fragment")
                    .commit();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            //Log.e("Stack", "There is iTem = " + getSupportFragmentManager().getBackStackEntryCount());
            getFragmentManager().popBackStack();
            super.onBackPressed();
        }
        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "main_fragment", main_fragment);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            Log.e("OnSave", fragmentTag);
            getSupportFragmentManager().putFragment(outState, "TopOfStack", getSupportFragmentManager().findFragmentByTag(fragmentTag));
        }
        Log.e("Save Instance", "Done");
        outState.putSerializable("User", User);
    }
}
