package org.ecs160.a2.Gates;
import org.ecs160.a2.LogicComponent;

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
