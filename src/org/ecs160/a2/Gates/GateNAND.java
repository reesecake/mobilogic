package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

public class GateNAND extends LogicComponent {
    public GateNAND(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateNAND";}

    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        boolean y = GetInputStates().get(1);
        x &= y;
        x = !x;
        SetOutput(x);
        System.out.println("NAND:  " + x);

    }
}
