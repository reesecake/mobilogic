package org.ecs160.a2;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.geom.Dimension;


/** Canvas
 *  The class that generates the area to place gates and wires, arranged in a Grid pattern.
 *  By default the canvas is 10000x10000px large and supports panning around.
 */
public class Canvas extends Container {
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

        addComponent(0, new Gate(GateType.POWER));
        addComponent(1, new Gate(GateType.AND));
        addComponent(2, new Gate(GateType.OR));
        addComponent(3, new Gate(GateType.XOR));
        addComponent(4, new Gate(GateType.NOT));
        addComponent(5, new Gate(GateType.NAND));
        addComponent(6, new Gate(GateType.NOR));
        addComponent(7, new Gate(GateType.XNOR));

        createCells();
    }

    // Create the grid of cells
    private void createCells() {
        for (int i = 0; i < 10000; i++) {
            addComponent(new CanvasCell());
        }
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

    /** CanvasCell
     *  The individual cells that make up the canvas grid.
     */
    private class CanvasCell extends Component {
        private Boolean isPressed = false;

        public CanvasCell() {
            super();
            getStyle().setBgTransparency(255);
            getStyle().setBgColor(0xffffff);

            // Demo for turning background black on long press
            addLongPressListener(evt -> {
                if (!isPressed) {
                    getUnselectedStyle().setBgColor(0x000000);
                } else {
                    getUnselectedStyle().setBgColor(0xffffff);
                }
                repaint();
                isPressed = !isPressed;
            });
        }

        // We want each cell to be 100x100
        @Override
        public Dimension calcPreferredSize() {
            return new Dimension(100,100);
        }
    }
}
