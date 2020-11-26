package org.ecs160.a2;

import java.util.ArrayList;
import java.util.List;

interface IO{
    public boolean getState();
    public void setState(boolean newState);

    public void logState();
}
class IO_Component implements IO {
    private boolean CurrState; // Powered On/Off
    private boolean Standalone; // Is connected by wire/component

    public boolean getState(){
        return CurrState;
    }
    public void setState(boolean newState) {
        CurrState = newState;
    }
    /* DEBUG */
    public void logState() {
        if(CurrState){
            System.out.println("Current State: ON");
        }else {
            System.out.println("Current State: OFF");
        }
    }
}
public class LogicComponent{
    private int numInputs;
    private List<IO_Component> Inputs;
    private IO_Component Output = new IO_Component();
    private double propagationDelay = 0;

    public LogicComponent(int numInputs) {
        this.numInputs = numInputs;

        Inputs = new ArrayList<>(numInputs);
        for(int i = 0; i < numInputs; i++){
            Inputs.get(i).setState(false);
        }
    }
    public LogicComponent(int numInputs, double propagationDelay) {
        this.numInputs = numInputs;
        this.propagationDelay = propagationDelay;

        Inputs = new ArrayList<>(numInputs);
        for(int i = 0 ; i < numInputs; i++){
            Inputs.add(new IO_Component());
        }
    }

    public boolean GetOutputState(boolean newState){
        return Output.getState();
    }
    public ArrayList<Boolean> GetInputStates(int index, boolean newState){
        ArrayList<Boolean> states = new ArrayList<>();
        for(int i = 0 ; i < numInputs; i++){
            states.add(Inputs.get(i).getState());
        }
        return states;
    }

    public void SetOutput(boolean newState){
        Output.setState(newState);
    }
    public void SetInput(int index, boolean newState){
        Inputs.get(index).setState(newState);
    }

    /* DEBUG */
    public void LogInputStates(){
        for(int i = 0 ; i < numInputs; i++){
            Inputs.get(i).logState();
        }
    }
    public void LogOutputState(){
        Output.logState();
    }
}
