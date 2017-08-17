package com.matrawy.a7oda.gorcoo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.R;

import java.util.List;

/**
 * Created by 7oda on 7/31/2017.
 */

public class Option_Adapter extends History_Adapter { // 7tet el date !!!!!

    public Option_Adapter(Activity context, List<Converge_Data_Class> Data_List) {
        super(context, Data_List);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Converge_Data_Class Current_Row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.option_table, parent, false);
        }
        if (Current_Row != null) {

            ((TextView) convertView.findViewById(R.id.Option_No)).setText(Current_Row.getNum());
            ((TextView) convertView.findViewById(R.id.Option_Query)).setText(Current_Row.getQuery());
            if (Current_Row.isStatus()) {
                ((CheckBox) convertView.findViewById(R.id.Option_Done)).setChecked(true);
            } else {
                ((CheckBox) convertView.findViewById(R.id.Option_Done)).setChecked(false);
            }
        }
        return convertView;
    }
}