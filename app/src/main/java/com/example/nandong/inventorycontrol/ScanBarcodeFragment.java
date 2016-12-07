package com.example.nandong.inventorycontrol;

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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by nandong on 12/5/16.
 */


public class ScanBarcodeFragment extends Fragment implements View.OnClickListener {
    private Button scanBtn;
    private TextView formatTxt, contentTxt;

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

    public void onClick(View v){
        //respond to clicks
        if(v.getId() == R.id.scan_button){
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
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);

            intent = new Intent(getActivity(), DisplayItemActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
