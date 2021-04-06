package com.example.sorrygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    Spinner spnrTheme;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spnrTheme = findViewById(R.id.spnrTheme);
        btnReturn = findViewById(R.id.btnstnsBack);
        String[] items = new String[]{ "default", "jungle", "ocean", "desert"};
        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spnrTheme.setAdapter(adapter);

        btnReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnReturn.getId()) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }
    }
}