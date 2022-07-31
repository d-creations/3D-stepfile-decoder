package ch.rcreations.stepdecoder.StepShapes;

import javafx.scene.control.TreeItem;

public class ProductDefinitionShape extends StepShapes {

    protected String description;
    protected StepShapes definition;

    public ProductDefinitionShape(String name, String description, StepShapes definition,int lineNumber) {
        super(AP242Code.PRODUCT_DEFINITION_SHAPE,name,lineNumber);
        this.description = description;
        this.definition = definition;
    }
    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(definition.getTreeItem());
        return treeItem;
    }
}
