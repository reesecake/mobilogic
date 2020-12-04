package org.ecs160.a2;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

public class SelectorPanel extends Form {

    public SelectorPanel() {
        super(BoxLayout.y());
        setScrollable(false);

        getStyle().setBgTransparency(255);
        getStyle().setBgColor(0xd3d3d3);

        addComponent(new Label("GATE SELECTOR"));

        addComponent(new GateList());
    }

    private static class GateList extends Container {
        public GateList() {
            super(BoxLayout.x());
            setScrollableX(true);

            addComponent(new SelectorGate(GateType.POWER));
            addComponent(new SelectorGate(GateType.AND));
            addComponent(new SelectorGate(GateType.OR));
            addComponent(new SelectorGate(GateType.XOR));
            addComponent(new SelectorGate(GateType.NOT));
            addComponent(new SelectorGate(GateType.NAND));
            addComponent(new SelectorGate(GateType.NOR));
            addComponent(new SelectorGate(GateType.XNOR));
        }
    }
}
