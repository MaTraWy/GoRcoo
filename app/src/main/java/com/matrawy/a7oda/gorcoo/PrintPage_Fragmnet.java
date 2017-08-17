package com.matrawy.a7oda.gorcoo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.matrawy.a7oda.gorcoo.Data.DB;
import com.matrawy.a7oda.gorcoo.bluetooth_Module.Bluetooth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.layout.simple_list_item_1;

/**
 * Created by 7oda on 3/23/2017.
 */

public class PrintPage_Fragmnet extends Fragment {
    private View Root;
    private TextView print_address;
    private TextView print_name;
    private TextView print_num;
    private TextView print_p1;
    private EditText print_p2;
    private TextView print_speed;
    private EditText print_problem;
    private CheckBox print_problem_check;
    private EditText print_remainder;
    private DatePickerDialog datedialog;
    private TimePickerDialog timedialog;
    private CheckBox print_remainder_check;
    private TextView print_date;
    Person_Customer Customer_data;
    private Person user;
    private TextView print_time;
    private AlertDialog alertDialog;
    private ArrayAdapter<Person_Customer> Person_Array_Adapter;
    private ListView Address_ListView;
    private SearchView searchView;
    private ArrayList<Person_Customer> Cust_arr;
    private ArrayList<String> data_bundle;
    private ImageView print;
    AlertDialog.Builder dialogBuilder;
    ListView Bluetooth_ListView;
    Bluetooth m;
    ArrayAdapter Blu_Adapter;
    BluetoothDevice Blu_Device;
    DB db;
    SQLiteDatabase db_heleper;

    //add print module & Search by Add
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Root = inflater.inflate(R.layout.fragment_printpage, container, false);
        if (savedInstanceState == null) {
            Cust_arr = (ArrayList<Person_Customer>) getArguments().getSerializable("Cust_arr");
            user = (Person) getArguments().getSerializable("User");
            data_bundle = new ArrayList<>();
        } else {
            Cust_arr = (ArrayList<Person_Customer>) savedInstanceState.getSerializable("Cust_arr");
            user = (Person) savedInstanceState.getSerializable("User");
            data_bundle = savedInstanceState.getStringArrayList("ArrayList");
            if (data_bundle != null) {
                print_address.setText(data_bundle.get(0));
                print_name.setText(data_bundle.get(1));
                print_num.setText(data_bundle.get(2));
                print_p1.setText(data_bundle.get(3));
                print_speed.setText(data_bundle.get(4));
            }
        }
        init(Root);
        print_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Person_Array_Adapter.getFilter().filter(newText);

