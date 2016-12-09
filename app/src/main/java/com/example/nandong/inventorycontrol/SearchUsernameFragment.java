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

/**
 * Created by nandong on 12/9/16.
 */

public class SearchUsernameFragment extends Fragment implements View.OnClickListener {

    private Button searchBtn;
    private EditText usernameTxt;
    private String username;

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
                Intent intent = new Intent(getActivity(), DisplayInventoryListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "please type in username", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

