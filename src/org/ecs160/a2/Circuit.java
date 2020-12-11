package org.ecs160.a2;

import com.codename1.io.Util;

import java.io.*;
import java.util.ArrayList;

public class Circuit implements com.codename1.io.Externalizable {
    ArrayList<LogicComponent> AllComponents = new ArrayList<>();
    ArrayList<LogicComponent> RootComponents = new ArrayList<>(); // attached to overall outputs

    public Circuit() {
    }

    public Circuit(Circuit newCircuit) {
        AllComponents = new ArrayList<>();
        for (LogicComponent c : newCircuit.AllComponents)
            AllComponents.add(new LogicComponent(c));
        RootComponents = new ArrayList<>();
        for (LogicComponent c : newCircuit.RootComponents)
            RootComponents.add(new LogicComponent(c));
    }

    private void UpdateRoots(){
        // Is root if Output is standalone (not connected)
        for (LogicComponent comp: AllComponents) {
            if (comp.IsOutputConnected()) {
                // Output is attached to something - remove from roots
                if (RootComponents.contains(comp)) {
                    RootComponents.remove(comp);
                }
            }
            else {
                // Output is standalone - add to roots
                if(!RootComponents.contains(comp)){
                    RootComponents.add(comp);
                }
            }
        }

    }

    // Individual Component Functions
    public void AddComponent(LogicComponent comp){
        AllComponents.add(comp);
        Update();
    }
    public void SetComponent(LogicComponent other){
        // Updates Changes in a Component
        Long ID = other.GetID();
        RemoveComponent(ID);
        AllComponents.add(other);
    }
    public LogicComponent GetComponent(long ID){
        for (LogicComponent comp:AllComponents) {
            if(comp.GetID() == ID){
                return comp;
            }
        }
        return null;
    }
    public void RemoveComponent(long ID){
        for (LogicComponent comp:AllComponents) {
            if(comp.GetID() == ID){
                AllComponents.remove(comp);
                if(RootComponents.contains(comp)){
                    RootComponents.remove(comp);
                }
                return;
            }
        }
    }

    // Circuit Overall State Update Functions
    private void UpdateRecursiveHelper(LogicComponent element){
        // This element is root

        // Get all Inputs
        ArrayList<IO_Component> currInputs = element.GetInputs();
        // Check each input is connected
        for (IO_Component in:currInputs) {
            if (in.isConnected()) {
                // if yes - call helper on the element attached
                LogicComponent attachedElement = GetComponent(in.GetConnectedID());

                // Error Check
                if (attachedElement == null) {
                    System.out.println("Error: ID<" + element + "> could not find element attached as input");
                    return;
                }

                UpdateRecursiveHelper(attachedElement);
                // update this input with updated output
                in.setState(attachedElement.GetOutputState());
            }
            // if no - continue
        }
        // all inputs are updated, perform operation and set output
        element.UpdateOutput();

    }
    public void UpdateStates(){
        // Updates IO information of all sub-circuits
        if(RootComponents != null){
            for(LogicComponent element : RootComponents){
                UpdateRecursiveHelper(element);
            }
        }
    }
    public void Update(){
        // Updates Entire Circuit
        UpdateRoots();
        UpdateStates();
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeObject(AllComponents, out);
        Util.writeObject(RootComponents, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        AllComponents = (ArrayList<LogicComponent>) Util.readObject(in);
        RootComponents = (ArrayList<LogicComponent>) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "Circuit";
    }
}
