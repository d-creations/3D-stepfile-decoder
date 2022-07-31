package ch.rcreations.stepdecoder.StepShapes;

import javafx.scene.control.TreeItem;
import javafx.scene.shape.Shape3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StepVector extends StepShapes{

    protected StepShapes orientation;
    protected Double length;

    public StepVector(String name, StepShapes orientation, Double length,int lineNumber) {
        super(AP242Code.VECTOR,name,lineNumber);
        this.orientation = orientation;
        this.length = length;
    }
    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>();
        treeItem.getChildren().add(orientation.getTreeItem());
        return treeItem;
    }
}
