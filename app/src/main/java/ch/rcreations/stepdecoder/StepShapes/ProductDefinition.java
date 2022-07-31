package ch.rcreations.stepdecoder.StepShapes;


import javafx.scene.control.TreeItem;


public class ProductDefinition extends StepShapes {

    protected String id;
    protected String description;
    protected StepShapes productDefinitionFormation;
    protected StepShapes product;

    public ProductDefinition(String name, String description, StepShapes productDefinitionFormation,StepShapes product,int lineNumber) {
        super(AP242Code.PRODUCT_DEFINITION,name,lineNumber);
        this.description = description;
        this.productDefinitionFormation = productDefinitionFormation;
        this.product = product;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(productDefinitionFormation.getTreeItem());
        treeItem.getChildren().add(product.getTreeItem());
        return treeItem;
    }

}
