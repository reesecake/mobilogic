package org.ecs160.a2;

public class Wire {
    private Gate gate1;
    private Gate gate2;
    private Boolean powered;

    public Wire(Gate first, Gate second) {
        gate1 = first;
        gate2 = second;
        powered = false;
    }

    public Boolean isConnected(Gate g1, Gate g2) {
        return (g1 == gate1 && g2 == gate2) || (g1 == gate2 && g2 == gate1);
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
}