package org.ecs160.a2;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;

import java.util.ArrayList;

public class Gate extends Component {
    private ArrayList<Gate> connections;
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
        getStyle().setBgImage(im);
        getStyle().setBgTransparency(255);
        getStyle().setBgColor(0x0000ff);
        setDraggable(true);

        connections = new ArrayList<>();

        makeDialog();
    }

    // We want each gate to be 100x100 (same size as cells)
    @Override
    public Dimension calcPreferredSize() {
        return new Dimension(100,100);
    }

    // addConnection - Logic for determining output can go here.
    public void addConnection(Gate gate) {
        connections.add(gate);
    }

    public void removeConnection(Gate gate) {

    }

    void makeDialog() {
        addLongPressListener(evt -> {
            InteractionDialog dlg = new InteractionDialog(type.toString());
            dlg.setLayout(new BorderLayout());
            dlg.setDisposeWhenPointerOutOfBounds(true);

            Button deleteGate = new Button("Delete");
            deleteGate.addActionListener(evt1 -> {
                int idx = getParent().getComponentIndex(this);
                if (getParent() instanceof Canvas) {
                    ((Canvas) getParent()).createSingleCell(idx);
                    remove();
                }
                dlg.dispose();
            });
            dlg.add(BorderLayout.CENTER, deleteGate);
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);

            dlg.showPopupDialog(this);
        });
    }

}
