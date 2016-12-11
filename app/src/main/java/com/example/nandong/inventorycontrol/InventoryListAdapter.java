package com.example.nandong.inventorycontrol;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nandong on 12/9/16.
 */

public class InventoryListAdapter extends BaseAdapter {

    private Context context;
    private JSONArray data;

    public InventoryListAdapter(Context context, JSONArray data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return data.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.inventory_list_item, viewGroup, false);
        }
        JSONObject jsonObject = null;
        try {
             jsonObject = (JSONObject) data.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            ((TextView) convertView.findViewById(R.id.item_description)).setText(jsonObject.get("description").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String itemInfo = jsonObject.toString();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisplayItemActivity.class);

                intent.putExtra("itemInfo", itemInfo);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
