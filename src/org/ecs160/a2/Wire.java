package org.ecs160.a2;

import com.codename1.io.Util;
import org.ecs160.a2.Gates.*;

import java.io.*;
import java.util.ArrayList;

public class Wire implements com.codename1.io.Externalizable {
    private Gate gate1;
    private Gate gate2;
    private Boolean powered;

    private Wire_Component circuitWire; // needed for circuit flow

    public Wire(Gate first, Gate second) {
        gate1 = first;
        gate2 = second;
        powered = false;

        circuitWire = new Wire_Component();
        connectGates();
    }

    public Wire(Wire newWire) {
        gate1 = new Gate(newWire.gate1);
        gate2 = new Gate(newWire.gate2);
        powered = newWire.powered;
        circuitWire = new Wire_Component(newWire.circuitWire);
    }

    public Boolean isConnected(Gate g1, Gate g2) {
        return (g1 == gate1 && g2 == gate2) || (g1 == gate2 && g2 == gate1);
    }

    public Boolean isConnected(Gate g) {
        return g == gate1 || g == gate2;
    }

    public Boolean getPower() {
        return powered;
    }

    public void turnOn() {
        powered = true;
    }

    public void turnOff() {
        powered = false;
    }

    public int[] getCoords() {
        int[] coords = new int[4];
        coords[0] = gate1.getX();
        coords[1] = gate1.getY();
        coords[2] = gate2.getX();
        coords[3] = gate2.getY();

        return coords;
    }
    public void connectGates(){
        // g1 = src Output
        // g2 = dest Input (first available)
        LogicComponent src = gate1.GetLogicalComponent();
        LogicComponent dest = gate2.GetLogicalComponent();

        circuitWire.AttachWireInput(src);
        circuitWire.AttachWireOutput(dest);

        circuitWire.UpdateOutput();
        if(circuitWire.GetOutputState()){
            this.turnOn();
        }else{
            this.turnOff();
        }
    }

    public LogicComponent getLogicalComponent() {
        return circuitWire;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeObject(gate1, out);
        Util.writeObject(gate2, out);
        out.writeBoolean(powered);
        Util.writeObject(circuitWire, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        gate1 = (Gate) Util.readObject(in);
        gate2 = (Gate) Util.readObject(in);
        powered = in.readBoolean();
        circuitWire = (Wire_Component) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "Wire";
    }
}