package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

public class GateSwitch extends LogicComponent {
    public GateSwitch() {
        super(0);
    }
    public  String getType() {return "GateSwitch";}

    @Override
    public void UpdateOutput(){
        SetOutput(!GetOutputState());
    }
}
