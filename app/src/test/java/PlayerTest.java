
import com.afssurani.sungka.Player;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by afssurani on 08/11/2015.
 */




public class PlayerTest
{

    //Test if player has 7 trays

    @Test
    public void testPlayerTrays()
    {
        Player testPlayer = new Player();
        int totalTrays = testPlayer.getTrays().size();
        assertEquals("Player does not have 7trays",7,totalTrays);

    }

    //Test if player has 7 shells in each tray when game is initialised

    @Test
    public void trayShellCount()
    {
        for(int i=0;i<7;i++)
        {
            Player testPlayer = new Player();
            int shells = testPlayer.getTrays().get(i).getShellCount();
            assertEquals("Tray"+i+1+"does not have 7 shells",7,shells);
        }
    }

}



