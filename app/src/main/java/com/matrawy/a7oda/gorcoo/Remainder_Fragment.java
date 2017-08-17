package com.matrawy.a7oda.gorcoo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.matrawy.a7oda.gorcoo.Data.DB;
import com.matrawy.a7oda.gorcoo.adapters.Converge_Data_Class;
import com.matrawy.a7oda.gorcoo.adapters.Remainder_Adapter;

import java.util.ArrayList;

/**
 * Created by 7oda on 3/23/2017.
 */

public class Remainder_Fragment extends Fragment {
    View Root;
    ListView list;
    private DB db;
    private SQLiteDatabase db_heleper;
    private Remainder_Adapter adapter;
    private AlertDialog.Builder alertDialogBuilder;
    //private SearchView search;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Root= inflater.inflate(R.layout.fragment_remainder,container,false);
        init();
        filling_table();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Converge_Data_Class m = adapter.getItem(position);
                alertDialogBuilder.setMessage("Option for " +
                        "user " + m.getNum() + "\n choose from adding note & marking status");
                alertDialogBuilder.setPositiveButton("Status", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (db.update_Remainder_Status(db_heleper, m.getNum())) {
                            m.setStatus(true);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Done updated status", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Error occurred, transaction failed", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.setNegativeButton("Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        dialogBuilder.setTitle("Note");
                        dialogBuilder.setMessage("Please enter The note");
                        final EditText input = new EditText(getContext());
                        dialogBuilder.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (db.update_Remainder_Note(db_heleper, m.getNum(),input.getText().toString())) {
                                    m.setNote(input.getText().toString());
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), "Done insert note", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "Error occurred, transaction failed", Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();
                            }
                        });
                        dialogBuilder.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        LinearLayout linear = new LinearLayout(getActivity());
                        linear.setOrientation(LinearLayout.VERTICAL);
                        linear.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));

                        linear.addView(input);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        dialogBuilder.setView(linear);
                        dialogBuilder.show();
                    }
                });
                alertDialogBuilder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });
        return Root;
    }
    public void init()
    {
        list = (ListView) Root.findViewById(R.id.list_remain);
        db = new DB(getActivity());
        db_heleper = db.getReadableDatabase();
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("announcement");
        alertDialogBuilder.setIcon(R.drawable.agrement);
    }
    private void filling_table() {
        ArrayList<Converge_Data_Class> test = new ArrayList<>();
        Cursor M = db.Get_Remainder(db_heleper);
        if (M == null)
            return;
        while (M.moveToNext()) {
            if(!M.getString(3).equalsIgnoreCase("1")) {
                Converge_Data_Class dude = new Converge_Data_Class();
                dude.setNum(M.getString(0));
                dude.setCode(M.getString(6));
                dude.setName(M.getString(7));
                dude.setNote(M.getString(4));
                dude.setStatus(M.getString(3).equals("1"));
                dude.setAddress(M.getString(16));
                dude.setRemain(Integer.parseInt(M.getString(11)));
                dude.setDate(db.Get_Transaction_Date(db_heleper, M.getString(5)));
                dude.setMetting_time(M.getString(2));
                test.add(dude);
            }
        }
        adapter = new Remainder_Adapter(getActivity(),test);
        M.close();
    }
}
