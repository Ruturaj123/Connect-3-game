package com.example.hp_owner.connect3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // yellow = 0, red = 1
    int activePlayer = 0;

    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    boolean gameIsActive = true;

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        counter.setTranslationY(-1000f);

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(500);

            for(int[] winningPosition : winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    //Someone has won
                    gameIsActive = false;
                    String winner = "Red";
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setBackgroundColor(Color.RED);

                    if (gameState[winningPosition[0]] == 0){
                        winner = "Yellow";
                        layout.setBackgroundColor(Color.YELLOW);
                    }

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has Won!");

                    Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                    layout.startAnimation(slideUp);
                    layout.setVisibility(View.VISIBLE);

                }
                else{
                    boolean gameIsOver = true;

                    for(int counterState : gameState){
                        if(counterState == 2){
                            gameIsOver = false;
                            break;
                        }
                    }

                    if(gameIsOver){
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw!");

                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setBackgroundColor(Color.WHITE);
                        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                        layout.startAnimation(slideUp);
                        layout.setVisibility(View.VISIBLE);

                    }
                }
            }
        }
    }

    public void playAgain(View view){

        gameIsActive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for(int i = 0; i < gameState.length; i++)
            gameState[i] = 2;

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

}
