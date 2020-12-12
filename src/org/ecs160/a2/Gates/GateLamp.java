package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;


public class GateLamp extends LogicComponent  {
    public GateLamp() {
        super(1);
    }

    public  String getType() {return "GateLamp";}

    @Override
    public void UpdateOutput(){
        SetOutput(!GetOutputState());
    }
}
