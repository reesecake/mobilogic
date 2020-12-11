package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GateLamp extends LogicComponent  {
    public GateLamp() {
        super(0);
    }

    public GateLamp(GateLamp newGateLamp) {
        super(newGateLamp);
    }

    public  String getType() {return "GateLamp";}

    @Override
    public void UpdateOutput(){
        SetOutput(!GetOutputState());
    }
}
