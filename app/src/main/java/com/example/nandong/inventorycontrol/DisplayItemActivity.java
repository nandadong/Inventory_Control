package com.example.nandong.inventorycontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandong on 12/6/16.
 */

public class DisplayItemActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.item_transfer_button) Button itemTransferBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_info);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String itemInfo = getIntent().getStringExtra("itemInfo");
        //Log.d("Item info", "Item info is " + itemInfo);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(itemInfo);
            setupItemInfoUI(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JSONObject finalJsonObject = jsonObject;
        itemTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayItemActivity.this, TransferOwnershipActivity.class);
                try {
                    intent.putExtra("barcode", finalJsonObject.get("barcode").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

    }

    private void setupItemInfoUI(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            finish();
        }

        ((TextView) findViewById(R.id.description))
                .setText(jsonObject.get("description").toString());
        ((TextView) findViewById(R.id.organization))
                .setText(jsonObject.get("organization").toString());
        ((TextView) findViewById(R.id.location))
                .setText(jsonObject.get("location").toString());
        ((TextView) findViewById(R.id.barcode))
                .setText(jsonObject.get("barcode").toString());
        ((TextView) findViewById(R.id.manufacturer))
                .setText(jsonObject.get("manufacturer").toString());
        ((TextView) findViewById(R.id.model))
                .setText(jsonObject.get("model").toString());
        ((TextView) findViewById(R.id.custodian))
                .setText(jsonObject.get("custodian").toString());
        ((TextView) findViewById(R.id.acq_date))
                .setText(jsonObject.get("acq_date").toString());
        ((TextView) findViewById(R.id.amt))
                .setText(jsonObject.get("amt").toString());
        ((TextView) findViewById(R.id.ownership))
                .setText(jsonObject.get("ownership").toString());
        ((TextView) findViewById(R.id.condition))
                .setText(jsonObject.get("condition").toString());
        ((TextView) findViewById(R.id.last_inv_date))
                .setText(jsonObject.get("last_inv_date").toString());
        ((TextView) findViewById(R.id.designation))
                .setText(jsonObject.get("designation").toString());
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
