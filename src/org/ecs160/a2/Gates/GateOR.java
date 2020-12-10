package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

public class GateOR extends LogicComponent {
    public GateOR(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateOr";}

    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        boolean y = GetInputStates().get(1);
        x |= y;
        System.out.printf("Or of X: %s%n", x);
    }
}

