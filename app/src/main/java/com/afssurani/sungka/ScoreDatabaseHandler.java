package com.afssurani.sungka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by afssurani on 14/11/2015.
 */
public class ScoreDatabaseHandler extends SQLiteOpenHelper
{
    //Database Version
    private static final int DATABASE_VERSION=1;

    //Database Name
    private static final String DATABASE_NAME = "GameStatistics";

    // Player's table name
    private static final String TABLE_GAME = "Players";

    // Players Table Column names
    private static final String KEY_ID = "id";
    private static final String Name = "playerName";
    private static final String Score = "score";
    private static final String Win_Loose = "winLose";

    public ScoreDatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public String getDBName()
    {
        return DATABASE_NAME;
    }

    //Creating Tables
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_GAME_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GAME + "("+
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +Name + " TEXT,"
                +Score + " INTEGER,"+Win_Loose+ " TEXT" +")";
        db.execSQL(CREATE_GAME_TABLE);
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);

        //Create tables again
        onCreate(db);
    }

    //Adding new player
    public void addPlayer(Player player)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Name,player.getPlayerName()); //Player name
        values.put(Score,player.getTotalScore());// game score
        values.put(Win_Loose, player.getWinnerLooser());//won or lost

        //instering a row

        db.insert(TABLE_GAME,null,values);
        db.close();
    }

    //getting a single player
    public Player getPlayer(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GAME, new String[]{KEY_ID,
                        Name, Score, Win_Loose}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor !=null)
            cursor.moveToFirst();

        Player player = new Player(cursor.getString(1),Integer.parseInt(cursor.getString(2))
                ,cursor.getString(3));

        //return player
        return player;
    }

    //get total score of a player- all his games
    public int playerTotalScore(String playerName)
    {
        String selectQuery = "SELECT DISTINCT SUM(score) FROM " + TABLE_GAME + " WHERE playerName = '"+ playerName +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        int totalScore = cursor.getInt(0);
        cursor.close();
        return totalScore;
    }

    //returns the total number of wins for the given player
    public int playerWinTotal(String playerName)
    {
        String selectQuery = "SELECT * FROM " + TABLE_GAME + " WHERE playerName = '"+ playerName +"' AND winLose = 'Won'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int totalWin = cursor.getCount();
        cursor.close();
        return totalWin;
    }

    //returns the total number of games played by the given player
    public int playerTotalGamesPlayed(String playerName)
    {
        String selectQuery = "SELECT * FROM " + TABLE_GAME + " WHERE playerName = '"+ playerName +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int totalGamesPlayed = cursor.getCount();
        cursor.close();
        return totalGamesPlayed;
    }

    // query for a distinct list of players
    public ArrayList<String> distinctPlayerList()
    {
        ArrayList<String> names = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT playerName FROM " + TABLE_GAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst())
        {
            do
            {
                names.add(cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }

    //query to return a 2D array of players and their total scores
    public String[][] resultsArray()
    {
        ArrayList<String> playerList = distinctPlayerList();
        String[][] array = new String[playerList.size()][4];
        for (int i =0;i<playerList.size();i++)
        {
            array[i][0] = playerList.get(i);
            array[i][1] = Integer.toString(playerTotalScore(playerList.get(i)));
            array[i][2] = Integer.toString((playerWinTotal(playerList.get(i))));
            array[i][3] = Integer.toString(playerTotalGamesPlayed(playerList.get(i)));
        }
        return array;
    }

    public static String[][] arraySort(String[][] ar) {
        Arrays.sort(ar, new Comparator<String[]>() {
            @Override
            public int compare(String[] int1, String[] int2) {
                Integer numOfKeys1 = Integer.parseInt(int1[1]);
                Integer numOfKeys2 = Integer.parseInt(int2[1]);
                return numOfKeys1.compareTo(numOfKeys2);
            }
        });
        return ar;
    }

    //getting top 3 players
    public String[][] topThreePlayers()
    {
        String[][] array = arraySort(resultsArray());
        String[][] sortedArray= new String[3][4];;

        if(array.length==1)
        {
            for (int i = 0;i<1;i++)
            {
                sortedArray[i][0] = array[array.length-i-1][0];
                sortedArray[i][1] = array[array.length-i-1][1];
                sortedArray[i][2] = array[array.length-i-1][2];
                sortedArray[i][3] = array[array.length-i-1][3];
            }
        }

        if(array.length==2)
        {
            for (int i = 0;i<2;i++)
            {
                sortedArray[i][0] = array[array.length-i-1][0];
                sortedArray[i][1] = array[array.length-i-1][1];
                sortedArray[i][2] = array[array.length-i-1][2];
                sortedArray[i][3] = array[array.length-i-1][3];
            }
        }

        if(array.length>=3)
        {
            for (int i = 0;i<3;i++)
            {
                sortedArray[i][0] = array[array.length-i-1][0];
                sortedArray[i][1] = array[array.length-i-1][1];
                sortedArray[i][2] = array[array.length-i-1][2];
                sortedArray[i][3] = array[array.length-i-1][3];
            }
        }

        return sortedArray;
    }
}
