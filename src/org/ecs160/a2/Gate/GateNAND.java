package org.ecs160.a2.Objects.Gate;

import org.ecs160.a2.IO_Component;
import org.ecs160.a2.LogicComponent;

public class GateNAND extends LogicComponent {
    public GateNAND(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateNAND";}

    @Override
    public void UpdateOutput(){
        boolean x = Inputs.get(0).getState();
        boolean y = Inputs.get(1).getState();
        x &= y;
        x = !x;
        System.out.println("NAND:  " + x);

    }
}
