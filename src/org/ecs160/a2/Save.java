package org.ecs160.a2;

import com.codename1.io.Storage;

import java.util.Vector;

public class Save {
    private Vector<Canvas> allCanvases;

    public Save() {
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
            if (allCanvases.elementAt(i).getId().equals(newCanvas.getId())) {
                allCanvases.set(i, newCanvas);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) allCanvases.add(newCanvas);

        Storage.getInstance().writeObject("savedCanvases", allCanvases);
    }

    public Vector<Canvas> getSavedCanvases() {
        return allCanvases;
    }

    public Canvas getCanvasById(Integer id) {
        for (Canvas c : allCanvases) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }
}
