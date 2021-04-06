package com.example.sorrygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //declare widgets
    Button btnStart;
    Button btnSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ref variables
        btnStart = findViewById(R.id.btnStart);
        btnSettings = findViewById(R.id.btnSettings);

        btnStart.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnStart.getId()) {
            Intent intent = new Intent(this, Player_setup.class);
            this.startActivity(intent);
        } else if (v.getId() == btnSettings.getId()) {
            Intent intent = new Intent(this, Settings.class);
            this.startActivity(intent);
        }
    }
}