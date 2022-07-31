package ch.rcreations.stepdecoder.StepShapes;

import javafx.scene.control.TreeItem;


public class MainfoldSolidBrep extends StepShapes {

    protected StepShapes shapeData;
    public MainfoldSolidBrep(String name, StepShapes stepShapes,int lineNumber) {
        super(AP242Code.MANIFOLD_SOLID_BREP,name,lineNumber);
        this.shapeData = stepShapes;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(shapeData.getTreeItem());
        return treeItem;
    }

}
