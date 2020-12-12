package org.ecs160.a2;

import com.codename1.io.Storage;

import java.io.IOException;
import java.util.Vector;

public class Save {
    private Vector<Canvas> allCanvases;

    public Save() {
        allCanvases = new Vector<>();
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
    }

    public Vector<Canvas> getSavedCanvases() {
        return allCanvases;
    }

    public Canvas getCanvasByName(String findName) {
        for (Canvas c : allCanvases) {
            if (c.getName().equals(findName)) return c;
        }
        return null;
    }
}
