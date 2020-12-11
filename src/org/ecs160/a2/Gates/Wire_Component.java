package org.ecs160.a2.Gates;
import com.codename1.io.Util;
import org.ecs160.a2.IO_Component;
import org.ecs160.a2.LogicComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Wire_Component extends LogicComponent implements com.codename1.io.Externalizable {
    public void AttachWireInput(LogicComponent src){
        //src gate -> dest wire
        src.ConnectToGate(this,0);
    }
    public void AttachWireOutput(LogicComponent dest){
        //src wire -> dest gate
        //Search first available input index
        int index = dest.GetIndexFirstAvailableInput();
        if(index!= -1)
            this.ConnectToGate(dest, index);
        else
            System.out.println("No free input to connect to!");
    }

    public Wire_Component() {
        super(1);
    }

    public Wire_Component(Wire_Component newWire_Component) {
        super(newWire_Component);
    }

    public  String getType() {return "Wire_Component";}

    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        SetOutput(x);
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getObjectId() {
        return "Wire_Component";
    }
}
