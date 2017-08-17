package com.matrawy.a7oda.gorcoo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.matrawy.a7oda.gorcoo.Data.DB;
import com.matrawy.a7oda.gorcoo.Data.Data_Parsing;

import static com.matrawy.a7oda.gorcoo.R.id.BigLogo;
import static com.matrawy.a7oda.gorcoo.R.id.Login_Button;
import static com.matrawy.a7oda.gorcoo.R.id.Login_Changeurl;
import static com.matrawy.a7oda.gorcoo.R.id.Login_Password;
import static com.matrawy.a7oda.gorcoo.R.id.Login_Username;
import static com.matrawy.a7oda.gorcoo.R.id.Login_rember;
import static com.matrawy.a7oda.gorcoo.R.id.test;

/**
 * Created by 7oda on 3/7/2017
 * The Main Login Page
 */

public class Login_Fragment extends Fragment implements CallBack {
    private View Root;
    DB db;
    SQLiteDatabase db_heleper;
    AlertDialog.Builder alertDialogBuilder;
    private LinearLayout logo;
    private ImageView logo2;
    private EditText username;
    private EditText password;
    private CheckBox check;
    private SharedPreferences  sharedPref;
    private SharedPreferences.Editor edit;
    Typeface newfont;
    Data_Parsing data;
    ProgressDialog Progress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB(getContext());
        db_heleper = db.getWritableDatabase();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(sharedPref.contains(getString(R.string.pref_username_key)))
        {
            Cursor Cu = db.Login(db_heleper, sharedPref.getString(getString(R.string.pref_username_key),getString(R.string.pref_username_default))
                    ,sharedPref.getString(getString(R.string.pref_password_key),getString(R.string.pref_password_default)));
            Intent in = new Intent(getActivity(), Home_Activity.class);
            Person user = GetUser_Data(Cu);
            in.putExtra("User", user);
            startActivity(in);
            Internet.clear();
            getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Root = inflater.inflate(R.layout.fragment_login, container, false);
        init();
        if (sharedPref.contains(getString(R.string.pref_version_key)) == true)
            Log.e("Mqwgoda", "aywa");
        listenr();
        softkey(container);
        // Database_check();
        Internet.isOnline();
        return Root;
    }

    private void init() {
        username = (EditText) Root.findViewById(Login_Username);
        logo = (LinearLayout) Root.findViewById(test);
        logo2 = (ImageView) Root.findViewById(BigLogo);
        password = (EditText) Root.findViewById(Login_Password);
        check = (CheckBox) Root.findViewById(Login_rember);
        newfont = Typeface.createFromAsset(getResources().getAssets(), "segoe.ttf");
        ((Button) Root.findViewById(Login_Button)).setTypeface(newfont);
        username.setTypeface(newfont);
        password.setTypeface(newfont);
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
      /*  db = new DB(getActivity());
        db_heleper = db.getReadableDatabase();*/
      //  sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = sharedPref.edit();
        Internet.URLL = sharedPref.getString(getString(R.string.pref_url_key), getString(R.string.pref_url_default));
        Internet.context = getContext();
        Internet.progress = new ProgressDialog(getActivity());
        Internet.log = this;
        Progress = new ProgressDialog(getActivity());
        Progress.setMessage("Retreving Data ...");
        Progress.setTitle("Wait");
        Progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        Progress.setCancelable(false);
        data = new Data_Parsing(getActivity(), Progress);
    }

