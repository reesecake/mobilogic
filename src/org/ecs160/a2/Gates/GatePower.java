package org.ecs160.a2.Gates;
import org.ecs160.a2.LogicComponent;

public class GatePower extends LogicComponent {
    public GatePower() {
        super(0);
    }
    public  String getType() {return "GatePower";}

    @Override
    public void UpdateOutput(){
        SetOutput(true);
    }
}
