package org.ecs160.a2.Objects.Gate;

import org.ecs160.a2.LogicComponent;

public class GateOR extends LogicComponent {
    public GateOR(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateOr";}

    @Override
    public void UpdateOutput(){
        boolean x = Inputs.get(0).getState();
        boolean y = Inputs.get(1).getState();
        x |= y;
        System.out.printf("Or of X: %s%n", x);
    }
}

