package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GateXOR extends LogicComponent implements com.codename1.io.Externalizable {
    public GateXOR(int numInputs) {
        super(numInputs);
    }

    public  String getType() {return "GateXOR";}

    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        boolean y = GetInputStates().get(1);

        if(x == false &&  y == false || y == true && x == true){
            x = false;
        }
        else if(x == true && y == false || x == false && y == true){
            x = true;
        }
        SetOutput(x);
        System.out.println("XOR:  " + x);

    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getObjectId() {
        return "GateXOR";
    }


}
