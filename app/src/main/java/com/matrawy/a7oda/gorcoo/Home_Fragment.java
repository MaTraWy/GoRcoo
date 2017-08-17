package com.matrawy.a7oda.gorcoo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.matrawy.a7oda.gorcoo.Data.DB;
import com.matrawy.a7oda.gorcoo.Data.Data_Parsing;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.matrawy.a7oda.gorcoo.R.id.main_log;
import static com.matrawy.a7oda.gorcoo.R.id.main_maintains;
import static com.matrawy.a7oda.gorcoo.R.id.main_print;
import static com.matrawy.a7oda.gorcoo.R.id.main_purchase;
import static com.matrawy.a7oda.gorcoo.R.id.main_remainders;
import static com.matrawy.a7oda.gorcoo.R.id.main_setting;

/**
 * Created by 7oda on 3/14/2017.
 */

public class Home_Fragment extends Fragment implements CallBack {
    private ImageView customer;
    private ImageView print;
    private ImageView log;
    private ImageView maintains;
    private ImageView remainder;
    private ImageView purchase;
    private ImageView setting;
    private View root;
    Home_Fragment me;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private android.support.v7.app.ActionBar m;
    private Person user;
    private AlertDialog.Builder alertDialogBuilder;
    private ProgressDialog Progress;
    private Person_Array Per_Arr;
    private Bundle Per_Arr_Bundle;
    private Data_Parsing data;
    private SharedPreferences saved_values;
    SharedPreferences.Editor editor;
    private Handler handler;
    Runnable runnable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            user = (Person) savedInstanceState.get("User");
        else
            user = (Person) getArguments().getSerializable("User");

        Progress = new ProgressDialog(getActivity());
        Progress.setMessage("Retreving Data ...");
        Progress.setTitle("Wait");
        Progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        Progress.setCancelable(false);

        data = new Data_Parsing(getActivity(), Progress);
        data.setCall(this);
        Per_Arr = new Person_Array(getActivity(), Progress);
        Thread mm = new Thread(Per_Arr);
        mm.start();

        m = ((AppCompatActivity) getActivity()).getSupportActionBar();

        alertDialogBuilder = new AlertDialog.Builder(getActivity());

        Per_Arr_Bundle = new Bundle();
        Per_Arr_Bundle.putSerializable("Per_Arr", Per_Arr);
        Per_Arr_Bundle.putSerializable("User", user);

