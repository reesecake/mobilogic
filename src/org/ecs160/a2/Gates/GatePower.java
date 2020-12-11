package org.ecs160.a2.Gates;
import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GatePower extends LogicComponent implements com.codename1.io.Externalizable {
    public GatePower() {
        super(0);
    }
    public  String getType() {return "GatePower";}

    @Override
    public void UpdateOutput(){
        SetOutput(true);
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getObjectId() {
        return "GatePower";
    }
}
