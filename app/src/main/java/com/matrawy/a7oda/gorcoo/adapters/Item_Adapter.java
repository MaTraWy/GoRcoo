package com.matrawy.a7oda.gorcoo.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.Data.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import static android.R.layout.simple_list_item_1;

/**
 * Created by 7oda on 8/16/2017.
 */

public class Item_Adapter  extends ArrayAdapter<Item> {
protected ArrayList<Item> original;
protected ArrayList<Item> fitems;
protected Filter filter;

public Item_Adapter(Activity context, List<Item> Data_List) {
        super(context, 0, Data_List);
        original = new ArrayList<>();
        for(Item m:Data_List)
        original.add(m);
        }
    public View getView(int position, View convertView, ViewGroup parent) {
        Item Current_Row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(simple_list_item_1, parent, false);
        }

        ((TextView) convertView.findViewById(android.R.id.text1)).setText(Current_Row.getName());
        return convertView;
    }
    class PersonNameFilter extends android.widget.Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();
            if (prefix == null || prefix.length() == 0) {
                ArrayList<Item> list = new ArrayList<Item>(original);
                results.values = list;
                Log.e("list",String.valueOf(list.size()));
                results.count = list.size();
            } else {
                final ArrayList<Item> list = new ArrayList<Item>(original);
                final ArrayList<Item> nlist = new ArrayList<Item>();
                int count = list.size();

                for (int i = 0; i < count; i++) {
                    final Item item = list.get(i);
                    final String value = item.getName().toLowerCase();

                    if (value.startsWith(prefix)) {
                        nlist.add(item);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            fitems = (ArrayList<Item>) results.values;
            clear();
            try {
                int count = fitems.size();
                for (int i = 0; i < count; i++) {
                    Item item = fitems.get(i);
                    add(item);
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }

        }

    }
}
