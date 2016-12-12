package com.example.nandong.inventorycontrol;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nandong on 12/5/16.
 */


public class ScanBarcodeFragment extends Fragment implements View.OnClickListener {
    private Button scanBtn;
    private TextView formatTxt, contentTxt;
    private static final String ITEM_INFO_URL = "http://ec2-35-162-62-161.us-west-2.compute.amazonaws.com:8000/item_info?barcode=";

    public static ScanBarcodeFragment newInstance() {
        return new ScanBarcodeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_barcode, container, false);


        scanBtn = (Button) view.findViewById(R.id.scan_button);
        formatTxt = (TextView) view.findViewById(R.id.scan_format);
        contentTxt = (TextView) view.findViewById(R.id.scan_content);

        scanBtn.setOnClickListener(this);
        return view;
    }

    public void onClick(View view){
        //respond to clicks
        if(view.getId() == R.id.scan_button){
            //scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //we have a result
            final String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String itemInfo = fetchScannedItemInfo(scanContent);

                        if (!itemInfo.isEmpty()) {
                            Intent intent = new Intent(getActivity(), DisplayItemActivity.class);
                            intent.putExtra("itemInfo", itemInfo);
                            startActivity(intent);
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "scanned item not found in database", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (IOException | JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String fetchScannedItemInfo(String scanContent) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = ITEM_INFO_URL + scanContent;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();

        try {
            JSONObject obj = new JSONObject(responseString);
            if (obj.length() == 0) {
                return "";
            }
            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
