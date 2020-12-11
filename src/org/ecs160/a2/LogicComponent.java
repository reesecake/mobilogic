package org.ecs160.a2;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicComponent implements com.codename1.io.Externalizable {
    private Long ID;
    public String name; // Easy Debug Option

    private int numInputs;
    private ArrayList<IO_Component> Inputs;
    private IO_Component Output;
    private double propagationDelay = 0;

    // Init
    private Long GenerateID(){
        return Math.abs(new Random().nextLong());
    }
    private void GenerateInputs(){
        Inputs = new ArrayList<>();
        for(int i = 0; i < this.numInputs; i++){
            IO_Component in = new IO_Component(this.ID);
            in.setState(false);
            Inputs.add(in);
        }
    }

    public LogicComponent() {

    }

    public LogicComponent(int numInputs) {
        this.ID = GenerateID();
        this.numInputs = numInputs;
        Output = new IO_Component(this.ID);

        GenerateInputs();
    }
    public LogicComponent(int numInputs, double propagationDelay) {
        this.ID = GenerateID();
        this.numInputs = numInputs;
        this.propagationDelay = propagationDelay;
        Output = new IO_Component(this.ID);

        GenerateInputs();
    }

    // Getters/Setters
    public boolean GetOutputState(){
        return Output.getState();
    }
    public ArrayList<Boolean> GetInputStates(){
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

    public boolean IsOutputConnected(){
        return Output.isConnected();
    }
    public ArrayList<IO_Component> GetInputs(){
        return Inputs;
    }
    public IO_Component GetOutput(){
        return Output;
    }

    public int GetIndexFirstAvailableInput(){
        // Returns index of first Not Connected Input
        // -1 if none
        int i = 0;
        for( ; i < Inputs.size() ; i++){
            if(Inputs.get(i).isConnected() == false){
                // Found first available input
                return i;
            }
        }
        return -1;
    }

    public void UpdateOutput(){
        // To be overrided by extended components
        return;
    }

    //Connection Functions
    public void ConnectToGate(LogicComponent other, int inputIndex){
        /*
        Connects this.output to other.inputs[inputIndex]
        ConnectedID is updated for both IO
         */

        if(inputIndex >= other.numInputs){
            System.out.println("ERROR: INPUT INDEX OUT OF BOUNDS");
        }
        //Update ConnectedIDs
        this.Output.SetConnectedID(other.GetID());
        other.SetInput(inputIndex,this.GetOutputState());
        //Update Inputs
        other.GetInputs().get(inputIndex).SetConnectedID(this.ID);


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

    public long GetID() {
        return ID;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        out.writeLong(ID);
        out.writeInt(numInputs);
        Util.writeObject(Inputs, out);
        Util.writeObject(Output, out);
        out.writeDouble(propagationDelay);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        ID = in.readLong();
        numInputs = in.readInt();
        Inputs = (ArrayList<IO_Component>) Util.readObject(in);
        Output = (IO_Component) Util.readObject(in);
        propagationDelay = in.readDouble();
    }

    @Override
    public String getObjectId() {
        return "LogicComponent";
    }
}
