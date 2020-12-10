package org.ecs160.a2;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

public class SelectorPanel extends Container {

    private static CanvasContainer canvasContainer;

    private static Button clear;
    private static Button delete;

    public SelectorPanel(CanvasContainer canvasCon) {
        super(BoxLayout.y());
        canvasContainer = canvasCon;

        clear = new Button("Clear");
        delete = new Button("Delete");
        clear.addActionListener((evt -> canvasContainer.clearCanvas()));
        // TODO use delete for sub circuits?

        getStyle().setBgTransparency(255);
        getStyle().setBgColor(0xd3d3d3);

        addComponent(new PanelToolBar());

        GateList gateList = new GateList();
        addComponent(gateList);

        addGateListListeners(gateList);
    }

    private static class PanelToolBar extends Container {
        public PanelToolBar() {
            super(BoxLayout.x());

            addComponent(new Label("GATE SELECTOR"));
            addComponent(clear);
            addComponent(delete);
        }
    }

    private static class GateList extends Container {
        public GateList() {
            super(BoxLayout.x());
            setScrollableX(true);

            SelectorGate selPower = new SelectorGate(GateType.POWER);
            SelectorGate selGround = new SelectorGate(GateType.GROUND);
            addComponent(selPower);
            addComponent(selGround);
            addComponent(new SelectorGate(GateType.AND));
            addComponent(new SelectorGate(GateType.OR));
            addComponent(new SelectorGate(GateType.XOR));
            addComponent(new SelectorGate(GateType.NOT));
            addComponent(new SelectorGate(GateType.NAND));
            addComponent(new SelectorGate(GateType.NOR));
            addComponent(new SelectorGate(GateType.XNOR));
        }
    }

    private void addGateListListeners(GateList gateList) {
        for (Component gate : gateList) {
            if (gate instanceof SelectorGate) {
                gate.addPointerPressedListener(evt -> canvasContainer.addNewGate(((SelectorGate) gate).type));
            }
        }
    }
}
