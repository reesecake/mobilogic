package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GateNAND extends LogicComponent implements com.codename1.io.Externalizable {
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
    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getObjectId() {
        return "GateNAND";
    }
}
