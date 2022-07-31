package ch.rcreations.stepdecoder.StepShapes;

import javafx.scene.control.TreeItem;

import java.util.*;

public class DefinitionalRepresentation extends StepShapes {

    protected Set<StepShapes> items;
    protected StepShapes representationContext;

    public DefinitionalRepresentation(String name, Set<StepShapes> items, StepShapes representationContext,int lineNumber) {
        super(AP242Code.DEFINITIONAL_REPRESENTATION,name,lineNumber);
        this.items = items;
        this.representationContext = representationContext;
    }

    @Override
    public AP242Code getTyp() {
        return AP242Code.DEFINITIONAL_REPRESENTATION;
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
