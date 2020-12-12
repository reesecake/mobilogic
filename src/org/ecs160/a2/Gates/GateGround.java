package org.ecs160.a2.Gates;
import org.ecs160.a2.LogicComponent;


public class GateGround extends LogicComponent {
    public GateGround() {
        super(0);
    }

    public  String getType() {return "GateGround";}

    @Override
    public void UpdateOutput(){
        SetOutput(false);
    }
}
