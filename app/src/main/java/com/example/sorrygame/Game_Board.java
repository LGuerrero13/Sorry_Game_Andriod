package com.example.sorrygame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

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
    int endX = 0;
    int endY = 0;
    int startX = 0;
    int startY = 0;
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
        while (endY < 16) {
            while (endX < 16) {
                tileID = "x" + endX + "y" + endY;
                // create ref variables to imageviews
                try {
                    int resID = getResources().getIdentifier(tileID, "id", getPackageName());
                    coordinates[endX][endY] = (findViewById(resID));
                    //Log.d("ids", tileID);
                    coordinates[endX][endY].setOnClickListener(this);
                } catch (Exception e) {
                }
                endX += 1;
            }
            // reset x coord
            endX = 0;
            endY += 1;
        }
     //   deleteStart();
        for (int i = 0; i < letters.length; i++) {
            while (n < 3) {
                n +=1;
                tileID = "s" + letters[i] + n;
                int resID = getResources().getIdentifier(tileID, "id", getPackageName());
                starts[i][n] = (findViewById(resID));
                Log.d("start", "createBoardVariables: " + starts[i][n] + " " + i + " " + n);
                starts[i][n].setOnClickListener(this);
            }
            n = 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                if (coordinates[endX][endY] != coordinates[startX][startY]) {
                    whichPiece();
                    Log.d("ugh", "endX "+endX +" endY "+endY+" startX "+startX+" startY "+startY);
                }
            } else {
                startX = endX;
                startY = endY;
                Log.d("ugh", "endX "+endX +" endY "+endY+" startX "+startX+" startY "+startY);
            }
            selectionStateSwitch();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getRoll() {
        // use default constructor to create 'pseudo-random' numbers.
        Random generator = new Random();
        Roll = 0;
        int randNum = 1 + generator.nextInt(6); // generate # from 1-6
        DieSwitch(randNum, die1);
        Roll += randNum;
        randNum = 1 + generator.nextInt(6); // generate # from 1-6
        DieSwitch(randNum, die2);
        Roll += randNum;
        Log.d("Roll", String.valueOf(Roll));
        if (Roll == 6) {
            Log.d("Roll", "You rolled a 6! Re-rolling.");
            getRoll();
        }
       Rolled = true;
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
           // coordinates[startX][startY].setColorFilter(null);
           // removeTint();
        } else {
            isSelected = true;
            //coordinates[endX][endY].setColorFilter(Color.GREEN);
            //tintLegal();
        }
    }

    public int difference() {
        int difference = 0;
        difference += abs(endX - startX);
        difference += abs(endY - startY);
        return difference;
    }

    public void rulesSwitch() {
        // add indicator on game board for what each roll does
        legalMove = false;
        int diff = difference();
        //if (!Rolled){
         //   return;
     //   }
        // set roll value for debug, remove later
       //Roll = 12;
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
                // if they've rolled a 7 and their not at beginning then do whatever
                if (diff == 7 && !startCheck()) {
                    legalMove = true; // it's a valid move
                }
                else {
                    turnSwitch(); // skip their turn and go to the next person
                    legalMove = false; // not a valid move!
                }
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
                //  forward 10 or back 1-3
                if (diff == 10 && !isMovingBack()){
                    legalMove = true;
                } else if (diff <= 3 && isMovingBack()) {
                    legalMove = true;
                }
                break;
            case 11:
                // forward 11 spaces or swap pos with opponent
                if (diff == 11) {
                    legalMove = true;
                }
                else if (coordinates[endX][endY].getTag() != null) {
                    legalMove = true;
                }
                break;
            case 12:
                if (startCheck()) {
                    legalMove = true;
                }
                //die.setImageResource(R.drawable.six);
                break;
        }
        Log.d("sigh", "rulesSwitch: legalMove = " + legalMove);
    }

    private boolean startCheck(){
        if (turn == "red" && (startX == 11 && startY == 14)){
            return true;
        } else if (turn == "blue" && (startX == 1 && startY == 11)){
            return true;
        } else if (turn == "yellow" && (startX == 4 && startY == 1)){
            return true;
        } else if (turn == "green" && (startX == 14 && startY == 4)){
            return true;
        } else {
            return false;
        }
    }

    //check if moving backward
    public boolean isMovingBack() {
        boolean backwards = false;
        int diffX = endX - startX;
        int diffY = endY- startY;
            if (startX == 0 || (startX == 1 && startY == 11)){
                if (diffY > 0){
                    backwards = true;
                } else if (startY == 15 && diffX > 0) {
                    backwards = true;
                } else {
                    backwards = false;
                }
            } else if (startX == 15 || (startX == 14 && startY == 4)){
                if (diffY < 0){
                    backwards = true;
                } else if (startY == 0 && diffX < 0) {
                    backwards = true;
                } else {
                    backwards = false;
                }
            } else if (startY == 15 || (startX == 11 && startY == 14)){
                if (diffX > 0){
                    backwards = true;
                } else if (startX == 15 && diffY < 0) {
                    backwards = true;
                } else {
                    backwards = false;
                }
            } else if (startY == 0 || (startX == 4 && startY == 1)){
                if (diffX < 0){
                    backwards = true;
                } else if (startX == 0 && diffY > 0) {
                    backwards = true;
                } else {
                    backwards = false;
                }
            }
        Log.d("moveCheck", "isMovingBack: " + backwards);
        return backwards;
    }

    private boolean startCheck(View v) {
      int  Y=0;
       int X=0;
        while (Y < 4) {
            while (X < 3) {
                X +=1;
                if (starts[Y][X].getId() == v.getId()) {
                    break;
                }
            }
            if (starts[Y][X].getId() == v.getId()) {
                break;
            }
           X = 0;
           Y += 1;
        }
        if (turn == "red" && Y == 0){
            endY = 14;
            endX = 11;
            hopper(X, Y);
            return true;
        } else if (turn == "blue" && Y == 1){
            endY = 11;
            endX = 1;
            hopper(X, Y);
            return true;
        } else if (turn == "yellow" && Y == 2){
            endY = 1;
            endX = 4;
            hopper(X, Y);
            return true;
        } else if (turn == "green" && Y == 3){
            endY = 4;
            endX = 14;
            hopper(X, Y);
            return true;
        } else {
            return false;
        }

    }

    private void hopper(int X, int Y){
      Object startTag = starts[Y][X].getTag();
      Drawable startDraw = starts[Y][X].getDrawable();
      Object queueTag = coordinates[endX][endY].getTag();
      Drawable queueDraw = coordinates[endX][endY].getDrawable();
      starts[Y][X].setImageDrawable(queueDraw);
      starts[Y][X].setTag(queueTag);
      coordinates[endX][endY].setImageDrawable(startDraw);
      coordinates[endX][endY].setTag(startTag);
    }

    public void whichTile(View v) {
        // iterating through start tiles
        if (startCheck(v)) {
            return;
        }
        // iterating through standard play tiles
        endY=0;
        endX=0;
        while (endY < 16) {
            while (endX < 16) {
                try {
                    if (coordinates[endX][endY].getId() == v.getId()) {
                        //coordinates[endX][endY].setImageResource(R.drawable.blackpawn);
                        break;
                    }
                } catch (Exception e) {
                }
                endX += 1;
            }
            try {
                if (coordinates[endX][endY].getId() == v.getId()) {
                   // coordinates[endX][endY].setImageResource(R.drawable.blackpawn);
                    break;
                }
            } catch (Exception e) {
            }
            // reset endX coord
            endX = 0;
            endY += 1;
        }
    }

    public void whichPiece() {
        rulesSwitch();
        homeCancel();
        String piece;
        String prey;
        Boolean Legal;
        if (legalMove == false){
            return;
        }
        if (coordinates[startX][startY].getTag() == null) {
            piece = "";
            prey = "";
        } else if (coordinates[endX][endY].getTag() == null) {
            prey = "";
            piece = (String) coordinates[startX][startY].getTag();
        } else {
            piece = (String) coordinates[startX][startY].getTag();
            prey = (String) coordinates[endX][endY].getTag();
        }
        if (turn == "red" && !Pattern.matches("^R.*", prey) && piece.equals("RP") && legalMove) {
            if (!swap(piece, prey)){
                if (prey != ""){ sendHome(prey);}
                setTile(piece);
            }
            coordinates[endX][endY].setImageResource(R.drawable.redpawn);
        }
       else if (turn == "yellow" && !Pattern.matches("^Y.*", prey)&& piece.equals("YP") && legalMove) {
            if (!swap(piece, prey)){
                if (prey != ""){ sendHome(prey);}
                setTile(piece);
            }
            coordinates[endX][endY].setImageResource(R.drawable.yellowpawn);
        }
       else if (turn == "green" && !Pattern.matches("^G.*", prey)&& piece.equals("GP") && legalMove) {
            if (!swap(piece, prey)){
                if (prey != ""){ sendHome(prey);}
                setTile(piece);
            }
            coordinates[endX][endY].setImageResource(R.drawable.greenpawn);
        }
       else if (turn == "blue" && !Pattern.matches("^B.*", prey)&& piece.equals("BP") && legalMove) {
            if (!swap(piece, prey)){
                if (prey != ""){ sendHome(prey);}
                setTile(piece);
            }
            coordinates[endX][endY].setImageResource(R.drawable.bluepawn);
        }
    }

    private boolean swap(String s, String q) {
        if (Roll == 11 && !(11 == difference())) {
            coordinates[startX][startY].setImageDrawable(coordinates[endX][endY].getDrawable());
            String tag = (String) coordinates[endX][endY].getTag();
            coordinates[endX][endY].setTag(s);
            coordinates[startX][startY].setTag(tag);
            turnSwitch();
            return true;
        } else if (Roll == 12) {
            if (q != ""){ sendHome(q);}
            Object startTag = coordinates[startX][startY].getTag();
            Drawable startDraw = coordinates[startX][startY].getDrawable();
            Object queueTag = coordinates[endX][endY].getTag();
            coordinates[endX][endY].setImageDrawable(startDraw);
            coordinates[endX][endY].setTag(startTag);
            coordinates[startX][startY].setImageDrawable(null);
            coordinates[startX][startY].setTag(null);
            turnSwitch();
            return true;
        } else {
            return false;
        }
    }

    private void sendHome(String homie){
        switch (homie){
            case "RP":
                coordinates[11][14].setImageResource(R.drawable.redpawn);
                coordinates[11][14].setTag("RP");
                break;
            case "BP":
                coordinates[1][11].setImageResource(R.drawable.bluepawn);
                coordinates[1][11].setTag("BP");
                break;
            case "YP":
                coordinates[4][1].setImageResource(R.drawable.yellowpawn);
                coordinates[4][1].setTag("YP");
                break;
            case "GP":
                coordinates[14][4].setImageResource(R.drawable.greenpawn);
                coordinates[14][4].setTag("GP");
                break;
        }
    }

    public void homeCancel() {
        switch (turn) {
            case "red":
                if (endX >=13 && startX < 13){
                    if (Roll != 11 && Roll != 12) {
                        legalMove = false;
                    }
                }
                break;
            case "blue":
                if (endY >=13 && startY < 13){
                    if (Roll != 11 && Roll != 12) {
                        legalMove = false;
                    }
                }
                break;
            case "yellow":
                if (endX <2 && startX > 2){
                    if (Roll != 11 && Roll != 12) {
                        legalMove = false;
                    }
                }
                break;
            case "green":
                if (endY <=2 && startY > 2){
                    if (Roll != 11 && Roll != 12) {
                        legalMove = false;
                    }
                }
                break;
        }
    }

    public void setTile(String s) {
        Log.d("A", "setTile: "+ s);
        coordinates[endX][endY].setTag(s);
        coordinates[startX][startY].setImageDrawable(null);
        coordinates[startX][startY].setTag(null);
       //if (endGame == true){
           // gameEnd();
        //} else {
        turnSwitch();
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