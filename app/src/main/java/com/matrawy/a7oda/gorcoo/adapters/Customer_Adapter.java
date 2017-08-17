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

public class Customer_Adapter extends History_Adapter { // 7tet el date !!!!!
    protected boolean type;

    public Customer_Adapter(Activity context, List<Converge_Data_Class> Data_List) {
        super(context, Data_List);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Converge_Data_Class Current_Row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customer_table, parent, false);
        }
        if (Current_Row != null) {

            ((TextView) convertView.findViewById(R.id.row_num_cust)).setText(Current_Row.getNum());
            ((TextView) convertView.findViewById(R.id.row_customer_cust)).setText(Current_Row.getCustomer());
            ((TextView) convertView.findViewById(R.id.row_item_cust)).setText(Current_Row.getItem());
            ((TextView) convertView.findViewById(R.id.row_price_cust)).setText(Current_Row.getPayment()+"");
            ((TextView) convertView.findViewById(R.id.row_user_cust)).setText(Current_Row.getUser());
            ((TextView) convertView.findViewById(R.id.row_date_cust)).setText(Current_Row.getDate());
        }
        return convertView;
    }
}
