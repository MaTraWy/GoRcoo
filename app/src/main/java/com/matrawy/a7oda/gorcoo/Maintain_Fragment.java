package com.matrawy.a7oda.gorcoo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
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
import com.matrawy.a7oda.gorcoo.adapters.Maintaince_Adapter;

import java.util.ArrayList;

/**
 * Created by 7oda on 3/23/2017.
 */

public class Maintain_Fragment extends Fragment { //fails in checks
    private View Root;
    //private SearchView search;
    private ListView list;
    private DB db;
    private SQLiteDatabase db_heleper;
    private Maintaince_Adapter adapter;
    private AlertDialog.Builder alertDialogBuilder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Root = inflater.inflate(R.layout.fragment_maintains, container, false);
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
                        if (db.update_Maintaine_Status(db_heleper, m.getNum())) {
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
                                if (db.update_Maintaine_Note(db_heleper, m.getNum(),input.getText().toString())) {
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

    private void init() {
        list = (ListView) Root.findViewById(R.id.row_list_main);
        db = new DB(getActivity());
        db_heleper = db.getReadableDatabase();
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("announcement");
        alertDialogBuilder.setIcon(R.drawable.agrement);
    }

    private void filling_table() {
        ArrayList<Converge_Data_Class> test = new ArrayList<>();
        Cursor M = db.Get_Maintainace(db_heleper);
        if (M == null)
            return;
        while (M.moveToNext()) {
            if(!M.getString(3).equalsIgnoreCase("1")) {
                Converge_Data_Class dude = new Converge_Data_Class();
                dude.setNum(M.getString(0));
                dude.setCode(M.getString(6));
                dude.setName(M.getString(8));
                Log.e("Name", M.getString(8));
                //dude.setName("Mahmoud Matrawy");
                dude.setAddress(M.getString(16)); //Log.e("Addd",M.getString(18));
                dude.setDate(M.getString(2));
                dude.setStatus(M.getString(3).equals("1"));
                dude.setNote(M.getString(4));
                test.add(dude);
            }
        }
        adapter = new Maintaince_Adapter(getActivity(), test);

    }
}
