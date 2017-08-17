package com.matrawy.a7oda.gorcoo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.R;

import java.util.List;

/**
 * Created by 7oda on 7/29/2017.
 */

public class User_Adapter extends History_Adapter { // 7tet el date !!!!!
    protected boolean type;

    public User_Adapter(Activity context, List<Converge_Data_Class> Data_List) {
        super(context, Data_List);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Converge_Data_Class Current_Row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_table, parent, false);
        }
        if (Current_Row != null) {

            ((TextView) convertView.findViewById(R.id.row_num_user)).setText(Current_Row.getNum());
            ((TextView) convertView.findViewById(R.id.row_user_user)).setText(Current_Row.getUser());
            ((TextView) convertView.findViewById(R.id.row_item_user)).setText(Current_Row.getItem());
            ((TextView) convertView.findViewById(R.id.row_price_user)).setText(Current_Row.getPayment()+"");
            ((TextView) convertView.findViewById(R.id.row_date_user)).setText(Current_Row.getDate());
        }
        return convertView;
    }
}
