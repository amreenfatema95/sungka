package com.afssurani.sungka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainMenuActivity extends Activity
{
    Button singlePlayer;
    Button twoPlayer;
    Button multiPlayer;
    Button highScores;
    Vibrator vibe;
    Button instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Initialises the vibration function so it can be implemented for the buttons in the main menu
        vibe = (Vibrator) MainMenuActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

//        //No Title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //Set to fullscreen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        singlePlayer = (Button)findViewById(R.id.single);
        twoPlayer = (Button)findViewById(R.id.singledevice);
        multiPlayer = (Button)findViewById(R.id.multi);
        highScores = (Button)findViewById(R.id.highscore);
        instructions = (Button) findViewById(R.id.instuctions);

    }

    //links the button to the single player board
    public void singlePlayerBoard(View view)
    {
        //Intent intent = new Intent(MainMenuActivity.this,Name of AI class)
        vibe.vibrate(80);
        //startActivity(intent);
    }

    //links the button to the two player board
    public void twoPlayerBoard(View view)
    {
        Intent intent = new Intent(MainMenuActivity.this,Name_2Player.class);
        vibe.vibrate(80);
        startActivity(intent);
    }

    //links the button to the multiplayer board
    public void multiPlayerBoard(View view)
    {
        //Intent intent = new Intent(MainMenuActivity.this,ClientActivity.class);
        vibe.vibrate(80);
        //startActivity(intent);
    }

    //links the button to the high score screen.
    public void highScoreScreen(View view)
    {
        Intent intent = new Intent(MainMenuActivity.this,HighscoresActivity.class);
        vibe.vibrate(80);
        startActivity(intent);
    }
    public void instructions(View view)
    {
        Intent intent = new Intent(MainMenuActivity.this,Instructions.class);
        vibe.vibrate(80);
        startActivity(intent);
    }
}