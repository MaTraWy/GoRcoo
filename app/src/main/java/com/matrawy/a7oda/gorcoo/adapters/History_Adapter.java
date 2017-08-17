package com.matrawy.a7oda.gorcoo.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 7oda on 7/14/2017.
 */

public class History_Adapter extends ArrayAdapter<Converge_Data_Class> {
    Context m;
    protected ArrayList<Converge_Data_Class> original;
    protected ArrayList<Converge_Data_Class> fitems;
    protected Filter filter;

    public History_Adapter(Activity context, List<Converge_Data_Class> Data_List) {
        super(context, 0, Data_List);
        m = context;
        original = new ArrayList<>();
        for(Converge_Data_Class m:Data_List)
            original.add(m);}

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Log.e("Postion", position + "");
        Converge_Data_Class Current_Row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_table, parent, false);
        }
        /*ImageView img = (ImageView) convertView.findViewById(R.id.img);
        Picasso.with(m).load(current_movie.getImg_main()).into(img);
        img.setAdjustViewBounds(true);*/
        if (Current_Row != null) {

            ((TextView) convertView.findViewById(R.id.row_num)).setText(Current_Row.getNum());
            ((TextView) convertView.findViewById(R.id.row_code)).setText(Current_Row.getCode());
            ((TextView) convertView.findViewById(R.id.row_name)).setText(Current_Row.getName());
            //Log.e("Adapter", "Before -->" + ((TextView) convertView.findViewById(R.id.row_address)).getText().toString());
            ((TextView) convertView.findViewById(R.id.row_address)).setText(Current_Row.getAddress());
            //Log.e("Adapter", "after -->" + ((TextView) convertView.findViewById(R.id.row_address)).getText().toString());
            //Log.e("Adapter", "Num -> " + position + " Addd-> " + Current_Row.getAddress());
            ((TextView) convertView.findViewById(R.id.row_price)).setText(Current_Row.getPayment()+"");
            ((TextView) convertView.findViewById(R.id.row_payment)).setText(Current_Row.getRemain()+"");
            ((TextView) convertView.findViewById(R.id.row_date)).setText(Current_Row.getDate());
/*            if(type)
            {
              ((ViewGroup)(convertView.findViewById(R.id.row_user).getParent())).removeView(convertView.findViewById(R.id.row_user));
            }
            else*/
            ((TextView) convertView.findViewById(R.id.row_user)).setText(Current_Row.getUser());
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new HistoryNameFilter();

        return filter;
    }

    protected class HistoryNameFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();
            if (prefix == null || prefix.length() == 0) {
             //   Log.e("Filter", "Test - null");
                ArrayList<Converge_Data_Class> list = new ArrayList<Converge_Data_Class> (original);
                results.values = list;
                results.count = list.size();
            } else {
                final ArrayList<Converge_Data_Class> list = new ArrayList<Converge_Data_Class>(original);
                final ArrayList<Converge_Data_Class> nlist = new ArrayList<Converge_Data_Class>();
                int count = list.size();
              //  Log.e("Filter", "Test not null -->"+list.size());

                for (int i = 0; i < count; i++) {
                    final Converge_Data_Class pkmn = list.get(i);
                    final String value = pkmn.getName().toLowerCase();

                    if (value.startsWith(prefix)) {
                        nlist.add(pkmn);
                    }
                }
                results.values = nlist;
                Log.e("Result", nlist.size() + "");
                results.count = nlist.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            fitems = (ArrayList<Converge_Data_Class>) results.values;

            clear();
            try {
                int count = fitems.size();
                for (int i = 0; i < count; i++) {
                    Converge_Data_Class pkmn = (Converge_Data_Class) fitems.get(i);
                    add(pkmn);
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }

        }

    }
}
