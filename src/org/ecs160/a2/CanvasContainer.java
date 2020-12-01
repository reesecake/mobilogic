package org.ecs160.a2;

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
        height = Math.toIntExact(Math.round(Display.getInstance().getDisplayHeight() * 0.75));
        canvas = new Canvas();
        add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public Dimension calcPreferredSize() {
        return new Dimension(width,height);
    }

}