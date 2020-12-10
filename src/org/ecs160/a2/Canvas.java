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

    private Circuit circuit;

    public Canvas() {
        super();
        circuit = new Circuit();

        getStyle().setBgTransparency(255);
        getStyle().setBgColor(0x99CCCC);
        setScrollableX(true);
        setScrollableY(true);
        setDropTarget(true);

        // Set the viewport to the middle of the grid
        setScrollX(5000);
        setScrollY(5000);

        createCells();

        wires = new ArrayList<>();

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
                // Are we trying to connect to the same component? Don't do that.
                if(wireExists(selectedWireGate, (Gate) dest)) {
                    System.out.println("Can't connect to same gate!");
                    return;
                }

                // Create wire connection between two components here.
                // How do we determine if input or output?

                selectedWireGate.addOutput((Gate) dest);
                ((Gate) dest).addInput(selectedWireGate);

                Wire newWire = new Wire(selectedWireGate, (Gate) dest);
                circuit.AddComponent(newWire.getLogicalComponent());

                wires.add(newWire);
                circuit.Update();

                repaint();
                holdingWire = false;
                System.out.println("Add Wire To: "+((Gate) dest).type.toString());
            } else {
                // Pick up the wire.
                selectedWireGate = (Gate) dest;
                holdingWire = true;
                System.out.println("Add Wire From: "+selectedWireGate.type.toString());
            }
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
