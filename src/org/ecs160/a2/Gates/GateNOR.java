package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

public class GateNOR extends LogicComponent {
    public GateNOR(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateNOR";}

    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        boolean y = GetInputStates().get(1);
        x |= y;
        x = !x;
        SetOutput(x);
        System.out.println("NOR:  " + x);
    }

}
