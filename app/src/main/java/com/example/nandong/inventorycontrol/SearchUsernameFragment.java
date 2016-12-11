package com.example.nandong.inventorycontrol;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nandong on 12/9/16.
 */

public class SearchUsernameFragment extends Fragment implements View.OnClickListener {

    private Button searchBtn;
    private EditText usernameTxt;
    private String username;
    private static final String USER_INVENTORY_URL = "http://ec2-35-162-62-161.us-west-2.compute.amazonaws.com:8000/item_info?username=";

    public static SearchUsernameFragment newInstance() {
        return new SearchUsernameFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_username, container, false);

        usernameTxt = (EditText) view.findViewById(R.id.search_username);
        username = null;
        searchBtn = (Button) view.findViewById(R.id.search_button);

        searchBtn.setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.search_button) {
            // search
            username = usernameTxt.getText().toString();
            if (!username.isEmpty()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String rawInventoryList = fetchUserInventoryList(username);
                            if (!rawInventoryList.isEmpty()) {
                                Intent intent = new Intent(getActivity(), DisplayInventoryListActivity.class);
                                intent.putExtra("rawInventoryList", rawInventoryList);
                                startActivity(intent);
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "custodian not found in database", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (IOException | JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "please type in username", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String fetchUserInventoryList(String userName) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = USER_INVENTORY_URL + userName;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();

        try {
            JSONArray arr = new JSONArray(responseString);
            if (arr.length() == 0) {
                return "";
            }
            return arr.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}

