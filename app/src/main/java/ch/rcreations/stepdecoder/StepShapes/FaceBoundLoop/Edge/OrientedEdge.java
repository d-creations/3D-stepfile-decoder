package ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.Edge;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import ch.rcreations.stepdecoder.StepShapes.Vertex.Vertex;
import javafx.scene.control.TreeItem;


public class OrientedEdge extends Edge {

    protected EdgeCurve edgeElement;
    protected Boolean orientation;

    public OrientedEdge(String name, Vertex edgeStart, Vertex edgeEnd, EdgeCurve edgeElement, Boolean orientation,int lineNumber) {
        super(name,edgeElement.getEdgeStart(),edgeElement.getEdgeEnd(),lineNumber,AP242Code.ORIENTED_EDGE);
        this.edgeElement = edgeElement;
        this.orientation = orientation;
        calculateEdge();
    }

    private void calculateEdge() {
        if(edgeStart.getName().equals("*")) {
            edgeStart = edgeElement.getEdgeStart();
        }
        if(edgeEnd.getName().equals("*")){
            edgeEnd = edgeElement.getEdgeEnd();
        }
    }
    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(edgeElement.getTreeItem());
        return treeItem;
    }

    public EdgeCurve getEdgeElement() {
        return edgeElement;
    }
}
