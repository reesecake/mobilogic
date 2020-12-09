package org.ecs160.a2.Objects.Gate;

import org.ecs160.a2.LogicComponent;

public class GateXOR extends LogicComponent {
    public GateXOR(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateXOR";}

    @Override
    public void UpdateOutput(){
        boolean x = Inputs.get(0).getState();
        boolean y = Inputs.get(1).getState();

        if(x == false &&  y == false || y == true && x == true){
            x = false;
        }
        else if(x == true && y == false || x == false && y == true){
            x = true;
        }
        System.out.println("XOR:  " + x);

    }


}
