package com.example.nandong.inventorycontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nandong on 12/12/16.
 */

public class TransferOwnershipActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.transfer_button) Button transferBtn;
    @BindView(R.id.transfer_username) EditText transferName;
    private static final String TRANSFER_URL = "http://ec2-35-162-62-161.us-west-2.compute.amazonaws.com:8000/ownership_trans";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        transferName = null;

        final String barcode = getIntent().getStringExtra("barcode");

        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String newCustodian = transferName.getText().toString();
                            String result = postOwnershipTransfer(barcode, newCustodian);
                            if (result.equals("OK")) {
                                Intent intent = new Intent(TransferOwnershipActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "ownership transfer successful", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "ownership transfer failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    private String postOwnershipTransfer(String barcode, String newCustodian) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody postBody = new FormBody.Builder()
                .add("barcode", barcode)
                .add("newCustodian", newCustodian)
                .build();
        Request request = new Request.Builder()
                .url(TRANSFER_URL)
                .post(postBody)
                .build();
        Response response = client.newCall(request).execute();

        String responseString = response.body().string();
        try {
            JSONObject obj = new JSONObject(responseString);
            return obj.getString("Result");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
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
