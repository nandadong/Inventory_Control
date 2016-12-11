package com.example.nandong.inventorycontrol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandong on 12/11/16.
 */

@SuppressWarnings("ConstantConditions")
public class AuthActivity extends AppCompatActivity {
    public static final String KEY_URL = "https://canvas.vt.edu/login/cas";
    public static final String REDIRECT_URI = "https://canvas.vt.edu/?login_success=1";
    @BindView(R.id.webview) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.equals(REDIRECT_URI)) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        String url = KEY_URL;
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
