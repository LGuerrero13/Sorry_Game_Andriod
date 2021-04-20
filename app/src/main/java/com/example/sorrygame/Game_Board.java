package com.example.sorrygame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;

import java.util.regex.Pattern;

public class Game_Board extends AppCompatActivity implements OnClickListener {

    ImageView[][] coordinates = new ImageView[17][17];

    int X = 0;
    int Y = 0;
    int X2 = 0;
    int Y2 = 0;
    String turn = "red";
    Boolean isSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__board);
        createBoardVariables();
    }

    public void createBoardVariables() {
        String tileID;
        while (Y < 16) {
            while (X < 16) {
                tileID = "x" + X + "y" + Y;
                // create ref variables to imageviews
                try {
                    int resID = getResources().getIdentifier(tileID, "id", getPackageName());
                    coordinates[X][Y] = (findViewById(resID));
                    //Log.d("ids", tileID);
                    coordinates[X][Y].setOnClickListener(this);
                } catch (Exception e) {

                }

                X += 1;
            }
            // reset x coord
            X = 0;
            Y += 1;
        }
    }

    @Override
    public void onClick(View view) {
        whichTile(view);
        if (isSelected) {
            if (coordinates[X][Y] != coordinates[X2][Y2]) {
                whichPiece();
            }
        }
        else {
            X2 = X;
            Y2 = Y;
        }
        selectionStateSwitch();
    }

    public void selectionStateSwitch() {
        if (isSelected) {
            isSelected = false;
            coordinates[X2][Y2].setColorFilter(null);
           // removeTint();
        } else {
            isSelected = true;
            coordinates[X][Y].setColorFilter(Color.GREEN);
            //tintLegal();
        }

    }

    public void whichTile(View v) {
        Y=0;
        X=0;
        while (Y < 16) {
            while (X < 16) {
                try {
                    if (coordinates[X][Y].getId() == v.getId()) {
                        //coordinates[X][Y].setImageResource(R.drawable.blackpawn);
                        break;
                    }
                } catch (Exception e) {
                }
                X += 1;
            }
            try {
                if (coordinates[X][Y].getId() == v.getId()) {
                   // coordinates[X][Y].setImageResource(R.drawable.blackpawn);
                    break;
                }
            } catch (Exception e) {
            }
            // reset X coord
            X = 0;
            Y += 1;
        }
    }

    public void whichPiece() {
        String piece;
        String prey;
        if (coordinates[X2][Y2].getTag() == null) {
            piece = "";
            prey = "";
        } else if (coordinates[X][Y].getTag() == null) {
            prey = "";
            piece = (String) coordinates[X2][Y2].getTag();
        } else {
            piece = (String) coordinates[X2][Y2].getTag();
            prey = (String) coordinates[X][Y].getTag();
        }
        if (turn == "red" && !Pattern.matches("^R.*", prey) && piece.equals("RP")) {
            coordinates[X][Y].setImageResource(R.drawable.redpawn);
            setTile(piece);
        }
       else if (turn == "yellow" && !Pattern.matches("^Y.*", prey)&& piece.equals("YP")) {
            coordinates[X][Y].setImageResource(R.drawable.yellowpawn);
            setTile(piece);
        }
       else if (turn == "green" && !Pattern.matches("^G.*", prey)&& piece.equals("GP")) {
            coordinates[X][Y].setImageResource(R.drawable.greenpawn);
            setTile(piece);
        }
       else if (turn == "blue" && !Pattern.matches("^B.*", prey)&& piece.equals("BP")) {
            coordinates[X][Y].setImageResource(R.drawable.bluepawn);
            setTile(piece);
        }
    }

    public void setTile(String s) {
        //boolean endGame = kingCheck();
        Log.d("A", "setTile: "+ s);
        coordinates[X][Y].setTag(s);
        coordinates[X2][Y2].setImageDrawable(null);
        coordinates[X2][Y2].setTag(null);
       //if (endGame == true){
           // gameEnd();
        //} else {
            turnSwitch();
      //  }
    }

    public void turnSwitch() {
        if (turn.equals("red")) {
            turn = "blue";
           // tvTurn.setText("turn: " + namePB);
        } else if (turn.equals("blue")){
            turn = "yellow";
           // tvTurn.setText("turn: " + namePW);
        } else if (turn.equals("yellow")){
            turn = "green";
        } else if (turn.equals("green")) {
            turn = "red";
        }
    }

}