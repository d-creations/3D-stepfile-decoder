package ch.rcreations.stepdecoder.StepShapes;


import javafx.scene.control.TreeItem;


public class Pcurve extends StepShapes {

    protected StepShapes basis;
    protected StepShapes curve;

    public Pcurve(String name, StepShapes basis, StepShapes curve,int lineNumber) {
        super(AP242Code.PCURVE,name,lineNumber);
        this.basis = basis;
        this.curve = curve;
    }
    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(basis.getTreeItem());
        treeItem.getChildren().add(curve.getTreeItem());
        return treeItem;
    }

}
