package org.ecs160.a2;
import com.codename1.io.Util;
import org.ecs160.a2.Gates.*;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;

import java.io.*;

public class Gate extends Component {
    private UITimer timer;
    protected GateType type;
    private LogicComponent component;
    private boolean on_off; // for lamp

    public Gate(GateType newType) {
        super();
        type = newType;
        // Determine image/logic on gate type

        String img = "power.jpg";
        switch (type) {
            case OR:
                img = "or.png";
                component = new GateOR(2);
                break;
            case AND:
                img = "and.png";
                component = new GateAND(2);
                break;
            case XOR:
                img = "xor.png";
                component = new GateXOR(2);
                break;
            case NOT:
                img = "not.png";
                component = new GateNOT(1);
                break;
            case NOR:
                img = "nor.png";
                component = new GateNOR(2);
                break;
            case NAND:
                img = "nand.png";
                component = new GateNAND(2);
                break;
            case XNOR:
                img = "xnor.jpg";
                component = new GateXNOR(2);
                break;
            case POWER:
                img = "power.jpg";
                component = new GatePower();
                break;
            case GROUND:
                img = "ground.jpg";
                component = new GateGround();
                break;
            case LAMP:
                img = "lamp.jpg";
                component = new GateLamp();
                on_off = false;
                makeLampToggleable();
                break;
        }
        Image im = AppMain.theme.getImage(img);
        getUnselectedStyle().setBgImage(im);
        getUnselectedStyle().setBgTransparency(255);
        getUnselectedStyle().setBgColor(0x0000ff);
        getSelectedStyle().setBgImage(im);
        getSelectedStyle().setBgTransparency(255);
        getSelectedStyle().setBgColor(0x0000ff);
        setDraggable(true);
    }

    public Gate(Gate newGate) {
        super();
        timer = null;
        type = newGate.type;
        on_off = newGate.on_off;
        String img = "power.jpg";
        switch (type) {
            case OR:
                img = "or.png";
                component = new GateOR((GateOR) newGate.component);
                break;
            case AND:
                img = "and.png";
                component = new GateAND((GateAND) newGate.component);
                break;
            case XOR:
                img = "xor.png";
                component = new GateXOR((GateXOR) newGate.component);
                break;
            case NOT:
                img = "not.png";
                component = new GateNOT((GateNOT) newGate.component);
                break;
            case NOR:
                img = "nor.png";
                component = new GateNOR((GateNOR) newGate.component);
                break;
            case NAND:
                img = "nand.png";
                component = new GateNAND((GateNAND) newGate.component);
                break;
            case XNOR:
                img = "xnor.jpg";
                component = new GateXNOR((GateXNOR) newGate.component);
                break;
            case POWER:
                img = "power.jpg";
                component = new GatePower((GatePower) newGate.component);
                break;
            case GROUND:
                img = "ground.jpg";
                component = new GateGround((GateGround) newGate.component);
                break;
            case LAMP:
                img = "lamp.jpg";
                component = new GateLamp((GateLamp) newGate.component);
                on_off = false;
                makeLampToggleable();
                break;
        }
        Image im = AppMain.theme.getImage(img);
        getUnselectedStyle().setBgImage(im);
        getUnselectedStyle().setBgTransparency(255);
        getUnselectedStyle().setBgColor(0x0000ff);
        getSelectedStyle().setBgImage(im);
        getSelectedStyle().setBgTransparency(255);
        getSelectedStyle().setBgColor(0x0000ff);
        setDraggable(true);
    }

    public LogicComponent GetLogicalComponent() {
        return component;
    }

    // We want each gate to be 100x100 (same size as cells)
    @Override
    public Dimension calcPreferredSize() {
        return new Dimension(100,100);
    }

    /* Developers haven't implemented double tap events, one of them made a stack overflow post on how to
     * implement it:
     * https://stackoverflow.com/questions/34648031/how-to-catch-double-click-event-on-the-list-and-on-the-label
     */
    @Override
    public void pointerReleased(int x, int y) {
        super.pointerReleased(x, y);
        if(timer == null) {
            timer = UITimer.timer(300, false, () -> {
                timer = null;
            });
        } else {
            timer.cancel();
            timer = null;
            makeDialog();
        }
    }

    void makeDialog() {
        InteractionDialog dlg = new InteractionDialog(type.toString());
        dlg.setLayout(new BorderLayout());
        dlg.setDisposeWhenPointerOutOfBounds(true);

        Button deleteGate = new Button("Delete");
        deleteGate.addActionListener(evt1 -> {
            int idx = getParent().getComponentIndex(this);
            if (getParent() instanceof Canvas) {
                Canvas canvas = (Canvas) getParent();
                canvas.removeAllWires(this);
                canvas.setHoldingWire(false);
                canvas.createSingleCell(idx);
                remove();
            }
            dlg.dispose();
        });
        dlg.add(BorderLayout.CENTER, deleteGate);
        Button close = new Button("Close");
        close.addActionListener((ee) -> dlg.dispose());
        dlg.addComponent(BorderLayout.SOUTH, close);

        dlg.showPopupDialog(this);
    }

    public void makeLampToggleable() {
        addPointerPressedListener(evt -> {
            on_off = !on_off;
            if (on_off) {
                getUnselectedStyle().setBgImage(AppMain.theme.getImage("lamp_on.jpg"));
                getSelectedStyle().setBgImage(AppMain.theme.getImage("lamp_on.jpg"));
            } else {
                getUnselectedStyle().setBgImage(AppMain.theme.getImage("lamp.jpg"));
                getSelectedStyle().setBgImage(AppMain.theme.getImage("lamp.jpg"));
            }
        });
    }
}
