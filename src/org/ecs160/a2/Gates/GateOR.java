package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GateOR extends LogicComponent {
    public GateOR(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateOr";}

    public GateOR(GateOR newGateOR) {
        super(newGateOR);
    }

    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        boolean y = GetInputStates().get(1);
        x |= y;
        SetOutput(x);
        System.out.printf("Or of X: %s%n", x);
    }
}

