package com.example.nandong.inventorycontrol;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by nandong on 12/11/16.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.activity_login_btn) TextView loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // load access token from shared preference

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });

    }
}