        me = this;
        saved_values = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = saved_values.edit();
        handler = new Handler(); //interface
        runnable = new Runnable() {
            public void run() {
                Log.e("Runnable","Here");
                Internet.isOnline2(new CallBack(){
                    @Override
                    public void callBackFunc(int val) {
                        if (Internet.Internet_flag && Internet.Cloud_flag) {
                            DB db = new DB(getActivity()); //update Looper
                            SQLiteDatabase db_heleper = db.getReadableDatabase();
                            Cursor m = db.Get_Cache(db_heleper);
                            if(m!=null) {
                                data.Operation_Num = 10;
                                while (m.moveToNext()) {
                                    if (m.getString(4).equalsIgnoreCase("0")) {
                                        data.Insert_Data(m.getString(3));
                                        if (!db.Update_Chace(m.getString(0), db_heleper)) {
                                            Toast.makeText(getContext(), "There is an error", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                    }
                                }
                                m.close();
                            }
                            db.close();
                            db_heleper.close();
                        }
                        handler.postDelayed(runnable, 5000);
                    }
                });
            }
        };
        runnable.run();

        Toast.makeText(getContext(), "Welcome" + user.getName(), Toast.LENGTH_LONG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        init();
        Listiner();
        return root;

    }

    private void init() {
        print = (ImageView) root.findViewById(main_print);
        log = (ImageView) root.findViewById(main_log);
        maintains = (ImageView) root.findViewById(main_maintains);
        remainder = (ImageView) root.findViewById(main_remainders);
        purchase = (ImageView) root.findViewById(main_purchase);
        setting = (ImageView) root.findViewById(main_setting);
    }

    private void Listiner() {
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Log_Activity.class);
                in.putExtra("User", user);
                startActivity(in);
            }
        });
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Pruchases_Activity.class);
                in.putExtra("User", user);
                startActivity(in);
            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                PrintPage_Fragmnet print = new PrintPage_Fragmnet();
                Bundle toto = new Bundle();
                toto.putSerializable("Cust_arr", Per_Arr.getCustomer_List());
                toto.putSerializable("User", user);
                print.setArguments(toto);
                fragmentTransaction.replace(R.id.main_container, print, "print");
                fragmentTransaction.addToBackStack("print");
                fragmentTransaction.commit();
                m.setTitle("  Print");
                m.setIcon(R.drawable.print_logo);
                m.setDisplayShowHomeEnabled(true);
                m.setDisplayHomeAsUpEnabled(true);
                m.setBackgroundDrawable(new ColorDrawable(Color.rgb(102, 154, 204)));
                m.show();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Option_Fragment M = new Option_Fragment();
                M.setUser(user);
                M.setData(data);
                M.setHome(me);
                fragmentTransaction.replace(R.id.main_container, M, "setting");
                fragmentTransaction.addToBackStack("setting");
                fragmentTransaction.commit();
                m.setTitle(" Setting");
                m.setIcon(R.drawable.setting_icon);
                m.setDisplayShowHomeEnabled(true);
                m.setDisplayHomeAsUpEnabled(true);
                m.setBackgroundDrawable(new ColorDrawable(Color.rgb(102, 154, 204)));
                m.show();

            }
        });
        remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new Remainder_Fragment(), "remainder");
                fragmentTransaction.addToBackStack("remainder");
                fragmentTransaction.commit();
                m.setTitle("  Remainder");
                m.setIcon(R.drawable.remainder_icon);
                m.setDisplayShowHomeEnabled(true);
                m.setDisplayHomeAsUpEnabled(true);
                m.setBackgroundDrawable(new ColorDrawable(Color.rgb(102, 154, 204)));
                m.show();

            }
        });
        maintains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new Maintain_Fragment(), "maintains");
                fragmentTransaction.addToBackStack("maintains");
                fragmentTransaction.commit();
                m.setTitle("  Maintains");
                m.setIcon(R.drawable.maintaince_icon);
                m.setDisplayShowHomeEnabled(true);
                m.setDisplayHomeAsUpEnabled(true);
                m.setBackgroundDrawable(new ColorDrawable(Color.rgb(102, 154, 204)));
                m.show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onStart() {
        super.onStart();
        //handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("User", user); //puting the saved instance state
    }

    public void Check_Version(String Version) {
        SharedPreferences sharedPref = getDefaultSharedPreferences(getActivity());
        if (!Version.equalsIgnoreCase(sharedPref.getString(getString(R.string.pref_version_key),
                getString(R.string.pref_version_default))))
            alertDialogBuilder.show();
    }

    public void update_PerArray() {
        Per_Arr = new Person_Array(getActivity(), Progress);
        Thread m = new Thread(Per_Arr);
        m.start();
    }

    @Override
    public void callBackFunc(int val) {
        if (val == 0) {
            Log.e("AnaFeCall", "CallBack" + val);
            String s = saved_values.getString(getString(R.string.pref_version_key), getString(R.string.pref_version_default));
            if (!saved_values.getString(getString(R.string.pref_version_key), getString(R.string.pref_version_default)).equalsIgnoreCase(data.getData_Version())) {
                getdata();
                /*alertDialogBuilder.setTitle("Announcement");
                alertDialogBuilder.setMessage("A new version is avaliable\nDownload it??");
                alertDialogBuilder.setIcon(R.drawable.warning);
                alertDialogBuilder.setPositiveButton("Downlaod", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();*/
            }
        } else {
            editor.putString(getString(R.string.pref_version_key), data.getData_Version());
            editor.commit();
            Per_Arr = new Person_Array(getActivity(), Progress);
            Thread mm = new Thread(Per_Arr);
            mm.start();
            Toast.makeText(getContext(), "Done Reterived data", Toast.LENGTH_LONG).show();
        }

    }

    void getdata() {
        Toast.makeText(getContext(), "Gettting Data", Toast.LENGTH_LONG).show();
        Progress.show();
        data.trunclate();
        for (int i = 0; i < 7; i++) {
            data.setOperation_Num(i);
            data.Get_Data();
        }
    }
}
