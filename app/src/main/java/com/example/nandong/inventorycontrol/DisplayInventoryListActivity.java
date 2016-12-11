package com.example.nandong.inventorycontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandong on 12/9/16.
 */

public class DisplayInventoryListActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String rawInventoryList = getIntent().getStringExtra("rawInventoryList");
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(rawInventoryList);
            setupInventoryListUI(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupInventoryListUI(JSONArray jsonArray) {
        ListView listView = (ListView) findViewById(R.id.list_view);
        InventoryListAdapter adapter = new InventoryListAdapter(this, jsonArray);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
