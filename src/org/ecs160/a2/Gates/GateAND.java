package org.ecs160.a2.Gates;
import org.ecs160.a2.LogicComponent;


public class GateAND extends LogicComponent {

    public GateAND(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateAND";}

    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        boolean y = GetInputStates().get(1);
        x &= y;
        SetOutput(x);
        System.out.printf("AND of X: %s%n", x);

    }
}
