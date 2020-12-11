package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GateNOT extends LogicComponent implements com.codename1.io.Externalizable {
    public GateNOT(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateNOT";}


    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        x = !x;
        SetOutput(x);
        System.out.println("NOT:  " + x);
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getObjectId() {
        return "GateNOT";
    }
}
