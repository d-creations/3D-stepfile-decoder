package ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.Edge;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Curve.Curve;
import ch.rcreations.stepdecoder.StepShapes.Curve.IncrementalPointsD;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import ch.rcreations.stepdecoder.StepShapes.Vertex.Vertex;
import ch.rcreations.stepdecoder.StepShapes.Vertex.VertexPoint;
import javafx.scene.control.TreeItem;

import java.util.*;


/**
 * Ecken Kante
 */
public class EdgeCurve extends Edge {

    protected List<IncrementalPointsD> edgeDrawingPoints = new ArrayList<IncrementalPointsD>();
    protected Curve edgeGeometrie; //vertex
    protected Boolean sameSense;//vertex

    public EdgeCurve(String name, Vertex edgeStart, Vertex edgeEnd, Curve edgeGeometrie, Boolean sameSense,int lineNumber) {
        super(name,edgeStart,edgeEnd,lineNumber,AP242Code.EDGE_CURVE);
        this.edgeGeometrie = edgeGeometrie;
        this.sameSense = sameSense;
    }
    public EdgeCurve(String name, VertexPoint edgeStart, VertexPoint edgeEnd, Curve edgeGeometrie, Boolean sameSense,int lineNumber) {
        super(name,edgeStart,edgeEnd,lineNumber,AP242Code.EDGE_CURVE);
        this.edgeGeometrie = edgeGeometrie;
        this.sameSense = sameSense;
        calculateEdgeDrawingPoints(edgeStart,edgeEnd);
    }

    private void calculateEdgeDrawingPoints(VertexPoint start,VertexPoint end){
        edgeDrawingPoints = edgeGeometrie.getEdgeDrawingPoints(start,end);
        return;
    }


    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(edgeStart.getTreeItem());
        treeItem.getChildren().add(edgeEnd.getTreeItem());
        return treeItem;
    }

    public List<IncrementalPointsD> getEdgeDrawingPoints() {
        return Collections.unmodifiableList(edgeDrawingPoints);
    }
}
