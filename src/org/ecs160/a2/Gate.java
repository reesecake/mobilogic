package org.ecs160.a2;

import com.codename1.ui.Component;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

public class Gate extends Component {

    public Gate(GateType type) {
        super();
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
    }

    // We want each gate to be 100x100 (same size as cells)
    @Override
    public Dimension calcPreferredSize() {
        return new Dimension(100,100);
    }
}
