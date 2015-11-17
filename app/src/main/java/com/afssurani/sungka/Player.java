package com.afssurani.sungka;

import java.util.ArrayList;

/**
 * Created by afssurani on 24/10/2015.
 */

//This class stores information about the player
public class Player
{
    public ArrayList<Tray> trays;
    public Store store;
    private boolean _allTraysClicked = false;
    public String playerName;
    public String winnerLooser;
    public int totalScore;

    //Assigns 7 small trays and ONE store to THE player.
    public Player()
    {
        trays= new ArrayList<Tray>();
        for(int i=0;i<7;i++)
        {
            trays.add(new Tray());
        }
        store = new Store();
    }

    public Player(String playerName,int totalScore,String winnerLooser)
    {
        this.playerName =playerName;
        this.winnerLooser = winnerLooser;
        this.totalScore = totalScore;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public void setTotalScore(int totalScore)
    {
        this.totalScore = totalScore;
    }

    public void setWinnerLooser(String winnerLooser)
    {
        this.winnerLooser = winnerLooser;
    }

    public String getWinnerLooser()
    {
        return winnerLooser;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public int getTotalScore()
    {
        return totalScore;
    }

    public boolean allTraysClicked(){
        if(_allTraysClicked != true){
            checkAllTraysClicked();
        }

        return _allTraysClicked;
    }

    private void checkAllTraysClicked(){
        int clicked = 0;

        for(int i=0;i<7;i++)
        {
            if(trays.get(i).clicked() == true){
                clicked ++;
            }
        }

        if(clicked == 8){
            _allTraysClicked = true;
        }
    }

    public ArrayList<Tray> getTrays()
    {
        return trays;
    }







}
