package org.ecs160.a2.Gates;

import org.ecs160.a2.IO_Component;
import org.ecs160.a2.LogicComponent;

import java.util.ArrayList;

public class Wire_Component extends LogicComponent {
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
    public void DetachWireInput(LogicComponent src){
        //src gate -> dest wire
        src.GetOutput().SetConnectedID(-1);
        this.GetInputs().get(0).SetConnectedID(-1);
        this.SetInput(0,false);
        this.UpdateOutput();
    }
    public void DetachWireOutput(LogicComponent dest){
        //src wire -> dest gate


        //Search input index
        ArrayList<IO_Component> destInputs = dest.GetInputs();
        int index = 0;
        for(; index < destInputs.size(); index++){
            if(destInputs.get(index).GetConnectedID() == this.GetID()){
                break;
            }
        }
        if(index == destInputs.size()) {
            System.out.println("No free input to connect to!");
            return;
        }
        else {
            this.GetOutput().SetConnectedID(-1);
            destInputs.get(index).SetConnectedID(-1);
            dest.SetInput(index, false);
        }
    }

    public Wire_Component() {
        super(1);
    }

    public  String getType() {return "Wire_Component";}

    @Override
    public void UpdateOutput(){
        boolean x = GetInputStates().get(0);
        SetOutput(x);
    }
}
