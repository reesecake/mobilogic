package org.ecs160.a2;

import java.io.*;

public class IO_Component {
    private boolean CurrState; // Powered On/Off
    private long ID; // ID of logic component holding it
    private long ConnectedID; // ID of logic component attached externally (-1 if NULL)

    public IO_Component(long ID) {
        this.ID = ID;
        CurrState = false;
        ConnectedID = -1; // Negative represents Not Connected
    }

    public IO_Component(IO_Component newIO_Component) {
        CurrState = newIO_Component.CurrState;
        ID = newIO_Component.ID;
        ConnectedID = newIO_Component.ConnectedID;
    }

    public boolean getState() {
        return CurrState;
    }

    public void setState(boolean newState) {
        CurrState = newState;
    }

    public boolean isConnected(){
        if(ConnectedID != -1){
            return true;
        }
        // Connected to NULL
        return false;
    }

    public long GetConnectedID(){
        return ConnectedID;
    }
    public void SetConnectedID(long ID){
        ConnectedID = ID;
    }
    /* DEBUG */
    public void logState() {
        if (CurrState) {
            System.out.println("Current State: ON");
        } else {
            System.out.println("Current State: OFF");
        }
    }
}
