package ch.rcreations.stepdecoder.StepShapes;


import javafx.scene.control.TreeItem;

import java.util.*;

public class Product extends StepShapes {

    protected String id;
    protected String descrition;
    protected Set<StepShapes> frameReferences;

    public Product(String id, String name, String description, Set<StepShapes> frameReferences,int lineNumber) {
        super(AP242Code.PRODUCT,name,lineNumber);
        this.id = id;
        this.descrition = description;
        this.frameReferences = frameReferences;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        for(StepShapes item : frameReferences){
            treeItem.getChildren().add(item.getTreeItem());
        }
        return treeItem;
    }
}
