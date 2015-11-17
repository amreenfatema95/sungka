import com.afssurani.sungka.Game;
import com.afssurani.sungka.Player;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by afssurani on 09/11/2015.
 */
public class GameTest
{

    //testing the getShellsInHand method
    @Test
    public void testGetShellsInHand()
    {
        final Game game = new Game();
        int shellsInTray = game.player1.trays.get(5).getShellCount();
        //assertEquals("shellsInHand test failed",shellsInTray,game.getShellsInHand(game.player1,5));
        assertEquals("shellsInHand test failed",7,game.getShellsInHand(game.player1,5));
    }


    //testing the redistributeShellsP2 method
    @Test
    public void testRedistributeShellsP2()
    {
        final Game game = new Game();
        //when
        game.player2.store.setShellCount(13);
        game.redistributeShellsP2(game.player2,0);

        //then
        assertEquals("selected tray shell count= 0 failed",0,game.player2.trays.get(0).getShellCount());
       for(int i=1;i<7;i++)
       {
            assertEquals("Player2:shells redistribution failed",8,game.player2.trays.get(i).getShellCount());
       }
        assertTrue(game.player2.store.getShellCount() == 14);
    }


    //testing the redistributeShellsP1 method
    @Test
    public void testRedistributeShellsP1()
    {
        final Game game = new Game();
        //when
        game.player1.store.setShellCount(13);
        game.redistributeShellsP1(game.player1, 6);

        //then
        assertEquals("Player1:selected tray shell count= 0 failed",0,game.player1.trays.get(6).getShellCount());
        for(int i=5;i>=0;i--)
        {
            assertEquals("shells redistribution failed",8,game.player1.trays.get(i).getShellCount());
        }
        assertTrue(game.player1.store.getShellCount()==14);
    }


    //testing the next turn method case1
    @Test
    public void testNextTurn1()
    {
        final Game game = new Game();
        //when
        game.turn=game.player1;//its the player 1's turn
        game.isLastShellInStore=true;//the last shell drops in player1's store
        game.nextTurn();// nextTurn method called

        //then
        assertEquals("nextTurn test1 failed", game.player1, game.turn);

    }

    //testing the next turn method case 2
    @Test
    public void testNextTurn2()
    {
        final Game game = new Game();
        //when
        game.turn=game.player1;//its the player 1's turn
        game.isLastShellInStore=false;//the last shell does not drop in player1's store
        game.nextTurn();// nextTurn method called

        //then
        assertEquals("nextTurn test2 failed",game.player2,game.turn);
    }

    //testing the next turn method case 3
    @Test
    public void testNextTurn3()
    {
        final Game game = new Game();
        //when
        game.turn=game.player1;//its the player 1's turn
        game.isLastShellInStore=true;//the last shell drops in player1's store
        //but all his trays are empty
        for(int i=0;i<7;i++)
        {
            game.player1.trays.get(i).setShellCount(0);
        }
        game.nextTurn();// nextTurn method called

        //then
        assertEquals("nextTurn test3 failed",game.player2,game.turn);
    }


    //testing the areTraysEmpty method
    @Test
    public void testAreTraysEmpty()
    {
        //when
        final Game game = new Game();//  new game starts none of the trays of player 2 are empty

        //then
        for (int i =0;i<7;i++)
        {
            assertTrue("Tray "+i+" is Empty",game.player2.trays.get(i).getShellCount()!=0);
        }
    }


    //testing the bonusShells method
    @Test
    public void testBonusShells()
    {
        final Game game = new Game();

        game.turn = game.player2;

        //when

        game.player1.trays.get(5).setShellCount(5);//player1's 6th tray has 5 shells
        game.player2.store.setShellCount(7);//the player2's store  has 7 shells
        int expectedOutcome = 13;
        game.bonusShells(5);


        //then
        //assertTrue(game.player2.store.getShellCount()==13);
        assertEquals("capturing method failed", expectedOutcome, game.player2.store.getShellCount());
        assertEquals("opponent's tray not empty",0,game.player1.trays.get(5).getShellCount());



    }


    //testing the isGameOver method
    @Test
    public void testIsGameOver()
    {
        final Game game = new Game();
        game.player1.store.setShellCount(72);
        game.player2.store.setShellCount(26);
        assertTrue(game.isGameOver());
        //assertEquals("isGameOver test failed", 98, game.player1.store.getShellCount() + game.player2.store.getShellCount());
    }


    //testing the gameWinnerMethod when player1 wins
    @Test
    public void testgameWinnerMethod1()
    {
        final Game game = new Game();
        game.player1.store.setShellCount(72);
        game.player2.store.setShellCount(26);
        assertEquals("gameWinner test method1 failed", game.player1, game.gameWinner());
    }


    //testing the gameWinnerMethod when player2 wins
    @Test
    public void testgameWinnerMethod2()
    {
        final Game game = new Game();
        game.player1.store.setShellCount(26);
        game.player2.store.setShellCount(72);
        assertEquals("gameWinner test method2 failed",game.player2,game.gameWinner());
    }


    //testing the gameWinnerMethod when its a tie
    @Test
    public void testgameWinnerMethod3()
    {
        final Game game = new Game();
        game.player1.store.setShellCount(49);
        game.player2.store.setShellCount(49);
        assertEquals("gameWinner test method3 failed",null,game.gameWinner());
    }


}
