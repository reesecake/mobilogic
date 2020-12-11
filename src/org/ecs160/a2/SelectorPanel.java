package org.ecs160.a2;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;

public class SelectorPanel extends Container {

    private static CanvasContainer canvasContainer;
    private static Save save;

    // might move these, they don't need to be global
    private static Button clear;
    private static Button delete;
    private static Button saveBtn;
    private static Button loadBtn;

    public SelectorPanel(CanvasContainer canvasCon) {
        super(BoxLayout.y());
        canvasContainer = canvasCon;

        save = new Save();

        clear = new Button("Clear");
        delete = new Button("Delete");
        clear.addActionListener((evt -> canvasContainer.clearCanvas()));
        // TODO make delete do something
        delete.addActionListener((evt -> {}));


        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage saveIcon = FontImage.createMaterial(FontImage.MATERIAL_SAVE, s);
        saveBtn = new Button("", saveIcon);
        saveBtn.addActionListener(evt -> {
            makeSave();
        });

        FontImage loadIcon = FontImage.createMaterial(FontImage.MATERIAL_FOLDER, s);
        loadBtn = new Button("", loadIcon);
        loadBtn.addActionListener(evt -> {
            loadSaves();
        });

        getStyle().setBgTransparency(255);
        getStyle().setBgColor(0xd3d3d3);

        addComponent(new PanelToolBar());

        GateList gateList = new GateList();
        addComponent(gateList);

        addGateListListeners(gateList);
    }

    private void makeSave() {
        Dialog saveDlg = new Dialog("Save Canvas");
        saveDlg.setLayout(BoxLayout.y());

        TextField name = new TextField(canvasContainer.getCanvas().getName(), "Save name", 15, TextArea.ANY);
        saveDlg.add(name);

        Button confirm = new Button("Save");
        confirm.addActionListener(evt -> {
            // do the saving here
            canvasContainer.getCanvas().setName(name.getText());
            save.addCanvas(canvasContainer.getCanvas());
            // then close
            saveDlg.dispose();
        });
        saveDlg.add(confirm);

        Button cancel = new Button("Cancel");
        cancel.addActionListener(evt -> saveDlg.dispose());
        saveDlg.add(cancel);

        saveDlg.show();
    }

    private void loadSaves() {
        Dialog loadDlg = new Dialog("Load Canvas");
        loadDlg.setLayout(BoxLayout.y());

        // only this block depends on saves
        Container loadContainer = new Container(BoxLayout.y());
        loadContainer.setScrollableY(true);
        if (save != null && !save.getSavedCanvases().isEmpty()) {
            for (Canvas c : save.getSavedCanvases()) {
                loadContainer.addComponent(new Label(c.getName()));
            }
            loadDlg.add(loadContainer);
        } else {
            loadDlg.add(new Label("No Saves"));
        }
        // to here

        Button confirm = new Button("Load");
        confirm.addActionListener(evt -> {
            loadDlg.dispose();
        });
        loadDlg.add(confirm);

        Button close = new Button("Close");
        close.addActionListener(evt -> loadDlg.dispose());
        loadDlg.add(close);

        loadDlg.show();
    }

    private static class PanelToolBar extends Container {
        public PanelToolBar() {
            super(BoxLayout.x());

            addComponent(new Label("GATE SELECTOR"));
            addComponent(clear);
            addComponent(delete);
            addComponent(saveBtn);
            addComponent(loadBtn);
        }
    }

    private static class GateList extends Container {
        public GateList() {
            super(BoxLayout.x());
            setScrollableX(true);

            addComponent(new SelectorGate(GateType.LAMP));
            addComponent(new SelectorGate(GateType.POWER));
            addComponent(new SelectorGate(GateType.GROUND));
            addComponent(new SelectorGate(GateType.AND));
            addComponent(new SelectorGate(GateType.OR));
            addComponent(new SelectorGate(GateType.XOR));
            addComponent(new SelectorGate(GateType.NOT));
            addComponent(new SelectorGate(GateType.NAND));
            addComponent(new SelectorGate(GateType.NOR));
            addComponent(new SelectorGate(GateType.XNOR));
        }
    }

    private void addGateListListeners(GateList gateList) {
        for (Component gate : gateList) {
            if (gate instanceof SelectorGate) {
                gate.addPointerPressedListener(evt -> canvasContainer.addNewGate(((SelectorGate) gate).type));
            }
        }
    }
}
