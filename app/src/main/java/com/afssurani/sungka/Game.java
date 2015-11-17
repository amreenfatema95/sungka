package com.afssurani.sungka;

import android.util.Log;
import android.content.Context;

/**
 * Created by afssurani on 24/10/2015.
 */

//Creates a a two player game with the assumption that Player 1 owns the trays in the top row
public class Game
{
    public Player player1 = new Player();
    public Player player2 = new Player();
    public Player turn = null;
    public Boolean isLastShellInStore=true;

    //Selects a player randomly for the first turn
    public Game( )
    {
        if ((Math.random()*10)%2==0)
        {
            turn = player2;
        }
        else
        {
            turn = player1;
        }

        /*
        I HAVE THIS METHOD COMMENTED OUT FOR NOW, TEST IT WHEN 2 PLAYERS ON 2 DEVICES IS POSSIBLE,
        ALSO COMMENT OUT THE ABOVE if else STATEMENTS WHEN DOING SO
        */
        //whoStarts();
    }

    //checks to see which player will start first
    private void whoStarts(){
        //will keep looping while the players are at a draw
        while(turn == null){
            if(player1.allTraysClicked() == true){
                turn = player1;
            }

            else if(player2.allTraysClicked() == true){
                turn = player2;
            }
        }
    }

    //1-done
    //Returns the number of shells in the selected tray
    public int getShellsInHand(Player currentPlayer,int selectedTray)
    {
        return currentPlayer.trays.get(selectedTray).getShellCount();

    }

    //Redistributes the shells for player 2 when a tray is selected
    public void redistributeShellsP2(Player currentPlayer,int selectedTray)
    {
        int shellsInHand = getShellsInHand(currentPlayer,selectedTray);


        currentPlayer.trays.get(selectedTray).setShellCount(0);

        for (int i=selectedTray+1;(i<currentPlayer.trays.size())&&(shellsInHand!=0);i++)
        {
            currentPlayer.trays.get(i).addShell();
            shellsInHand--;
            if ((shellsInHand==0)&&(currentPlayer.trays.get(i).getShellCount()==1))
            {
                bonusShells(i);
            }
        }

        while (shellsInHand!=0)
        {
            currentPlayer.store.addShell();
            shellsInHand--;
            if (shellsInHand==0)
            {
                isLastShellInStore=true;
            }

            for (int i = player1.trays.size()-1;(i>=0)&&(shellsInHand!=0);i--)
            {
                player1.trays.get(i).addShell();
                shellsInHand--;
                if ((shellsInHand==0)&&(currentPlayer.trays.get(i).getShellCount()==1))
                {
                    bonusShells(i);
                }
            }

            for (int i = 0;(i<currentPlayer.trays.size())&&(shellsInHand!=0);i++)
            {
                currentPlayer.trays.get(i).addShell();
                shellsInHand--;
                if ((shellsInHand==0)&&(currentPlayer.trays.get(i).getShellCount()==1))
                {
                    bonusShells(i);
                }
            }
        }
    }

    //3-done
    //Redistributes the shells for player 1 when a tray is selected
    public void redistributeShellsP1(Player currentPlayer,int selectedTray)
    {
        int shellsInHand = getShellsInHand(currentPlayer,selectedTray);

        currentPlayer.trays.get(selectedTray).setShellCount(0);

        for (int i=selectedTray-1;(i>=0)&&(shellsInHand!=0);i--)
        {
            currentPlayer.trays.get(i).addShell();
            shellsInHand--;
            if ((shellsInHand==0)&&(currentPlayer.trays.get(i).getShellCount()==1))
            {
                bonusShells(i);
            }
        }

        while (shellsInHand!=0)
        {

            currentPlayer.store.addShell();
            shellsInHand--;
            if (shellsInHand==0)
            {
                isLastShellInStore=true;
            }

            for (int i =0;(i<currentPlayer.trays.size())&&(shellsInHand!=0);i++)
            {
                player2.trays.get(i).addShell();
                shellsInHand--;
                if ((shellsInHand==0)&&(currentPlayer.trays.get(i).getShellCount()==1))
                {
                    bonusShells(i);
                }
            }

            for (int i=currentPlayer.trays.size()-1;(i>=0)&&(shellsInHand!=0);i--)
            {
                currentPlayer.trays.get(i).addShell();
                shellsInHand--;
                if ((shellsInHand==0)&&(currentPlayer.trays.get(i).getShellCount()==1))
                {
                    bonusShells(i);
                }
            }
        }

    }

    //Assigning the next move
    public void nextTurn()
    {
        if(isLastShellInStore==false)
        {
            if((turn.equals(player1))&&!(areTraysEmpty(player2)))
            {
                turn = player2;
            }
            else if((turn.equals(player1))&&(areTraysEmpty(player2)))
            {
                turn = player1;
            }
            else if ((turn.equals(player2)&&!(areTraysEmpty(player1))))
            {
                turn = player1;
            }
            else
            {
                turn = player2;
            }
        }
        else
        {
            if ((turn.equals(player1))&&!(areTraysEmpty(player1)))
            {
                turn = player1;
            }
            else if ((turn.equals(player2))&&!(areTraysEmpty(player2)))
            {
                turn = player2;
            }
            else if ((turn.equals(player2))&&(areTraysEmpty(player2)))
            {
                turn = player1;
            }
            else
            {
                turn = player2;
            }
        }
        isLastShellInStore = false;

    }

    //Checks each tray of the player to see if there are no shells in any of his trays
    public Boolean areTraysEmpty(Player currentPlayer)
    {
        int i=0;

        while ((i<7)&&(currentPlayer.trays.get(i).isEmpty()))
        {
            i++;
        }
        if (i==7)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    //Caputuring the opponent's shells
    public void bonusShells(int trayNum)
    {
        int numShells;
        if (turn.equals(player1))
        {
            numShells = player2.trays.get(trayNum).getShellCount();
            // Log.d("NumShells", ""+numShells);
            player2.trays.get(trayNum).setShellCount(0);
            turn.store.setShellCount(turn.store.getShellCount()+numShells+1);
            player1.trays.get(trayNum).setShellCount(0);
        }
        else
        {
            numShells = player1.trays.get(trayNum).getShellCount();
            player1.trays.get(trayNum).setShellCount(0);
            turn.store.setShellCount(turn.store.getShellCount()+numShells+1);
            player2.trays.get(trayNum).setShellCount(0);
        }
    }

    // checking if the game is over
    public boolean isGameOver()
    {
        if ((player1.store.getShellCount())+(player2.store.getShellCount())==98)
        {
            updateStats();
            return true;
        }
        else
        {
            return false;
        }
    }




    // deciding the winner when the game is Over.
    public String gameWinner()
    {
        if ((player1.store.getShellCount())>(player2.store.getShellCount()))
        {
            player1.setWinnerLooser("Won");
            player2.setWinnerLooser("Lost");
            return player1.getPlayerName();
        }
        else if ((player1.store.getShellCount())<(player2.store.getShellCount()))
        {
            player2.setWinnerLooser("Won");
            player1.setWinnerLooser("Lost");
            return  player2.getPlayerName();
        }
        else
        {
            player1.setWinnerLooser("Draw");
            player2.setWinnerLooser("Draw");
            return player1.getPlayerName() + " "+ player2.getPlayerName();
        }
    }

    public void updateStats()
    {
        //set total score for each player
        player1.setTotalScore(player1.store.getShellCount());
        player2.setTotalScore(player2.store.getShellCount());
    }


}












