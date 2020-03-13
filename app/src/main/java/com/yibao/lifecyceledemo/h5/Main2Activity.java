package com.yibao.lifecyceledemo.h5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yibao.lifecyceledemo.MainActivity;
import com.yibao.lifecyceledemo.R;

public class Main2Activity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.btn_start).setOnClickListener(v -> startActivity(new Intent(Main2Activity.this, MainActivity.class)));
        mWebView = findViewById(R.id.web_view2);
        initData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initData() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl("file:///android_asset/store.html");
        mWebView.addJavascriptInterface(new JsObject(this), "smartisan");
    }
}
