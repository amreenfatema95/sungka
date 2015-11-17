import com.afssurani.sungka.Tray;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by afssurani on 09/11/2015.
 */
public class TrayTest
{


    //testing the remove shell method
    @Test
    public void testRemoveShell()
    {
        final Tray tray = new Tray();
        //when
        int initialShells = 6;
        tray.setShellCount(initialShells);
        tray.removeShell();

        //then
        assertEquals("remove shell method failed",5,tray.getShellCount());
    }

    //testing the isEmpty method when isEmpty=false
    @Test
    public void testIsEmptyMethod1()
    {
        final Tray tray = new Tray();
        assertFalse(tray.isEmpty());
    }

    //testing the isEmpty method when isEmpty=true
    @Test
    public void testIsEmptyMethod2()
    {
        final Tray tray = new Tray();
        //when
        tray.setShellCount(0);

        //then
        assertTrue(tray.isEmpty());
    }
}
