package com.example.sorrygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Player_setup extends AppCompatActivity implements View.OnClickListener {
    Button btnBack;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        btnBack = findViewById(R.id.btnBack);
        btnStart = findViewById(R.id.btnStartGame);

        btnBack.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnBack.getId()) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }
        else if (v.getId()== btnStart.getId()) {
            Log.d("ass", "onClick: poo");
            Intent intent = new Intent(this, Game_Board.class);
            this.startActivity(intent);
        }
    }
}