package org.ecs160.a2.Gates;

import org.ecs160.a2.Canvas;
import org.ecs160.a2.Gate;
import org.ecs160.a2.LogicComponent;

public class GateLamp extends LogicComponent {
    public GateLamp() {
        super(1);
    }
    public  String getType() {return "GateLamp";}

    @Override
    public void UpdateOutput(){
        if(GetInputs().get(0).GetConnectedID() == -1){
            SetInput(0,false);
        }
        SetOutput(GetInputStates().get(0));
    }
}
