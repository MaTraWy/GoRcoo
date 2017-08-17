package com.matrawy.a7oda.gorcoo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.matrawy.a7oda.gorcoo.Data.DB;
import com.matrawy.a7oda.gorcoo.Data.Data_Parsing;
import com.matrawy.a7oda.gorcoo.adapters.Converge_Data_Class;
import com.matrawy.a7oda.gorcoo.adapters.Option_Adapter;

import java.util.ArrayList;

/**
 * Created by 7oda on 7/31/2017.
 */

public class Option_Fragment extends Fragment{
    View Root;
    ProgressDialog Progress;
    Person user;
    ListView list;
    EditText Server;
    EditText Port;
    TextView version;
    Home_Fragment home;
    EditText Username;
    private AlertDialog.Builder alertDialogBuilder;
    EditText Password;
    AlertDialog alertDialog;
    Button But_Ret;
    Button But_Upd;
    Button But_Upd_Upd;
    Button Save_change;
    Button Log_out;
    CheckBox Status;
    ArrayList<Converge_Data_Class> Cache_List;
    private DB db;
    private SQLiteDatabase db_heleper;
    private Option_Adapter adapter;
    private Data_Parsing data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Root = inflater.inflate(R.layout.fragment_option, container, false);
        init();
        filling_table();
        list.setAdapter(adapter);
        listner();
        return Root;
    }

    void init() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.option_dialouge, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        list = (ListView) dialogView.findViewById(R.id.list_option);
        But_Upd_Upd = (Button) dialogView.findViewById(R.id.option_server_upd);
        Server = (EditText) Root.findViewById(R.id.Option_Server);
        Server.setText(Internet.URLL);
        Port = (EditText) Root.findViewById(R.id.Option_Port);
        Port.setText("80");
        Save_change = (Button) Root.findViewById(R.id.option_savechange);
        Username = (EditText) Root.findViewById(R.id.Option_Username);
        Username.setText(user.getUsername());
        Password = (EditText) Root.findViewById(R.id.Option_Password);
        Password.setText(user.getPassword());
        version = (TextView) Root.findViewById(R.id.Option_Version);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        version.setText(sharedPref.getString(getString(R.string.pref_version_key),getString(R.string.pref_version_default)));
        Status = (CheckBox) Root.findViewById(R.id.Option_ServerStatus);
        Status.setChecked(Internet.Cloud_flag);
//        Internet.isOnline();
       // Internet.log =this;
        Status.setClickable(false);
        But_Ret = (Button) Root.findViewById(R.id.Option_retrieve);
        But_Upd = (Button) Root.findViewById(R.id.Option_Update);
        Log_out = (Button) Root.findViewById(R.id.option_logout) ;
        Progress = new ProgressDialog(getContext());
        alertDialogBuilder = new AlertDialog.Builder(getContext());
        db = new DB(getActivity());
        db_heleper = db.getReadableDatabase();
    }

    void filling_table() {
        Cache_List = new ArrayList<>();
        Cursor M = db.Get_Cache(db_heleper);
        if (M == null) {
            Log.e("Cache", "Nulls");
            return;
        }
        while (M.moveToNext()) {
            Converge_Data_Class dude = new Converge_Data_Class();
            dude.setNum(M.getString(0));
            dude.setQuery(M.getString(3));
            dude.setStatus(Integer.parseInt(M.getString(4)) == 1);
            Cache_List.add(dude);
        }
        adapter = new Option_Adapter(getActivity(), Cache_List);
    }

    void listner() {
        But_Upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
        But_Ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Progress.setMessage("Retreving Data ...");
                Progress.setTitle("Wait");
                Progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                Progress.setCancelable(false);
                final Data_Parsing data = new Data_Parsing(getActivity(), Progress);
                if (Internet.Cloud_flag) {
                    Progress.show();
                    data.trunclate();
                    for (int i = 0; i < 7; i++) {
                        data.setOperation_Num(i);
                        data.Get_Data();
                    }
                }
            }
        });
        But_Upd_Upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Internet.isOnline();
                if (!Internet.Cloud_flag || !Internet.Internet_flag) {
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("There is no internet, or the cloud is down\nplease check it");
                    alertDialog.setIcon(R.drawable.warning);
                    alertDialog.show();
                    return;
                }
                data.getProgress().show();
                data.Operation_Num = 10;
                for (Converge_Data_Class dude : Cache_List) {
                    if (!dude.isStatus()) {
                        data.Insert_Data(dude.getQuery());
                        Log.e("query", dude.getQuery());
                        dude.setStatus(true);
                        if (!db.Update_Chace(dude.getNum(), db_heleper)) {
                            Toast.makeText(getContext(), "There is an error", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }
                data.Get_Data();
                home.update_PerArray();
                Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();

            }
        });
        Save_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Internet.URLL=Server.getText().toString();
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.pref_url_key), Server.getText().toString());
                editor.commit();
                Toast.makeText(getContext(), "Done updated", Toast.LENGTH_LONG).show();
            }
        });
        Log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().remove(getString(R.string.pref_username_key)).commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().remove(getString(R.string.pref_password_key)).commit();
                Intent in = new Intent(getActivity(), Login_Activity.class);
                startActivity(in);
                getActivity().finish();
            }
        });
    }


    public void setUser(Person user) {
        this.user = user;
    }

    public void setData(Data_Parsing data) {
        this.data = data;
    }

    public void setHome(Home_Fragment home) {
        this.home = home;
    }
}