package com.yibao.lifecyceledemo.h5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yibao.lifecyceledemo.MainActivity;
import com.yibao.lifecyceledemo.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.btn_start).setOnClickListener(v -> startActivity(new Intent(Main2Activity.this, MainActivity.class)));
    }
}
