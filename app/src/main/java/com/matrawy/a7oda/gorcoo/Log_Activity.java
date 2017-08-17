package com.matrawy.a7oda.gorcoo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by 7oda on 3/15/2017.
 */

public class Log_Activity extends AppCompatActivity {
    Log_Fragment Log_fragment;
    Fragment TopOfStack;
    Person User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        User = (Person) getIntent().getSerializableExtra("User");
        Log_fragment = new Log_Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", User);
        Log_fragment.setArguments(bundle);
        if (savedInstanceState != null) {
            Log_fragment = (Log_Fragment) getSupportFragmentManager().getFragment(savedInstanceState, "Log_fragment");
            TopOfStack = getSupportFragmentManager().getFragment(savedInstanceState, "TopOfStack");

        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.log_container, Log_fragment, "Log_fragment")
                    .commit();
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
        getSupportFragmentManager().putFragment(outState, "Log_fragment", Log_fragment);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            Log.e("OnSave", fragmentTag);
            getSupportFragmentManager().putFragment(outState, "TopOfStack", getSupportFragmentManager().findFragmentByTag(fragmentTag));
        }
        Log.e("Save Instance", "Done");
        outState.putSerializable("User", User);
    }
}
