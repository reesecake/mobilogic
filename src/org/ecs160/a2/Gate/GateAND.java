package org.ecs160.a2.Objects.Gate;

import org.ecs160.a2.IO_Component;
import org.ecs160.a2.LogicComponent;


public class GateAND extends LogicComponent {

    public GateAND(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateAND";}



    @Override
    public void UpdateOutput(){
        boolean x = Inputs.get(0).getState();
        boolean y = Inputs.get(1).getState();
        x &= y;
        System.out.printf("AND of X: %s%n", x);

    }
}
