package com.matrawy.a7oda.gorcoo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.Data.DB;

import java.util.ArrayList;

import static com.matrawy.a7oda.gorcoo.R.drawable.user;
import static com.matrawy.a7oda.gorcoo.R.id.total_day_cprice;
import static com.matrawy.a7oda.gorcoo.R.id.total_day_vprice;
import static com.matrawy.a7oda.gorcoo.R.id.total_month_cprice;
import static com.matrawy.a7oda.gorcoo.R.id.total_month_vprice;
import static com.matrawy.a7oda.gorcoo.R.id.total_spinner_1;
import static com.matrawy.a7oda.gorcoo.R.id.total_spinner_2;

/**
 * Created by 7oda on 3/15/2017.
 */

public class Log_Totalpage_Fragment extends Fragment {
    View root;
    TextView day_cprice;
    double cprice;
    TextView day_vprice;
    double vprice;
    TextView month_cprice;
    TextView month_vprice;
    ArrayList<String[]> Data;
    DB db;
    TextView number_day_view;
    TextView number_month_view;
    SQLiteDatabase db_heleper;
    AlertDialog.Builder lol;
    Person User;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_total, container, false);
        if(savedInstanceState!=null)
        {
            User = (Person) savedInstanceState.getSerializable("User");
        }
        else
        {
            User = (Person) getArguments().getSerializable("User");
        }
        init(root);
        get_Data();
        Listner();
        return root;
    }

    private void init(View root) {
        day_cprice = (TextView) root.findViewById(total_day_cprice);
        day_vprice = (TextView) root.findViewById(total_day_vprice);
        month_cprice = (TextView) root.findViewById(total_month_cprice);
        month_vprice = (TextView) root.findViewById(total_month_vprice);
        number_day_view = (TextView) root.findViewById(total_spinner_1);
        number_month_view = (TextView) root.findViewById(total_spinner_2);
        db = new DB(getActivity());
        db_heleper = db.getWritableDatabase();
        Data = new ArrayList<String[]>();
        lol = new AlertDialog.Builder(getActivity());
    }

    private void get_Data() {
        Cursor M = db.Get_History(db_heleper, String.valueOf(User.getId()));
        if (M == null)
            return;
        while (M.moveToNext()) {
            String[] m = new String[3];
            m[0] = M.getString(14);
            m[1] = M.getString(13);
            m[2] = M.getString(5);
            Data.add(m);
        }
    }

    public void Listner()
    {
        lol.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        number_day_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker number_day=new NumberPicker(getContext());
                number_day.setMinValue(1);
                number_day.setMaxValue(31);
                number_day.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        cprice = 0;
                        vprice = 0;
                        String Val = newVal<10? "0"+String.valueOf(newVal):String.valueOf(newVal);
                        number_day_view.setText(Val);
                        for (String[] m : Data) {
                            if (Val.equalsIgnoreCase(m[2].split("-")[2].split(" ")[0])) {
                                cprice += Double.parseDouble(m[0])+Double.parseDouble(m[1]);
                                vprice += Double.parseDouble(m[0]);
                            }
                        }
                        day_cprice.setText("" + cprice);
                        day_vprice.setText("" + vprice);
                    }
                });
                lol.setView(number_day);
                lol.show();
            }
        });
        number_month_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker number_Month = new NumberPicker(getContext());
                number_Month.setMaxValue(12);
                number_Month.setMinValue(1);
                number_Month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        cprice = 0;
                        vprice = 0;
                        String Val = newVal<10? "0"+String.valueOf(newVal):String.valueOf(newVal);
                        number_month_view.setText(Val);
                        for (String[] m : Data) {
                            Log.e("Split",(m[2].split("-")[1]));
                            if (Val.equalsIgnoreCase(m[2].split("-")[1])) {
                                cprice += Double.parseDouble(m[0]) +Double.parseDouble(m[1]);
                                vprice += Double.parseDouble(m[0]);
                            }
                        }
                        month_cprice.setText("" + cprice);
                        month_vprice.setText("" + vprice);
                    }
                });
                lol.setView(number_Month);
                lol.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("User", user); //User
    }
   /* public void test()
    {
        CheckBox m = new CheckBox(getActivity());
        boolean Bluetooth_ListView = new Random().nextBoolean();
        m.setChecked(Bluetooth_ListView);
        if (m.isSelected())
        {
            Log.e("Saad","100");
        }
        else
            Log.e("Matrawy","50");
    }*/
}
