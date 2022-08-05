package ch.rcreations.stepdecoder.StepShapes;

import javafx.scene.control.TreeItem;

public class ShapeDefinitionRepesentation extends StepShapes{

    protected ProductDefinitionShape PRODUCT_DEFINITION_SHAPE;
    protected AdvancedBrepShapeRepresentation ADVANCED_BREP_SHAPE_REPRESENTATION;

    public ShapeDefinitionRepesentation(StepShapes productDefinitionShape, StepShapes advancedBrepShapeRepresentation,int lineNumber) {
        super(AP242Code.PRODUCT_DEFINITION_SHAPE,"",lineNumber);
        this.ADVANCED_BREP_SHAPE_REPRESENTATION = (AdvancedBrepShapeRepresentation) advancedBrepShapeRepresentation;
        this.PRODUCT_DEFINITION_SHAPE = (ProductDefinitionShape) productDefinitionShape;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        if (PRODUCT_DEFINITION_SHAPE != null) {
            treeItem.getChildren().add(PRODUCT_DEFINITION_SHAPE.getTreeItem());
        }
        if (ADVANCED_BREP_SHAPE_REPRESENTATION != null) {
            treeItem.getChildren().add(ADVANCED_BREP_SHAPE_REPRESENTATION.getTreeItem());
        }
        return treeItem;
    }
}
