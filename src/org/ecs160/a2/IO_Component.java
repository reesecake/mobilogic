package org.ecs160.a2;

import java.io.*;

public class IO_Component implements com.codename1.io.Externalizable {
    private boolean CurrState; // Powered On/Off
    private long ID; // ID of logic component holding it
    private long ConnectedID; // ID of logic component attached externally (-1 if NULL)

    public IO_Component(long ID) {
        this.ID = ID;
        CurrState = false;
        ConnectedID = -1; // Negative represents Not Connected
    }

    public IO_Component() {

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

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        out.writeBoolean(CurrState);
        out.writeLong(ID);
        out.writeLong(ConnectedID);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        CurrState = in.readBoolean();
        ID = in.readLong();
        ConnectedID = in.readLong();
    }

    @Override
    public String getObjectId() {
        return "IO_Component";
    }
}
