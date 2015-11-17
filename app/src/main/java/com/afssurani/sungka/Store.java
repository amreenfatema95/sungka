package com.afssurani.sungka;

/**
 * Created by afssurani on 24/10/2015.
 */

//This class represents one stores.
public class Store
{
    public int shellCount;



    // counts the number of shells
    public int getShellCount()
    {
        return shellCount;
    }

    // adds a shell
    public void addShell()
    {
        shellCount++;
    }

    // resets  the number of shells
    public void setShellCount(int count)
    {
        shellCount = count;
    }

}
