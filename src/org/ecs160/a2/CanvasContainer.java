package org.ecs160.a2;

import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;

/** CanvasContainer
 *  The properly sized container that holds the Canvas within a normal size.
 *  Because the Canvas class spawns a massive component, this container is necessary to
 *  insert the canvas into a form while constraining the viewport.
 */
public class CanvasContainer extends Form {
    private int width;
    private int height;
    private Canvas canvas;

    public CanvasContainer() {
        super(BoxLayout.y());
        setScrollableX(false);
        setScrollableY(false);
        width = Display.getInstance().getDisplayWidth();
        // Height of the container is 75% of the display height, with the rest reserved for the gate selection view
        // changed to 76% because otherwise the CanvasContainer overlaps SelectorPanel
        height = Math.toIntExact(Math.round(Display.getInstance().getDisplayHeight() * 0.76));
        canvas = new Canvas();
        add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas newCanvas) {
        removeComponent(canvas);
        canvas = newCanvas;
        add(canvas);
        revalidate();
    }

    public void addNewGate(GateType type) {
        // Canvas Section
        Component dest = canvas.getAddLocation();
        int destIdx = canvas.getComponentIndex(dest);

        if(dest instanceof Gate) return;

        // Remove Existing Component/Gate at destIdx. Fix shift-right bug.
        canvas.deleteComponent(dest);

        // Instantiate new gate
        Gate newGate = new Gate(type);
        canvas.addGate(destIdx, newGate);
        
        System.out.println("GATE ADDED! Type = " + type.name());
    }

    public void clearCanvas() {
        removeComponent(canvas);
        canvas = new Canvas();
        add(canvas);
        revalidate();
    }

    @Override
    public Dimension calcPreferredSize() {
        return new Dimension(width,height);
    }

}
