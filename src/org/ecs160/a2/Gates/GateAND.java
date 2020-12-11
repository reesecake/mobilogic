package org.ecs160.a2.Gates;
import com.codename1.io.Util;
import com.codename1.ui.util.UITimer;
import org.ecs160.a2.GateType;
import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GateAND extends LogicComponent {

    public GateAND(int numInputs) {
        super(numInputs);
    }

    public GateAND(GateAND newGateAND) {
        super(newGateAND);
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
