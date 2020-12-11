package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GateXNOR extends LogicComponent implements com.codename1.io.Externalizable {
    public GateXNOR(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateXNOR";}

    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        boolean y = GetInputStates().get(1);
        if(x == false && y == false || x == true && y == true){
            x = true;
        }
        else if(x == true && y == false || x == false && y == true){
            x = false;
        }
        SetOutput(x);
        System.out.println("XNOR:  " + x);

    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getObjectId() {
        return "GateXNOR";
    }
}
