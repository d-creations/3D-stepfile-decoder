package ch.rcreations.stepdecoder.StepShapes;

import javafx.scene.control.TreeItem;

import java.util.*;

public class AdvancedBrepShapeRepresentation extends   StepShapes{
    protected Set<StepShapes> items;
    protected StepShapes context;

    public AdvancedBrepShapeRepresentation(String name, Set<StepShapes> items, StepShapes context,int lineNumber) {
        super(AP242Code.ADVANCED_BREP_SHAPE_REPRESENTATION,name,lineNumber);
        this.items = items;
        this.context = context;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        for(StepShapes item : items){
            treeItem.getChildren().add(item.getTreeItem());
        }
        return treeItem;
    }
}
