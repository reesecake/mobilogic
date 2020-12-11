package org.ecs160.a2;

import org.ecs160.a2.Gates.*;

import java.util.ArrayList;

public class Wire {
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

    public Boolean isConnected(Gate g1, Gate g2) {
        return (g1 == gate1 && g2 == gate2) || (g1 == gate2 && g2 == gate1);
    }

    public Boolean isConnected(Gate g) {
        return g == gate1 || g == gate2;
    }

    public Boolean getPower() {
        return powered;
    }

    public int[] getCoords() {
        int[] coords = new int[4];
        coords[0] = gate1.getX();
        coords[1] = gate1.getY();
        coords[2] = gate2.getX();
        coords[3] = gate2.getY();

        return coords;
    }

    public void connectGates() {
        // g1 = src Output
        // g2 = dest Input (first available)
        LogicComponent src = gate1.GetLogicalComponent();
        LogicComponent dest = gate2.GetLogicalComponent();

        circuitWire.AttachWireInput(src);
        circuitWire.AttachWireOutput(dest);

        circuitWire.UpdateOutput();
        powered = circuitWire.GetOutputState();
    }

    public LogicComponent disconnectGates() {
        // g1 = src Output
        // g2 = dest Input (first available)
        LogicComponent src = gate1.GetLogicalComponent();
        LogicComponent dest = gate2.GetLogicalComponent();
        circuitWire.DetachWireInput(src);
        circuitWire.DetachWireOutput(dest);

        return circuitWire;
    }

    public void update() {
        powered = circuitWire.GetOutputState();
    }

    public LogicComponent getLogicalComponent() {
        return circuitWire;
    }
}