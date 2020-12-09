package org.ecs160.a2.Objects.Gate;

import org.ecs160.a2.LogicComponent;

public class GateXNOR extends LogicComponent {
    public GateXNOR(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateXNOR";}

    @Override
    public void UpdateOutput(){
        boolean x = Inputs.get(0).getState();
        boolean y = Inputs.get(1).getState();
        if(x == false && y == false || x == true && y == true){
            x = true;
        }
        else if(x == true && y == false || x == false && y == true){
            x = false;
        }
        System.out.println("XNOR:  " + x);

    }
}
