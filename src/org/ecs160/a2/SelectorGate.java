package org.ecs160.a2;

import com.codename1.ui.geom.Dimension;

public class SelectorGate extends Gate {

//    GateType type;

    public SelectorGate(GateType newType) {
        super(newType);
        setDraggable(false);

        type = newType;

        getStyle().setMargin(10, 10, 10, 10);
    }

    @Override
    public Dimension calcPreferredSize() { return new Dimension(150,150); }
}
