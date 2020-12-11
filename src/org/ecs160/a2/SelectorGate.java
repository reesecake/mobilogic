package org.ecs160.a2;

import com.codename1.io.Util;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.util.UITimer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

    @Override
    public void makeLampToggleable() {}

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getObjectId() {
        return "SelectorGate";
    }
}
