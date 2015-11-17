package com.afssurani.sungka;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {
    Game game;

    //displays which players turn it is
    TextView playerTurnView;

    //player 1 trays/bottons
    ArrayList<Button> player1Trays = new ArrayList<Button>();
    //player 2 trays/buttons
    ArrayList<Button> player2Trays = new ArrayList<Button>();

    //player stores
    Button storeP1; //player 1 store
    Button storeP2; //player 2 store

    String player1Name;
    String player2Name;

    ScoreDatabaseHandler scoreDBHandler;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ServerActivity servact = new ServerActivity();
        Intent intent = getIntent();
        player1Name = intent.getStringExtra(Name_2Player.EXTRA_MESSAGE1);
        player2Name = intent.getStringExtra(Name_2Player.EXTRA_MESSAGE2);

        TextView player1text = (TextView) findViewById(R.id.player1text);
        TextView player2text = (TextView) findViewById(R.id.player2text);

        player1text.setText(player1Name);
        player2text.setText(player2Name);

        init();
        game.player1.setPlayerName(player1text.getText().toString());
        game.player2.setPlayerName(player2text.getText().toString());



        displayPlayerTurn();

    }

    public void displayPlayerTurn()
    {
        if (game.isGameOver())
        {
            if (game.gameWinner().equals(game.player1.getPlayerName()))
            {
                updateDBStats();
                Toast announceWinner=Toast.makeText(getApplicationContext(),player1Name+" Wins! :)",Toast.LENGTH_LONG);
                announceWinner.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                announceWinner.show();
            }
            else if (game.gameWinner().equals(game.player2.getPlayerName()))
            {
                updateDBStats();
                Toast announceWinner=Toast.makeText(getApplicationContext(),player2Name+" Wins! :)",Toast.LENGTH_LONG);
                announceWinner.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                announceWinner.show();
            }
            else
            {
                updateDBStats();
                Toast announceDraw=Toast.makeText(getApplicationContext(),"Draw!",Toast.LENGTH_LONG);
                announceDraw.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                announceDraw.show();
            }
        }
        else
        {
            game.nextTurn();
            if (game.turn.equals(game.player1))
            {
                Toast player1Toast = Toast.makeText(getApplicationContext(), player1Name+" plays!", Toast.LENGTH_SHORT);
                player1Toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                player1Toast.show();
                //disabling player 2 trays when its player 1s turn
                for(int i=0;i<player2Trays.size();i++)
                {
                    player2Trays.get(i).setEnabled(false);
                    // if the shell count in any tray of player 1 is 0 the button is disabled
                    if (player1Trays.get(i).getText().toString().equals("0"))
                    {
                        player1Trays.get(i).setEnabled(false);
                    }
                    else
                    {
                        player1Trays.get(i).setEnabled(true);
                    }
                }

            }
            else if (game.turn.equals(game.player2))
            {
                Toast player2Toast=Toast.makeText(getApplicationContext(), player2Name+" plays!", Toast.LENGTH_SHORT);
                player2Toast.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,0);
                player2Toast.show();
                //disabling player 1 trays when its player 2s turn
                for(int i=0;i<player1Trays.size();i++)
                {
                    player1Trays.get(i).setEnabled(false);
                    // if the shell count in any tray of player 2 is 0 the button is disabled
                    if (player2Trays.get(i).getText().toString().equals("0"))
                    {
                        player2Trays.get(i).setEnabled(false);
                    }
                    else
                    {
                        player2Trays.get(i).setEnabled(true);
                    }

                }
            }
        }
    }

    //initialising the game/application
    private void init(){
        game = new Game();

        //playerTurnView = (TextView) findViewById(R.id.playerTurnView);

        // Store buttons initialised.
        storeP1 = (Button) findViewById(R.id.storep1);
        storeP2 = (Button) findViewById(R.id.storep2);

        //Player 1 trays/buttons initialised
        player1Trays.add((Button) findViewById(R.id.tray0p1));
        player1Trays.add((Button) findViewById(R.id.tray1p1));
        player1Trays.add((Button) findViewById(R.id.tray2p1));
        player1Trays.add((Button) findViewById(R.id.tray3p1));
        player1Trays.add((Button) findViewById(R.id.tray4p1));
        player1Trays.add((Button) findViewById(R.id.tray5p1));
        player1Trays.add((Button) findViewById(R.id.tray6p1));

        //Player 2 trays/buttons initialised
        player2Trays.add((Button) findViewById(R.id.tray0p2));
        player2Trays.add((Button) findViewById(R.id.tray1p2));
        player2Trays.add((Button) findViewById(R.id.tray2p2));
        player2Trays.add((Button) findViewById(R.id.tray3p2));
        player2Trays.add((Button) findViewById(R.id.tray4p2));
        player2Trays.add((Button) findViewById(R.id.tray5p2));
        player2Trays.add((Button) findViewById(R.id.tray6p2));

        //Attaching listeners to each tray for player1 & player2 .
        for(int i=0;i<player1Trays.size();i++)
        {
            player1Trays.get(i).setOnClickListener(new player1OnClickListener());
            player2Trays.get(i).setOnClickListener(new player2OnClickListener());
        }

        displayButtonValues();
    }

    //this method return selected tray number.
    public int selectedTrayNumber(View v)
    {
        for(int i =0;i<player1Trays.size();i++)
        {
            if((v.getId() == player1Trays.get(i).getId())||(v.getId() == player2Trays.get(i).getId()))
            {
                return i;
            }
        }
        return -1;
    }

    //displays the values of the buttons is accordance to the players trays/stores values
    protected void displayButtonValues()
    {
        for (int i=0;i<player2Trays.size();i++)
        {
            player1Trays.get(i).setText(Integer.toString(game.player1.trays.get(i).getShellCount()));
            player2Trays.get(i).setText(Integer.toString(game.player2.trays.get(i).getShellCount()));
        }
        storeP1.setText(Integer.toString(game.player1.store.getShellCount()));
        storeP2.setText(Integer.toString(game.player2.store.getShellCount()));
        updateButtonGraphics();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class player1OnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            int selectedTray = selectedTrayNumber(v);
            game.redistributeShellsP1(game.player1, selectedTray);
            game.player1.trays.get(selectedTray).clickedOn();
            displayButtonValues();
            displayPlayerTurn();
        }
    }

    class player2OnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            int selectedTray = selectedTrayNumber(v);
            game.redistributeShellsP2(game.player2, selectedTray);
            game.player2.trays.get(selectedTray).clickedOn();
            displayButtonValues();
            displayPlayerTurn();
        }
    }

    /**
     *Updates pebble count in all stores and trays
     */
    private void updateButtonGraphics(){
        //Button array
        Button[] allBluePlayerStore = {storeP1, player1Trays.get(0),player1Trays.get(1),player1Trays.get(2),player1Trays.get(3),player1Trays.get(4),player1Trays.get(5),player1Trays.get(6)};
        Button[] allRedPlayerStore = {storeP2,player2Trays.get(0),player2Trays.get(1),player2Trays.get(2),player2Trays.get(3),player2Trays.get(4),player2Trays.get(5),player2Trays.get(6)};

        //Red player graphics
        for(int i = 0; i<allRedPlayerStore.length; i++) {
            switch (Integer.parseInt(allRedPlayerStore[i].getText().toString())) {
                case 0:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p0);
                    break;
                case 1:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p1);
                    break;
                case 2:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p2);
                    break;
                case 3:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p3);
                    break;
                case 4:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p4);
                    break;
                case 5:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p5);
                    break;
                case 6:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p6);
                    break;
                case 7:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p7);
                    break;
                case 8:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p8);
                    break;
                case 9:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p9);
                    break;
                case 10:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p10);
                    break;
                case 11:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p11);
                    break;
                //12 Or more
                default:
                    allRedPlayerStore[i].setBackgroundResource(R.drawable.button2_red_p12);
            }
        }
        //Blue Player graphics
        for(int i = 0; i<allBluePlayerStore.length; i++) {
            switch (Integer.parseInt(allBluePlayerStore[i].getText().toString())) {
                case 0:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p0);
                    break;
                case 1:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p1);
                    break;
                case 2:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p2);
                    break;
                case 3:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button2_red_p3);
                    break;
                case 4:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p4);
                    break;
                case 5:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p5);
                    break;
                case 6:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p6);
                    break;
                case 7:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p7);
                    break;
                case 8:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p8);
                    break;
                case 9:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p9);
                    break;
                case 10:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p10);
                    break;
                case 11:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p11);
                    break;
                //12 Or more
                default:
                    allBluePlayerStore[i].setBackgroundResource(R.drawable.button1_blue_p12);
            }
        }
    }

    //update database with playerscores
    public void updateDBStats()
    {
        scoreDBHandler = new ScoreDatabaseHandler(this);
        SQLiteDatabase scoreDB = scoreDBHandler.getWritableDatabase();
        scoreDBHandler.onCreate(scoreDB);
        scoreDBHandler.addPlayer(game.player1);
        scoreDBHandler.addPlayer(game.player2);
    }
}
