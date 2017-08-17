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
 * Created by 7oda on 7/29/2017.
 */

public class Remainder_Adapter extends History_Adapter { // 7tet el date !!!!!

    public Remainder_Adapter(Activity context, List<Converge_Data_Class> Data_List) {
        super(context, Data_List);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Converge_Data_Class Current_Row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.remainder_table, parent, false);
        }
        if (Current_Row != null) {

            ((TextView) convertView.findViewById(R.id.row_num_remain)).setText(Current_Row.getNum());
            ((TextView) convertView.findViewById(R.id.row_code_remain)).setText(Current_Row.getCode());
            ((TextView) convertView.findViewById(R.id.row_name_remain)).setText(Current_Row.getName());
            ((TextView) convertView.findViewById(R.id.row_address_remain)).setText(Current_Row.getAddress());
            ((TextView) convertView.findViewById(R.id.row_price_remain)).setText(Current_Row.getRemain()+"");
            ((TextView) convertView.findViewById(R.id.row_date_remain)).setText(Current_Row.getDate());
            ((TextView) convertView.findViewById(R.id.row_metting_remain)).setText(Current_Row.getDate());
            ((TextView) convertView.findViewById(R.id.row_note_remain)).setText(Current_Row.getNote());
            ((CheckBox) convertView.findViewById(R.id.row_stat_remain)).setChecked(Current_Row.isStatus());
        }
        return convertView;
    }
}