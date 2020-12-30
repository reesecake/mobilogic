# Mobile Logic Simulator
Note: Renamed project and removed class code

## Overview
Mobi-logic is a mobile application that simulates simple logic expressions using circuits. It is meant to be intuitive and easy for new users to quickly learn the basics of logic simulations with a combination of gates and wires. It features a list of logic gates that can be manipulated in a mobile-friendly manner and saved as sub circuits for later use. The gates and wires use color coding to indicate the functionality of their fundamental operation.

## Expected Use Case
We expect the profile of our typical customer to look like this: It is a student or someone new to learning logic expressions and digital circuits. They want a simple, clean application in order to practice their skills. It should be intuitive and user friendly, as many more complicated logic simulators are not. The user does not need more advanced features, just simple logic gates will be fine for learning and experimenting. They want to be able to control inputs and outputs and optionally calculate the critical path given customizable propagation delays if they want to practice in that area. They want to be able to connect gates with wires and control the inputs to see how the output changes.
## UI/UX
The UI for Mobi-logic is meant to be simple and intuitive for users. It is made up of a canvas area for editing circuits and a panel at the bottom of the screen for selecting gates or saving and loading. The canvas instance is held inside a CanvasContainer so that it can be cleared or saved/loaded. The canvas itself is a series of white cells that can be replaced with the gates. 
Below the canvas, there is the gate selection panel and toolbar. In the panel, there is a list of gates that the user can press to add to the canvas. In the toolbar is a clear button, to reset the canvas with a fresh one, and save and load buttons indicated by a floppy disk icon and folder icon. Pressing the save button will bring up a menu to input a name and save the canvas. Pressing the load button will bring up a similar menu with a list of saved canvases. 
	As for interacting with gates on the canvas, double tapping them will popup a menu with options and long pressing them will attach a wire. The gates can be dragged around the canvas and dropped; the connected wires will ‘follow’ the gates wherever they move.
### UI/UX Code
The UI/UX is visually simple and so the code for their corresponding parts is relatively cohesive. The main Form is separated into a CanvasContainer and the SelectorPanel which has access to the instance of CanvasContainer. The CanvasContainer determines the size of the viewing window for the canvas and has methods for the SelectorPanel to access the canvas instance with, such as getCanvas, clearCanvas, etc. 
The SelectorPanel extends Container and displays a toolbar and a scrollable list of available gates. Those gates are an extension of the parent gate object but with limited functionality since they are not meant to be interacted with. The important methods within SelectorPanel are makeSave, loadSaves, and addGateListListeners. These methods allow users to create saves, load saves, and add gates to the canvas by pressing a SelectorGate, respectively. 
An interactable Gate, is a component with an image and corresponding logic component to make it operate. The Gate class constructor gives the gate its image and component then sets its style and makes it draggable. The other methods in the class allow the Gate to be interacted with by double tapping for a dialog or toggling the state of an input switch. Its other methods are for saving the Gate via the Save class.
The Canvas class is the display for the user’s circuit. Its constructor creates an associated backend circuit, styles itself, creates a sheet of CanvasCells, and keeps a list of wire connections. Its other methods are generally for Gate manipulation, like dropping or adding, and editing wires between Gates on the canvas. It also has functions for its parent CanvasContainer or SelectorPanel to use.
## Business Logic
The business logic is the series of classes to support the functionality of circuits, gates, and wires present on the canvas.
Firstly, the canvas declares an instance of the Circuit class which tracks the component Gates and their states. Its important methods are for updating the states of gates whenever a change is made on the canvas. 
Each Gate component is also a child of the LogicComponent class which abstracts the operation of all the gates. It has variables such as its inputs and output and methods to change the state of the gate whenever it is interacted with. All the gates are derived from LogicComponent to give them their unique functionality, like an AND gate acting as a logical AND. 
The application also has a Save class for handling the saved canvases and their storage. It has a vector of canvases and is called on by the SelectorPanel to store or load its canvases. addCanvas, getSavedCanvases, and getCanvasByName are all methods that are used in dialog boxes for saving and loading.
Lastly, the Wire class handles the connection between gates by storing the two gates it is connected to and a boolean for power. Its methods are used by Canvas when the user long presses gates to connect them.

## Operations Guide
#### Adding Gates to the Canvas
Adding gates to the canvas for operation only requires the user to press the gate they want in the ‘Gate Selector’ menu at the bottom of the screen. The corresponding gate will appear on the canvas around the top left of the screen, unless another gate already occupies that spot.

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/a2prj_addgates.png" alt="Add Gates" width="250"/>

#### Connecting Wires
To connect gates via a wire, long press on the first gate and then long press on the second. The long press will be indicated by the square outline surrounding a gate and connection will be indicated by the line between gates. A green wire shows when the wire has power, a blue wire shows there is no power, and a red wire shows there is a connection problem.

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/a2prj_connectwires.png" alt="Connecting Wires" width="250"/></p>

#### Gate Menus and Deleting
Double tapping on a placed gate will open a window adjacent to it displaying its gate type and a button for deletion. Deleting the gate will remove it from the canvas and disconnect its wires. Pressing close or anywhere outside of the window, will close the menu.

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/a2prj_deletegate.png" alt="Deleting Gates" width="250"/></p>

#### Info Button
In the top right corner there is the Info button. Pressing it will open an info window reminding the user how to interact with the gates. Pressing ‘Got it!’ or outside of the window will close it.

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/CodenameOne%20mobilogic_info.png" alt="Info Button" width="250"/></p>

#### Saving the Canvas
In the Gate Selector panel toolbar, there is a save button. Pressing the button will bring up a prompt to save the current canvas. Input a name and press save to add the canvas to memory.; press Cancel to cancel the operation.

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/a2prj_save.png" alt="Saving Canvas" width="250"/></p>

#### Loading the Canvas
In the Gate Selector panel toolbar, there is a folder icon next to the save button. Pressing the load button will bring up a similar menu to that of the save button. The load menu will display the names of the saved canvases which can be selected and loaded. Otherwise, it will say “No Saves.”

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/a2prj_loadscreen.png" alt="Loading Canvas" width="250"/></p>

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/a2prj_success.png" alt="Loading Success" width="250"/></p>

#### Using Inputs and Outputs
Mobilogic also has input switches and output lamps to give users control over the input in the circuit and visualize output.

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/a2prj_io_off.png" alt="Inputs and outputs off" width="250"/></p>

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/a2prj_io_on.png" alt="Inputs and outputs on" width="250"/></p>

#### Example Circuit
Using the operations guide and mobile application allows users to create circuits such as this one:

<p align="center"><img src="https://github.com/reesecake/mobilogic/blob/master/images/a2prj_circuit_example.png" alt="Example Circuit" width="250"/></p>

### Artifacts of original readme
[Design Flow Chart](https://lucid.app/lucidchart/invitations/accept/81c8a6fe-cd08-4075-b6da-bf2002145bae)

Use the command `java -cp  out\artifacts\A2Prj\A2Prj.jar;JavaSE.jar com.codename1.impl.javase.Simulator org.ecs160.a2.AppMain` if on Windows to run the jar.
