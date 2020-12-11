package org.ecs160.a2;

import com.codename1.io.Util;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.Border;

import java.io.*;
import java.util.ArrayList;


/** Canvas
 *  The class that generates the area to place gates and wires, arranged in a Grid pattern.
 *  By default the canvas is 10000x10000px large and supports panning around.
 */
public class Canvas extends Container implements com.codename1.io.Externalizable {
    private Boolean holdingWire = false;
    private Gate selectedWireGate;
    private ArrayList<Wire> wires;
    private String name;

    private Circuit circuit;

    public Canvas() {
        super();
        circuit = new Circuit();

        getStyle().setBgTransparency(255);
        getStyle().setBgColor(0xffffff);
        setScrollableX(true);
        setScrollableY(true);
        setDropTarget(true);

        // Set the viewport to the middle of the grid
        setScrollX(5000);
        setScrollY(5000);

        createCells();

        name = "";

        wires = new ArrayList<>();
    }

    public Canvas(Canvas newCanvas) {
        super();
        circuit = new Circuit(newCanvas.circuit);

        getStyle().setBgTransparency(255);
        getStyle().setBgColor(0xffffff);
        setScrollableX(true);
        setScrollableY(true);
        setDropTarget(true);

        // Set the viewport to the middle of the grid
        setScrollX(5000);
        setScrollY(5000);

        createCells();

        name = new String(newCanvas.name);

        wires = new ArrayList<Wire>();
        for (Wire w : newCanvas.wires)
            wires.add(new Wire(w));
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        wires.forEach(wire -> {
            int[] coords = wire.getCoords();
            if(wire.getPower()) {
                g.setColor(0x00ff00);
            } else {
                g.setColor(0x0000ff);
            }
            g.drawLine(coords[0], coords[1], coords[2], coords[3]);
        });
    }

    @Override
    public Dimension calcPreferredSize() {
        return new Dimension(10000,10000);
    }

    @Override
    public void drop(Component dragged, int x, int y) {
        int i = getComponentIndex(dragged);
        Component dest = getComponentAt(x, y);
        int destIdx = getComponentIndex(dest);
        if(destIdx > -1 && dest != dragged && dest instanceof CanvasCell) {
            removeComponent(dragged);
            removeComponent(dest);
            addComponent(destIdx, dragged);
            addComponent(i, new CanvasCell());
        }
        animateLayout(1);
    }

    public void setHoldingWire(Boolean wire) {
        System.out.println("Wire holding: "+ wire);
        holdingWire = wire;
    }

    public Component getAddLocation() {
        // x y coordinates are located at top-left of physical device
        // adjust x y to center of display
        return getClosestComponentTo(getX() + 600, getY() + 1100);
    }

    public void addGate(int idx, Gate gate) {
        gate.addLongPressListener(evt -> {
            Component dest = getComponentAt(evt.getX(), evt.getY());
            if(!(dest instanceof Gate)) {
                holdingWire = false;
                return;
            }
            if(holdingWire) {
                holdingWire = false;

                selectedWireGate.getUnselectedStyle().setBorder(null);
                selectedWireGate.getSelectedStyle().setBorder(null);

                // Remove wire if attempting to wire components already together
                if(wireExists(selectedWireGate, (Gate) dest)) {
                    removeWire(selectedWireGate, (Gate) dest);
                    repaint();
                    return;
                };

                Wire newWire = new Wire(selectedWireGate, (Gate) dest);
                circuit.AddComponent(newWire.getLogicalComponent());

                wires.add(newWire);
                circuit.Update();
            } else {
                // Pick up the wire.
                selectedWireGate = (Gate) dest;
                selectedWireGate.getUnselectedStyle().setBorder(Border.createLineBorder(2));
                selectedWireGate.getSelectedStyle().setBorder(Border.createLineBorder(2));
                holdingWire = true;
                System.out.println("Add Wire From: "+selectedWireGate.type.toString());
            }
            repaint();
        });
        // Canvas Visual Element
        addComponent(idx, gate);
        // Circuit Logical Element
        LogicComponent component = gate.GetLogicalComponent();
        circuit.AddComponent(component);
    }

    // Create the grid of cells
    private void createCells() {
        for (int i = 0; i < 10000; i++) {
            addComponent(new CanvasCell());
        }
    }

    public void createSingleCell(int index) {
        addComponent(index, new CanvasCell());
    }

    private Boolean wireExists(Gate i, Gate j) {
        for(Wire wire : wires) {
            if(wire.isConnected(i, j)) return true;
        }
        return false;
    }

    public void removeWire(Gate i, Gate j) {
        ArrayList<Wire> newWires = new ArrayList<>();

        for(Wire wire : wires) {
            if(!wire.isConnected(i, j)) newWires.add(wire);
        }

        wires = newWires;
    }

    public void removeAllWires(Gate x) {
        ArrayList<Wire> newWires = new ArrayList<>();

        // Filtering...the Java way.
        for (Wire wire : wires) {
            if (!wire.isConnected(x)) newWires.add(wire);
        }
        wires = newWires;
    }
    public void deleteComponent(Component component) {
        removeComponent(component);
        if(component instanceof Gate){
            LogicComponent temp = ((Gate) component).GetLogicalComponent();
            circuit.RemoveComponent(temp.GetID());
        }
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeObject(wires, out);
        Util.writeUTF(name, out);
        Util.writeObject(circuit, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        wires = (ArrayList<Wire>) Util.readObject(in);
        name =  Util.readUTF(in);
        circuit = (Circuit) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "Canvas";
    }

    /** CanvasCell
     *  The individual cells that make up the canvas grid.
     */
    private class CanvasCell extends Component implements com.codename1.io.Externalizable {
        public CanvasCell() {
            super();
            getUnselectedStyle().setBgTransparency(255);
            getUnselectedStyle().setBgColor(0xffffff);
            getSelectedStyle().setBgTransparency(255);
            getSelectedStyle().setBgColor(0xffffff);
        }

        // We want each cell to be 100x100
        public Dimension calcPreferredSize() {
            return new Dimension(100,100);
        }

        @Override
        public int getVersion() {
            return 0;
        }

        public void externalize(DataOutputStream out) throws IOException {

        }

        public void internalize(int version, DataInputStream in) throws IOException {

        }
        @Override
        public String getObjectId() {
            return "CanvasCell";
        }
    }
}
