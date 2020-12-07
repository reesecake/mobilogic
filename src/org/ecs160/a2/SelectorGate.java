package org.ecs160.a2;

import com.codename1.ui.geom.Dimension;

public class SelectorGate extends Gate{
    public SelectorGate(GateType type) {
        super(type);
        setDraggable(false);

        getStyle().setMargin(10, 10, 10, 10);
    }

    @Override
    public Dimension calcPreferredSize() { return new Dimension(150,150); }
}
