package org.ecs160.a2;

import com.codename1.io.Storage;

import java.util.Vector;

public class Save {
    private Vector<Canvas> allCanvases;

    public Save() {
        Storage.getInstance().clearStorage();
        if (!Storage.getInstance().exists("savedCanvases")) {
            allCanvases = new Vector<Canvas>();
        } else {
            allCanvases = (Vector<Canvas>) Storage.getInstance().readObject("savedCanvases");
        }
    }

    public void addCanvas(Canvas newCanvas) {
        // replace canvas with new version if already in saved list
        boolean alreadyExists = false;
        for (int i = 0; i < allCanvases.size(); i++) {
            if (allCanvases.elementAt(i).getName().equals(newCanvas.getName())) {
                allCanvases.set(i, newCanvas);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) allCanvases.addElement(newCanvas);

        Storage.getInstance().writeObject("savedCanvases", allCanvases);
    }

    public Vector<Canvas> getSavedCanvases() {
        return allCanvases;
    }

    public Canvas getCanvasByName(String findName) {
        for (Canvas c : allCanvases) {
            if (c.getName().equals(findName)) return new Canvas(c);
        }
        return null;
    }
}
