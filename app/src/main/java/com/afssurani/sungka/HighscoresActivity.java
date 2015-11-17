package com.afssurani.sungka;

import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;


public class HighscoresActivity extends Activity {

    public TextView firstName;
    public TextView firstScore;
    public TextView firstWinLossRatio;

    public  TextView secondName;
    public TextView secondScore;
    public TextView secondWinLossRatio;

    public TextView thirdName;
    public TextView thirdScore;
    public TextView thirdWinLossRatio;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        Intent intent = getIntent();
        init();
    }

    private void init()
    {
        firstName = (TextView) findViewById(R.id.firstPersonsName);
        firstScore = (TextView) findViewById(R.id.highScore1);
        firstWinLossRatio = (TextView) findViewById(R.id.firstWinLossRatio);

        secondName = (TextView) findViewById(R.id.secondPersonName);
        secondScore = (TextView) findViewById(R.id.highScore2);
        secondWinLossRatio = (TextView) findViewById(R.id.secondWinLossRatio);

        thirdName = (TextView) findViewById(R.id.thirdPersonName);
        thirdScore = (TextView) findViewById(R.id.highScore3);
        thirdWinLossRatio = (TextView) findViewById(R.id.thirdWinLossRatio);

        ScoreDatabaseHandler scoreDatabaseHandler = new ScoreDatabaseHandler(this);
        String[][] winnerarray = scoreDatabaseHandler.topThreePlayers();

        if (winnerarray.length>0)
        {
            firstName.setText(winnerarray[0][0]);
            firstScore.setText("Total Score: "+winnerarray[0][1]);
            firstWinLossRatio.setText("Games won: "+winnerarray[0][2]+"/"+winnerarray[0][3]);
        }

        if (winnerarray.length>1)
        {
            secondName.setText(winnerarray[1][0]);
            secondScore.setText("Total Score: "+winnerarray[1][1]);
            secondWinLossRatio.setText("Games won: "+winnerarray[1][2]+"/"+winnerarray[1][3]);
        }

        if(winnerarray.length>2)
        {
            thirdName.setText(winnerarray[2][0]);
            thirdScore.setText("Total Score: "+winnerarray[2][1]);
            thirdWinLossRatio.setText("Games won: "+winnerarray[2][2]+"/"+winnerarray[2][3]);
        }








    }

}
