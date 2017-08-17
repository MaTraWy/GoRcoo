package com.matrawy.a7oda.gorcoo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.R;

import java.util.List;

/**
 * Created by 7oda on 7/21/2017.
 */

public class Non_Payment_Adapter extends History_Adapter {
    protected boolean type;

    public Non_Payment_Adapter(Activity context, List<Converge_Data_Class> Data_List) {
        super(context, Data_List);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Converge_Data_Class Current_Row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.nonpayment_table, parent, false);
        }
        if (Current_Row != null) {

            ((TextView) convertView.findViewById(R.id.row_num_nonpay)).setText(Current_Row.getNum());
            ((TextView) convertView.findViewById(R.id.row_code_nonpay)).setText(Current_Row.getCode());
            ((TextView) convertView.findViewById(R.id.row_name_nonpay)).setText(Current_Row.getName());
            //Log.e("Adapter", "Before -->" + ((TextView) convertView.findViewById(R.id.row_address)).getText().toString());
            ((TextView) convertView.findViewById(R.id.row_address_nonpay)).setText( Current_Row.getAddress());
            //Log.e("Adapter", "after -->" + ((TextView) convertView.findViewById(R.id.row_address)).getText().toString());
            //Log.e("Adapter", "Num -> " + position + " Addd-> " + Current_Row.getAddress());
            ((TextView) convertView.findViewById(R.id.row_price_nonpay)).setText(Current_Row.getPayment()+"");
            ((TextView) convertView.findViewById(R.id.row_payment_nonpay)).setText(Current_Row.getRemain()+"");
            ((TextView) convertView.findViewById(R.id.row_date_nonpay)).setText(Current_Row.getDate());
        }
        return convertView;
    }
}
