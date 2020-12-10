package org.ecs160.a2;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;

import java.util.ArrayList;


/** Canvas
 *  The class that generates the area to place gates and wires, arranged in a Grid pattern.
 *  By default the canvas is 10000x10000px large and supports panning around.
 */
public class Canvas extends Container {
    private Boolean holdingWire = false;
    private Gate selectedWireGate;
    private ArrayList<Wire> wires;

    public Canvas() {
        super();
        getStyle().setBgTransparency(255);
        getStyle().setBgColor(0x99CCCC);
        setScrollableX(true);
        setScrollableY(true);
        setDropTarget(true);

        // Set the viewport to the middle of the grid
        setScrollX(5000);
        setScrollY(5000);

        addGate(0, new Gate(GateType.POWER));
        addGate(1, new Gate(GateType.AND));
        addGate(2, new Gate(GateType.OR));
        addGate(3, new Gate(GateType.XOR));
        addGate(4, new Gate(GateType.NOT));
        addGate(5, new Gate(GateType.NAND));
        addGate(6, new Gate(GateType.NOR));
        addGate(7, new Gate(GateType.XNOR));

        createCells();

        wires = new ArrayList<>();
    }

    public void setHoldingWire(Boolean wire) {
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
            if(!(dest instanceof Gate)) return;
            if(holdingWire) {
                // Are we trying to connect to the same component? Don't do that.
                if(wireExists(selectedWireGate, (Gate) dest)) return;

                // Create wire connection between two components here.
                // How do we determine if input or output?
                selectedWireGate.addOutput((Gate) dest);
                ((Gate) dest).addInput(selectedWireGate);
                wires.add(new Wire(selectedWireGate, (Gate) dest));
                repaint();
                holdingWire = false;
            } else {
                // Pick up the wire.
                selectedWireGate = (Gate) dest;
                holdingWire = true;
            }
        });
        addComponent(idx, gate);
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

    private Boolean wireExists(Gate i, Gate j) {
        for(Wire wire : wires) {
            if(wire.isConnected(i, j)) return true;
        }
        return false;
    }

    public void removeAllWires(Gate x) {
        ArrayList<Wire> newWires = new ArrayList<>();

        // Filtering...the Java way.
        for(Wire wire : wires) {
            if(!wire.isConnected(x)) newWires.add(wire);
        }
        wires = newWires;
    }

    /** CanvasCell
     *  The individual cells that make up the canvas grid.
     */
    private class CanvasCell extends Component {
        public CanvasCell() {
            super();
            getUnselectedStyle().setBgTransparency(255);
            getUnselectedStyle().setBgColor(0xffffff);
            getSelectedStyle().setBgTransparency(255);
            getSelectedStyle().setBgColor(0xffffff);
        }

        // We want each cell to be 100x100
        @Override
        public Dimension calcPreferredSize() {
            return new Dimension(100,100);
        }
    }
}
