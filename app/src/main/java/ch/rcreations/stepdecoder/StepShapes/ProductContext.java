package ch.rcreations.stepdecoder.StepShapes;


import javafx.scene.control.TreeItem;


public class ProductContext extends StepShapes {

    protected StepShapes frameReferenz;
    protected String disciplineType;

    public ProductContext(String name, StepShapes frameReferenz, String disciplineType,int lineNumber) {
        super(AP242Code.PRODUCT_CONTEXT,name,lineNumber);
        this.frameReferenz = frameReferenz;
        this.disciplineType = disciplineType;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(frameReferenz.getTreeItem());
        return treeItem;
    }
}
