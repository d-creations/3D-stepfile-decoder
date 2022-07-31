package ch.rcreations.stepdecoder.StepShapes;

import javafx.scene.control.TreeItem;
import javafx.scene.shape.Shape3D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

abstract public class StepShapes {
    protected AP242Code typ;

    protected String name;

    protected int fileLineNumber;

    protected final List<Map<String, String>> preferencesMapList = new ArrayList<>();


    public StepShapes(AP242Code typ, String name, int fileLineNumber) {
        this.typ = typ;
        this.name = name;
        this.fileLineNumber = fileLineNumber;

        Map<String,String> preferencesMap = new HashMap<>();
        preferencesMap.put("TYPE",typ.toString());
        preferencesMap.put("NAME",name);
        preferencesMap.put("FILE LINE NUMBER",Integer.toString(fileLineNumber));
        preferencesMapList.add(preferencesMap);
    }


    public AP242Code getTyp() {
        return this.typ;
    }


    public TreeItem<StepShapes> getTreeItem() {
        return new TreeItem<>(this);
    }

    public String toString() {
        return typ + " " + name;
    }


    public List<Map<String, String>> getPreferencesList() {
        return Collections.unmodifiableList(this.preferencesMapList);
    }


    public boolean setPreference(Map<String, String> preference) {
        return this.preferencesMapList.add(preference);
    }


    public Shape3D getShape() {
        return null;
    }

    public String getName() {
        return name;
    }

    public int getFileLineNumber() {
        return fileLineNumber;
    }

    public List<Map<String, String>> getPreferencesMapList() {
        return preferencesMapList;
    }
}
