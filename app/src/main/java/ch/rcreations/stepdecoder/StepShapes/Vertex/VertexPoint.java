package ch.rcreations.stepdecoder.StepShapes.Vertex;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Point.CartesianPoint;
import ch.rcreations.stepdecoder.StepShapes.Point.Point;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VertexPoint extends Vertex {

    CartesianPoint point;

    public VertexPoint(String name, CartesianPoint point,int lineNumber) {
        super(name,lineNumber,AP242Code.VERTEX_POINT);
        this.point = point;
    }


    @Override
    public CartesianPoint ifExistGivePoint() {
        return this.point;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(point.getTreeItem());
        return treeItem;
    }
}
