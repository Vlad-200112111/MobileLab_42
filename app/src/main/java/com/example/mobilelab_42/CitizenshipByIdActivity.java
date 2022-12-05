package com.example.mobilelab_42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CitizenshipByIdActivity extends AppCompatActivity {

    TextView tvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizenship_by_id);

        tvView = (TextView) findViewById(R.id.tvView);

        Intent intent = getIntent();

        String id = intent.getStringExtra("pos");
    }
}