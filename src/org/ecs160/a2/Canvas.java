package org.ecs160.a2;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.Border;

import java.util.ArrayList;

import java.util.EventListener;

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
        circuit = new Circuit(this);

        getStyle().setBgTransparency(255);
        getStyle().setBgColor(0xffffff);
        setScrollableX(true);
        setScrollableY(true);
        setDropTarget(true);

        // Set the viewport to the middle of the grid
        setScrollX(5000);
        setScrollY(5000);

        createCells();

        wires = new ArrayList<>();

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
            if(!wire.isConnected(i, j)) {
                newWires.add(wire);
            }else{
                // disconnect logical wire from circuit
                wire.disconnectGates();
                circuit.RemoveComponent(wire.getLogicalComponent().GetID());
                circuit.Update();
            }
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