                return false;
            }
        });
        Address_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer_data = Person_Array_Adapter.getItem((position));
                if (Customer_data == null)
                    return;
                print_address.setText(Customer_data.getAddress());
                print_name.setText(Customer_data.getName());
                print_num.setText(String.valueOf(Customer_data.getId()));
                print_p1.setText(Customer_data.getRemain());
                print_p2.setText("");
                print_speed.setText(Customer_data.getSpeed());

                data_bundle.clear();
                data_bundle.add(Customer_data.getAddress());
                data_bundle.add(Customer_data.getName());
                data_bundle.add(String.valueOf(Customer_data.getId()));
                data_bundle.add(Customer_data.getRemain());
                data_bundle.add((String) Customer_data.getSpeed());
                alertDialog.dismiss();
            }
        });

        return Root;
    }

    @SuppressLint("InflateParams")
    private void init(View Root) {
        print_address = (TextView) Root.findViewById(R.id.print_address);
        print_name = (TextView) Root.findViewById(R.id.print_name);
        print_num = (TextView) Root.findViewById(R.id.print_num);
        print_p1 = (TextView) Root.findViewById(R.id.print_p1);
        print_p2 = (EditText) Root.findViewById(R.id.print_p2);
        print_p2.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Chonburi-Regular.ttf"));
        print_speed = (TextView) Root.findViewById(R.id.print_speed);
        print_problem_check = (CheckBox) Root.findViewById(R.id.print_check1);
        print_problem = (EditText) Root.findViewById(R.id.print_problem);
        print_problem.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Chonburi-Regular.ttf"));
        print_remainder_check = (CheckBox) Root.findViewById(R.id.print_check2);
        print_remainder = (EditText) Root.findViewById(R.id.print_remainder);
        print_remainder.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Chonburi-Regular.ttf"));
        print_date = (TextView) Root.findViewById(R.id.print_date);
        print_time = (TextView) Root.findViewById(R.id.print_time);
        print = (ImageView) Root.findViewById(R.id.print_print);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBase_Module();
            }
        });

        // Date Area ---------------------*/
        int year, month, dayOfMonth;
        int H, M;
        Date d = new Date();
        print_date.setText(" " + DateFormat.format("yyyy-MM-dd", d.getTime()));
        print_time.setText(" " + DateFormat.format("hh:mm", d.getTime()));
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        H = Calendar.getInstance().get(Calendar.HOUR);
        M = Calendar.getInstance().get(Calendar.MINUTE);
        datedialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                print_date.setText(new StringBuilder("").append(year).append("-").append(month).append("-").append(dayOfMonth));
            }
        }, year, month, dayOfMonth);
        timedialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                print_time.setText(new StringBuilder("").append(hourOfDay).append(":").append(minute));
            }
        }, H, M, false);
        print_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datedialog.show();
            }
        });
        print_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timedialog.show();
            }
        });
        print_problem_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    if (print_remainder_check.isChecked())
                        print_remainder_check.setChecked(false);
            }
        });
        print_remainder_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    if (print_problem_check.isChecked())
                        print_problem_check.setChecked(false);
            }
        });
        // ---------------------------- */
        db = new DB(getActivity());
        db_heleper = db.getWritableDatabase();

        /* List View Area */
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View dialogView;
        dialogView = getActivity().getLayoutInflater().inflate(R.layout.searchable_list_dialog, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        Address_ListView = (ListView) dialogView.findViewById(R.id.listItems);
        searchView = (SearchView) dialogView.findViewById(R.id.search1);
        Person_Array_Adapter = new ArrayAdapter<Person_Customer>(getActivity(), simple_list_item_1, Cust_arr) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Person_Customer Current_Row = getItem(position);
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(simple_list_item_1, parent, false);
                }

                ((TextView) convertView.findViewById(android.R.id.text1)).setText(Current_Row.getAddress());
                return convertView;
            }
        };
        Address_ListView.setAdapter(Person_Array_Adapter);
        //------------------------------------------*/

        //  Bluetooth Area --------------------*/

        m = new Bluetooth(getActivity());
        ArrayList<BluetoothDevice> arr = new ArrayList<BluetoothDevice>(m.findBT());
        AlertDialog.Builder Bluetooth_Dialog = new AlertDialog.Builder(getActivity());
        View BluetoothView = getActivity().getLayoutInflater().inflate(R.layout.blutooth, null);
        Bluetooth_ListView = (ListView) BluetoothView.findViewById(R.id.blutooth);
        Blu_Adapter = new ArrayAdapter<BluetoothDevice>(getActivity(), simple_list_item_1
                , android.R.id.text1, arr) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                BluetoothDevice Current_Row = getItem(position);
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(simple_list_item_1, parent, false);
                }

                ((TextView) convertView.findViewById(android.R.id.text1)).setText(Current_Row.getName());
                return convertView;
            }
        };
        Bluetooth_ListView.setAdapter(Blu_Adapter);
        Bluetooth_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Blu_Device = (BluetoothDevice) Blu_Adapter.getItem(position);
                print();
            }
        });
        Bluetooth_Dialog.setView(BluetoothView);
        Bluetooth_Dialog.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Bluetooth_Dialog.show();
    }

    private void DataBase_Module() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        if (print_name.getText().length() != 0) {
            if (print_problem_check.isChecked()) {
                if (db.Insert_Maintain(db_heleper, String.valueOf(Customer_data.getId()), print_date.getText().toString() + " " + print_time.getText().toString(), print_problem.getText().toString())) {
                    Toast.makeText(getContext(), "Maintaince Insertion done", Toast.LENGTH_LONG).show();
                }
            } else if (print_remainder_check.isChecked()) {
                if (db.Insert_Remainder(db_heleper, String.valueOf(Customer_data.getId()), print_date.getText().toString() + " " + print_time.getText().toString(), print_problem.getText().toString())) {
                    Toast.makeText(getContext(), "Remainder Insertion done", Toast.LENGTH_LONG).show();
                }
            } else if (!print_p2.getText().toString().equalsIgnoreCase("") && !print_p1.getText().toString().equalsIgnoreCase("")) {
                String status_string = db.Insert_History(db_heleper, String.valueOf(user.getId()), Customer_data, Double.parseDouble(print_p2.getText().toString()));
                if (status_string.equalsIgnoreCase("Done")) {
                    Toast.makeText(getContext(), "transaction done", Toast.LENGTH_LONG).show();
                } else {
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage(status_string);
                    alertDialog.show();
                }
            } else {
                alertDialog.setTitle("Missing data");
                alertDialog.setMessage("fill the variable price area");
                alertDialog.show();
            }
        } else {
            alertDialog.setTitle("Missing data");
            alertDialog.setMessage("Choose by clicking on Address field");
            alertDialog.show();
        }
    }

    public void print() {
        m.setMmDevice(Blu_Device);
        try {
            ProgressDialog progress = new ProgressDialog(getActivity());
            progress.setTitle("Connecting...");
            progress.setCancelable(false);
            progress.setMessage("Connecting to Device:\n"+Blu_Device.getName());
            progress.show();
            m.openBT(progress, new CallBack() {
                @Override
                public void callBackFunc(int val) {
                    if(val==0)
                    {
                        String instDate = DateFormat.format("yyyy-MM-dd hh-mm-ss", new Date().getTime()).toString();
                        StringBuilder Data=new StringBuilder("");
                        Data.append("Name:").append(Customer_data.getName()).append("\n");
                        Data.append("Code:").append(Customer_data.getCode()).append("\n");
                        Data.append("Payment:").append(print_p1.getText().toString()).append("\n");
                        Data.append("Remain:").append(print_p2.getText().toString()).append("\n");
                        Data.append("Date:").append(instDate).append("\n");
                        Data.append("Casher Name:").append(user.getName()).append("\n");
                        Data.append("Contact Us:").append(" ").append("\n");
                        Data.append("\t\t").append("Comment").append("\n");
                        Data.append("\t\t").append("We are happy to provide \nthe service to you").append("\n");
                        Data.append("\t\t\t\t").append("Powered by gorcoo\n");
                        Toast.makeText(getContext(),"Start to send data",Toast.LENGTH_LONG);
                        m.sendData(Data.toString());
                        m.closeBT();
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("User", user); //User
        savedInstanceState.putSerializable("Cust_arr", Cust_arr);//Person Array
        savedInstanceState.putStringArrayList("ArrayList", data_bundle); //data_bundle
    }
}