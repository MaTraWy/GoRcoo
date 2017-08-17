package com.matrawy.a7oda.gorcoo.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.R;

import java.util.List;

/**
 * Created by 7oda on 7/28/2017.
 */

public class Maintaince_Adapter extends History_Adapter {
    public Maintaince_Adapter(Activity context, List<Converge_Data_Class> Data_List) {
        super(context, Data_List);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Converge_Data_Class Current_Row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.maintain_table, parent, false);
        }
        if (Current_Row != null) {

            ((TextView) convertView.findViewById(R.id.row_num_main)).setText(Current_Row.getNum());
            ((TextView) convertView.findViewById(R.id.row_code_main)).setText(Current_Row.getCode());
            ((TextView) convertView.findViewById(R.id.row_name_main)).setText(Current_Row.getName());
            ((TextView) convertView.findViewById(R.id.row_address_main)).setText(Current_Row.getAddress());
            ((TextView) convertView.findViewById(R.id.row_date_main)).setText(Current_Row.getDate());
            ((TextView) convertView.findViewById(R.id.row_note_main)).setText(Current_Row.getNote());
            ((CheckBox) convertView.findViewById(R.id.row_stat_main)).setChecked(Current_Row.isStatus());

        }
        return convertView;
    }
}
