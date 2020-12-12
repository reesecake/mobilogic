package org.ecs160.a2.Gates;

import org.ecs160.a2.LogicComponent;

public class GateSwitch extends LogicComponent {
    public GateSwitch() {
        super(1);
    }
    public  String getType() {return "GateSwitch";}

    public void ToggleInput(boolean state){
        this.SetInput(0, state);
        UpdateOutput();

    }

    @Override
    public void UpdateOutput(){
        SetOutput(this.GetInputStates().get(0));
    }
}
