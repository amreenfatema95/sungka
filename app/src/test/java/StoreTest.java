/**
 * Created by afssurani on 09/11/2015.
 */

import com.afssurani.sungka.Store;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class StoreTest
{

    // testing the set shell count method
    @Test
    public void testSetShellCount() throws NoSuchFieldException,IllegalAccessException
    {
        //given
        final  Store store = new Store();
        //when
        store.setShellCount(5);

        //then
        final Field field = store.getClass().getDeclaredField("shellCount");
        field.setAccessible(true);
        assertEquals("Set Test failed",field.get(store),5);
    }

    //testing the get shell count method
    @Test
    public void testGetShellCount() throws NoSuchFieldException, IllegalAccessException
    {
        //given
        final  Store store = new Store();
        final Field field = store.getClass().getDeclaredField("shellCount");
        field.setAccessible(true);
        field.set(store, 14);

        //when
        final int result = store.getShellCount();

        //then
        assertEquals("Get Test failed",result,14);
    }

    //testing the addShell method
    public void testAddShell()
    {
        //given
        final  Store store = new Store();
        //when
        int initialShells = 5;
        store.setShellCount(initialShells);
        store.addShell();

        //then
        assertEquals("add shell method failed", 6,store.getShellCount());
    }


}
