package com.example.copdatdrawer;

/**
 * Created by Rose on 3/27/2018.
 */
//ListView for Purchases
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class purchase_list_Adapter {


    ListView listView;

    public static class LstViewAdapter extends ArrayAdapter<String> {
        int groupid;
        String[] item_list;
        ArrayList<String> desc;
        Context context;
        public LstViewAdapter(Context context, int vg, int id, String[] item_list){
            super(context,vg, id, item_list);
            this.context=context;
            groupid=vg;
            this.item_list=item_list;

        }
        // Hold views of the ListView to improve its scrolling performance
        public class ViewHolder {
            public TextView textview;
            public Button button;

        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            // Inflate the list_item.xml file if convertView is null
            if(rowView==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView= inflater.inflate(groupid, parent, false);
                ViewHolder viewHolder = new ViewHolder();
               // viewHolder.textview= (TextView) rowView.findViewById(R.id.txt);
               // viewHolder.button= (Button) rowView.findViewById(R.id.bt);
                rowView.setTag(viewHolder);

            }
            // Set text to each TextView of ListView item
            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.textview.setText(item_list[position]);
            holder.button.setText(item_list[position]);
            return rowView;
        }

    }
}



