package org.ecs160.a2;

import com.codename1.components.InteractionDialog;
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

    @Override
  
    public void makeDialog() {
        InteractionDialog dlg = new InteractionDialog(type.toString());
        dlg.setDisposeWhenPointerOutOfBounds(true);

        dlg.showPopupDialog(this);
    }
  
    // can't toggle lamp in list
    public void makeLampToggleable() {}
}
