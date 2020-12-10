package org.ecs160.a2;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;

import java.util.ArrayList;

public class Gate extends Component {
    private UITimer timer;
    protected GateType type;

    public Gate(GateType newType) {
        super();
        type = newType;
        // Determine image/logic on gate type
        String img = "power.jpg";
        switch (type) {
            case OR:
                img = "or.png";
                break;
            case AND:
                img = "and.png";
                break;
            case XOR:
                img = "xor.png";
                break;
            case NOT:
                img = "not.png";
                break;
            case NOR:
                img = "nor.png";
                break;
            case NAND:
                img = "nand.png";
                break;
            case XNOR:
                img = "xnor.jpg";
                break;
            case POWER:
                img = "power.jpg";
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

}
