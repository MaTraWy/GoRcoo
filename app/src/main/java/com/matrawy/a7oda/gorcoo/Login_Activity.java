package com.matrawy.a7oda.gorcoo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Login_Activity extends AppCompatActivity {
    private Login_Fragment login_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_);

        login_fragment = new Login_Fragment();

        //check for two ui pane

        if (savedInstanceState != null) {
            login_fragment = (Login_Fragment) getSupportFragmentManager().getFragment(savedInstanceState, "login_fragment");

        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.login_container, login_fragment, "login_fragment")
                    .commit();
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "login_fragment", login_fragment);
    }
}
