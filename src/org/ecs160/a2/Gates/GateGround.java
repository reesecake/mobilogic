package org.ecs160.a2.Gates;
import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GateGround extends LogicComponent {
    public GateGround() {
        super(0);
    }

    public GateGround(GateGround newGateGround) {
        super(newGateGround);
    }

    public  String getType() {return "GateGround";}

    @Override
    public void UpdateOutput(){
        SetOutput(false);
    }
}