    public void Login() {
        View view = getActivity().getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(view != null ? view.getWindowToken() : null, 0);

        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder.setNegativeButton("", null);
        alertDialogBuilder.setPositiveButton("Oky", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        if (username.getText().length() == 0 || password.getText().length() == 0) {
            alertDialogBuilder.setMessage("Please fill the free area");
            alertDialogBuilder.show();
            return;
        }
        Cursor Cu = db.Login(db_heleper, username.getText().toString(), password.getText().toString());
        if (Cu == null) {
            alertDialogBuilder.setMessage("Username or password are wrong");
            alertDialogBuilder.show();
            return;
        }
        Person user = GetUser_Data(Cu);
        if(check.isChecked())
        {
            Log.e("Done","PutPreferance");
            edit.putString(getString(R.string.pref_username_key), user.getUsername());
            edit.putString(getString(R.string.pref_password_key), user.getPassword());
            edit.apply();
        }
        Intent in = new Intent(getActivity(), Home_Activity.class);
        in.putExtra("User", user);
        startActivity(in);
        getActivity().finish();
        Internet.clear();
    }

    private Person GetUser_Data(Cursor M) {
        if (!M.moveToFirst())
            return null;
        Person user = new Person();
        user.setId(Integer.parseInt(M.getString(0)));
        user.setName(M.getString(1));
        user.setUsername(M.getString(2));
        user.setPassword(M.getString(3));
        M.close();
        return user;
    }

    @Override
    public void onStart() {
        super.onStart();
        db = new DB(getContext());
        db_heleper = db.getWritableDatabase();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        db_heleper.close();
    }

    public void Database_check() {
        if (Internet.Internet_flag && Internet.Cloud_flag) {
            if (db.Is_Empty(db_heleper)) {
                getdata();
            } else {
                Log.e("VersionTest", "Version");
                data.setCall(this);
                data.setOperation_Num(11);
                data.Get_Data();//wait for call back
            }
        } else {
            if (db.Is_Empty(db_heleper)) {
                alertDialogBuilder.setTitle("The database is empty and there is no internet connection Or with Cloud");
                alertDialogBuilder.setMessage("Please have Internet connection or check the cloud as soon as possible in order" +
                        "to use the program & retrieve data from cloud.");
                alertDialogBuilder.setIcon(R.drawable.warning);
                alertDialogBuilder.show();
                alertDialogBuilder.setNegativeButton("", null);
                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            } else {
                Log.e("Hiii", "HeyThere");
            }
        }
    }

    public void callBackFunc(int val) {
        if (val == 0) {
            if (!sharedPref.getString(getString(R.string.pref_version_key), getString(R.string.pref_version_default)).equalsIgnoreCase(data.getData_Version())) {
                alertDialogBuilder.setTitle("Announcement");
                alertDialogBuilder.setMessage("A new version is avaliable\nDownload it??");
                alertDialogBuilder.setIcon(R.drawable.warning);
                alertDialogBuilder.setPositiveButton("Downlaod", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getdata();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        }
        else
        {
            edit.putString(getString(R.string.pref_version_key), data.getData_Version());
            edit.commit();
            Toast.makeText(getContext(), "Done Reterived data", Toast.LENGTH_LONG).show();
        }
    }

    private void getdata() {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(getContext(), "Gettting Data", Toast.LENGTH_LONG).show();
               Progress.show();
           }
       });
        data.trunclate();
        data.setCount(0);
        for (int i = 0; i < 7; i++) {
            data.setOperation_Num(i);
            data.Get_Data();
        }
    }

    private void softkey(ViewGroup container) {
        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                Root.getWindowVisibleDisplayFrame(r);
                int screenHeight = Root.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                //Log.d(TAG, "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    logo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.05));
                    logo2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, (float) 0.4));
                } else {
                    logo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1.5));
                    logo2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, (float) 1.5));

                }
            }
        });
    }

    private void listenr() {
        (Root.findViewById(Login_Button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (db.Is_Empty(db_heleper) && !Internet.Internet_flag) {
                            alertDialogBuilder.setTitle("Database is empty, and no internet connection or cloud down");
                            alertDialogBuilder.setMessage("please come back when there is and internet connection or check cloud");
                            alertDialogBuilder.setNegativeButton("", null);
                            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialogBuilder.show();
                        } else if (db.Is_Empty(db_heleper) && Internet.Internet_flag && Internet.Cloud_flag) {
                            alertDialogBuilder.setTitle("Database is empty");
                            alertDialogBuilder.setMessage("Please get data from cloud, By clicking ok");
                            alertDialogBuilder.show();
                            alertDialogBuilder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getdata();
                                }
                            });
                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        } else
                            Login();
                    }
                }
        );
        (Root.findViewById(Login_Changeurl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("URL");
                dialogBuilder.setMessage("Please enter new URL");
                final EditText input = new EditText(getContext());
                final Button default1 = new Button(getContext());
                default1.setText("Default URL");
                default1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        input.setText(getString(R.string.pref_url_default));
                    }
                });
                LinearLayout linear = new LinearLayout(getActivity());
                linear.setOrientation(LinearLayout.VERTICAL);
                linear.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                linear.addView(input);
                linear.addView(default1);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                dialogBuilder.setView(linear);
                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Internet.URLL = input.getText().toString();
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.pref_url_key), input.getText().toString());
                        editor.apply();
                        Database_check();
                    }
                });
                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        db.close();
        db_heleper.close();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}


  /*Data_Parsing data = new Data_Parsing(getActivity(),new ProgressDialog(getActivity()));

            for(int i=0;i<7;i++)
            {
                data.setOperation_Num(i);
                data.Get_Data();
            }*/
