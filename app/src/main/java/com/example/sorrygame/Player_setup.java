package com.example.sorrygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class Player_setup extends AppCompatActivity implements View.OnClickListener {
    Button btnBack;
    Button btnStart;
    EditText etPR;
    EditText etPB;
    EditText etPY;
    EditText etPG;
    ToggleButton tbPR;
    ToggleButton tbPB;
    ToggleButton tbPY;
    ToggleButton tbPG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        btnBack = findViewById(R.id.btnBack);
        btnStart = findViewById(R.id.btnStartGame);

        tbPR = findViewById(R.id.tbPR);
        tbPB = findViewById(R.id.tbPB);
        tbPY = findViewById(R.id.tbPY);
        tbPG = findViewById(R.id.tbPG);

        etPR = findViewById(R.id.etP1);
        etPB = findViewById(R.id.etP2);
        etPY = findViewById(R.id.etP3);
        etPG = findViewById(R.id.etP4);

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
            Log.d("toggle", "onClick: "+tbPR.isChecked());
            Intent intent = new Intent(this, Game_Board.class);

            intent.putExtra("pR", etPR.getText().toString());
            intent.putExtra("pB", etPB.getText().toString());
            intent.putExtra("pY", etPY.getText().toString());
            intent.putExtra("pG", etPG.getText().toString());

            intent.putExtra("mG", tbPG.isChecked());
            intent.putExtra("mY", tbPY.isChecked());
            intent.putExtra("mB", tbPB.isChecked());
            intent.putExtra("mR", tbPR.isChecked());
            this.startActivity(intent);
        }
    }
}