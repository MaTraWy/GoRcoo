package com.matrawy.a7oda.gorcoo.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.matrawy.a7oda.gorcoo.Person_Customer;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

/**
 * Created by 7oda on 8/16/2017.
 */

public class Address_Adapter extends ArrayAdapter<Person_Customer> {
    protected ArrayList<Person_Customer> original;
    protected ArrayList<Person_Customer> fitems;
    protected Filter filter;

    public Address_Adapter(Activity context, List<Person_Customer> Data_List) {
        super(context, 0, Data_List);
        original = new ArrayList<>();
        for(Person_Customer m:Data_List)
            original.add(m);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Person_Customer Current_Row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(simple_list_item_1, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(Current_Row.getAddress());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new PersonNameFilter();

        return filter;
    }

    class PersonNameFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();
            if (prefix == null || prefix.length() == 0) {
                ArrayList<Person_Customer> list = new ArrayList<Person_Customer>(original);
                results.values = list;
                Log.e("list",String.valueOf(list.size()));
                results.count = list.size();
            } else {
                final ArrayList<Person_Customer> list = new ArrayList<Person_Customer>(original);
                final ArrayList<Person_Customer> nlist = new ArrayList<Person_Customer>();
                int count = list.size();

                for (int i = 0; i < count; i++) {
                    final Person_Customer person = list.get(i);
                    final String value = person.getAddress().toLowerCase();

                    if (value.startsWith(prefix)) {
                        nlist.add(person);
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
            fitems = (ArrayList<Person_Customer>) results.values;
            clear();
            try {
                int count = fitems.size();
                for (int i = 0; i < count; i++) {
                    Person_Customer person_customer = fitems.get(i);
                    add(person_customer);
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }

        }

    }

}
