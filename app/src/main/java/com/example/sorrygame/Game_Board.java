package com.example.sorrygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.util.concurrent.ThreadLocalRandom;

import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class Game_Board extends AppCompatActivity implements OnClickListener {

    ImageView[][] coordinates = new ImageView[17][17];

    ImageView[][] starts = new ImageView[5][5];

    String[] letters = new String []{"R","B","Y","G"};

    TextView tvTurn;
    ImageView die1;
    ImageView die2;
    Button btnRoll;

    int Roll = 0;
    Boolean Rolled = false;

    int n = 0;
    int X = 0;
    int Y = 0;
    int X2 = 0;
    int Y2 = 0;
    String namePR;
    String namePB;
    String namePY;
    String namePG;

    Boolean modeG;
    Boolean modeY;
    Boolean modeB;
    Boolean modeR;

    String turn = "red";
    Boolean isSelected = false;
    Boolean legalMove = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__board);

        Intent intent = getIntent();
        namePR = intent.getStringExtra("pR");
        namePB = intent.getStringExtra("pB");
        namePY = intent.getStringExtra("pY");
        namePG = intent.getStringExtra("pG");

        modeG = intent.getBooleanExtra("mG", true);
        modeY = intent.getBooleanExtra("mY", true);
        modeB = intent.getBooleanExtra("mB", true);
        modeR = intent.getBooleanExtra("mR", true);

        die1 = findViewById(R.id.ivDie1);
        die2 = findViewById(R.id.ivDie2);
        btnRoll = findViewById(R.id.btnRoll);
        btnRoll.setOnClickListener(this);

        tvTurn = findViewById(R.id.tvTurn);
        tvTurn.setText("turn: " + namePR);
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
        for (int i = 0; i < letters.length; i++) {
            while (n < 4) {
                n +=1;
                tileID = "s" + letters[i] + n;
                int resID = getResources().getIdentifier(tileID, "id", getPackageName());
                starts[i][n] = (findViewById(resID));
                Log.d("start", "createBoardVariables: " + starts[i][n]);
            }
            n = 0;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnRoll.getId()) {
            if (Rolled == false){
                getRoll();
            }
        }
        else {
            whichTile(view);
            if (isSelected) {
                if (coordinates[X][Y] != coordinates[X2][Y2]) {
                    whichPiece();
                }
            } else {
                X2 = X;
                Y2 = Y;
            }
            selectionStateSwitch();
        }
    }

    private void getRoll() {
        Roll = 0;
        int randNum = ThreadLocalRandom.current().nextInt(1, (6+1));
        DieSwitch(randNum, die1);
        Roll += randNum;
        randNum = ThreadLocalRandom.current().nextInt(1, (6+1));
        DieSwitch(randNum, die2);
        Roll += randNum;
        Log.d("Roll", String.valueOf(Roll));
        if (Roll == 6) {
            getRoll();
        }
       // Rolled = true;

    }

    public void DieSwitch(int num, ImageView die) {
        switch (num){
            case 1:
                die.setImageResource(R.drawable.one);
                break;
            case 2:
                die.setImageResource(R.drawable.two);
                break;
            case 3:
                die.setImageResource(R.drawable.three);
                break;
            case 4:
                die.setImageResource(R.drawable.four);
                break;
            case 5:
                die.setImageResource(R.drawable.five);
                break;
            case 6:
                die.setImageResource(R.drawable.six);
                break;
        }
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

    public int difference() {
        int difference = 0;
        difference += abs(X - X2);
        difference += abs(Y - Y2);
        return difference;
    }

    public void rulesSwitch() {
        // add indicator on gameboard for what each roll does
        legalMove = false;
        int diff = difference();
        switch (Roll){
            case 2:
                // Move pawn 2 spaces

                if (diff == 2){
                    legalMove = true;
                }
                // code for moving out of start
                break;
            case 3:
                // Move pawn 3 spaces
                if ( diff ==3 ){
                    legalMove = true;
                }
                // code for moving out of start
                break;
            case 4:
                // Move pawn 4 spaces
                if ( diff ==4){
                    legalMove = true;
                }
                // code for moving out of start
                break;
            case 5:
                // Move pawn 5 spaces
                if ( diff ==5){
                    legalMove = true;
                }
                // code for moving out of start
                break;
            case 7:
                //  die.setImageResource(R.drawable.one);
                break;
            case 8:
                // Move pawn 5 spaces
                if (diff ==8){
                    legalMove = true;
                }
                // code for moving out of start
                break;
            case 9:
                // Move pawn 5 spaces
                if (diff ==9){
                    legalMove = true;
                }
                // code for moving out of start
                break;
            case 10:
                //  die.setImageResource(R.drawable.four);
                break;
            case 11:
                // die.setImageResource(R.drawable.five);
                break;
            case 12:
                //die.setImageResource(R.drawable.six);
                break;
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
        rulesSwitch();
        homeCancel();
        String piece;
        String prey;
        Boolean Legal;
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
        if (turn == "red" && !Pattern.matches("^R.*", prey) && piece.equals("RP") && legalMove) {
            coordinates[X][Y].setImageResource(R.drawable.redpawn);
            setTile(piece);
        }
       else if (turn == "yellow" && !Pattern.matches("^Y.*", prey)&& piece.equals("YP") && legalMove) {
            coordinates[X][Y].setImageResource(R.drawable.yellowpawn);
            setTile(piece);
        }
       else if (turn == "green" && !Pattern.matches("^G.*", prey)&& piece.equals("GP") && legalMove) {
            coordinates[X][Y].setImageResource(R.drawable.greenpawn);
            setTile(piece);
        }
       else if (turn == "blue" && !Pattern.matches("^B.*", prey)&& piece.equals("BP") && legalMove) {
            coordinates[X][Y].setImageResource(R.drawable.bluepawn);
            setTile(piece);
        }
    }

    public void homeCancel() {
        switch (turn) {
            case "red":
                if (X >=13 && X2 < 13){
                    if (Roll != 11 && Roll != 12) {
                        legalMove = false;
                    }
                }
                break;
            case "blue":
                if (Y >=13 && Y2 < 13){
                    if (Roll != 11 && Roll != 12) {
                        legalMove = false;
                    }
                }
                break;
            case "yellow":
                if (X <=2 && X2 > 2){
                    if (Roll != 11 && Roll != 12) {
                        legalMove = false;
                    }
                }
                break;
            case "green":
                if (Y <=2 && Y2 > 2){
                    if (Roll != 11 && Roll != 12) {
                        legalMove = false;
                    }
                }
                break;
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
            tvTurn.setText("turn: " + namePB);
        } else if (turn.equals("blue")){
            turn = "yellow";
            tvTurn.setText("turn: " + namePY);
        } else if (turn.equals("yellow")){
            turn = "green";
            tvTurn.setText("turn: " + namePG);
        } else if (turn.equals("green")) {
            turn = "red";
            tvTurn.setText("turn: " + namePR);
        }
        Rolled = false;
    }

}