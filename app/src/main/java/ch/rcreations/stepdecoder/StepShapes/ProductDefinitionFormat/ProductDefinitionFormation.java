package ch.rcreations.stepdecoder.StepShapes.ProductDefinitionFormat;


import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import javafx.scene.control.TreeItem;

public class ProductDefinitionFormation extends StepShapes {

    protected String id;
    protected String description;
    protected StepShapes productReference;

    public ProductDefinitionFormation(String id, String description, StepShapes productReference,int lineNumber) {
        this(id,description,productReference,lineNumber,AP242Code.PRODUCT_DEFINITION_FORMATION);
    }

    public ProductDefinitionFormation(String id, String description, StepShapes productReference,int lineNumber,AP242Code ap242Code) {
        super(AP242Code.PRODUCT_DEFINITION_FORMATION,"",lineNumber);
        this.id = id;
        this.description = description;
        this.productReference = productReference;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(productReference.getTreeItem());
        return treeItem;
    }


}
