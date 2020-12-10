package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

public class GateNOT extends LogicComponent {
    public GateNOT(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateNOT";}


    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        x = !x;
        System.out.println("NOT:  " + x);

    }
}
