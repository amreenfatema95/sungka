package com.afssurani.sungka;

/**
 * Created by afssurani on 24/10/2015.
 */
// This class defines one tray.
public class Tray extends Store
{
    //used to store if the tray has been clicked on by the respective player
    private boolean _clicked;

    //Puts 7 shells in a tray.
    public Tray()
    {
        _clicked = false;
        shellCount = 7;
    }

    // removes a shell from the tray
    public void removeShell()
    {
        shellCount--;
    }

    //sets the clicked variable to true
    public void clickedOn(){
        _clicked = true;
    }

    public boolean clicked(){
        return _clicked;
    }

    // checks if the tray is empty
    public boolean isEmpty()
    {
        if (shellCount==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
