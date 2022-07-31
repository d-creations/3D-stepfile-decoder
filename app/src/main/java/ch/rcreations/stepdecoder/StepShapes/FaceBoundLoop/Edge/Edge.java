package ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.Edge;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Point.CartasianAxisE;
import ch.rcreations.stepdecoder.StepShapes.Point.Point;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import ch.rcreations.stepdecoder.StepShapes.Vertex.Vertex;
import ch.rcreations.stepdecoder.StepShapes.Vertex.VertexPoint;
import javafx.scene.control.TreeItem;

import java.util.*;

public class Edge extends StepShapes {
    protected Vertex edgeStart;
    protected Vertex edgeEnd;

    protected final List<Map<String, String>> preferencesMapList = new ArrayList<>();
    protected double startX;
    protected double startY;
    protected double startZ;
    protected double endX;
    protected double endY;
    protected double endZ;
    public Edge(String name, Vertex edgeStart, Vertex edgeEnd,int lineNumber){
        this(name,edgeStart,edgeEnd,lineNumber,AP242Code.EDGE);
    }

    public Edge(String name, VertexPoint edgeStart, VertexPoint edgeEnd,int lineNumber) {
        this(name,edgeStart,edgeEnd,lineNumber,AP242Code.EDGE);
    }

    public Edge(String name, Vertex edgeStart, Vertex edgeEnd,int lineNumber,AP242Code ap242Code) {
        super(ap242Code,name,lineNumber);
        this.name = name;
        this.edgeStart = edgeStart;
        this.edgeEnd = edgeEnd;
        Point VertexStart = edgeStart.ifExistGivePoint();
        Point VertexEnd = edgeEnd.ifExistGivePoint();
        if (VertexStart != null && VertexEnd != null) {
            Map<CartasianAxisE, Double> pointStart = VertexStart.getPoint();
            Map<CartasianAxisE, Double> pointEnd = VertexEnd.getPoint();
            CartasianAxisE x = CartasianAxisE.X;
            CartasianAxisE y = CartasianAxisE.Y;
            CartasianAxisE z = CartasianAxisE.Z;
            startX = pointStart.get(x);
            startY = pointStart.get(y);
            startZ = pointStart.get(z);
            endX = pointEnd.get(x);
            endY = pointEnd.get(y);
            endZ = pointEnd.get(z);
        }
        Map<String,String> preferencesMap = new HashMap<>();
        preferencesMap.put("Start X",Double.toString(startX));
        preferencesMap.put("Start Y",Double.toString(startY));
        preferencesMap.put("Start Z",Double.toString(startZ));
        preferencesMap.put("End X",Double.toString(endX));
        preferencesMap.put("End Y",Double.toString(endY));
        preferencesMap.put("End Z",Double.toString(endZ));
        preferencesMapList.add(preferencesMap);
    }

    public Edge(String name, VertexPoint edgeStart, VertexPoint edgeEnd,int lineNumber,AP242Code ap242Code) {
        super(ap242Code,name,lineNumber);
        this.name = name;
        this.edgeStart = edgeStart;
        this.edgeEnd = edgeEnd;
        Point VertexStart = edgeStart.ifExistGivePoint();
        Point VertexEnd = edgeEnd.ifExistGivePoint();
        if (VertexStart != null && VertexEnd != null) {
            Map<CartasianAxisE, Double> pointStart = VertexStart.getPoint();
            Map<CartasianAxisE, Double> pointEnd = VertexEnd.getPoint();
            CartasianAxisE x = CartasianAxisE.X;
            CartasianAxisE y = CartasianAxisE.Y;
            CartasianAxisE z = CartasianAxisE.Z;
            startX = pointStart.get(x);
            startY = pointStart.get(y);
            startZ = pointStart.get(z);
            endX = pointEnd.get(x);
            endY = pointEnd.get(y);
            endZ = pointEnd.get(z);
        }
        Map<String,String> preferencesMap = new HashMap<>();
        preferencesMap.put("Start X",Double.toString(startX));
        preferencesMap.put("Start Y",Double.toString(startY));
        preferencesMap.put("Start Z",Double.toString(startZ));
        preferencesMap.put("End X",Double.toString(endX));
        preferencesMap.put("End Y",Double.toString(endY));
        preferencesMap.put("End Z",Double.toString(endZ));
        preferencesMapList.add(preferencesMap);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Vertex getEdgeStart() {
        return edgeStart;
    }

    public Vertex getEdgeEnd() {
        return edgeEnd;
    }

    //Drawing


    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getStartZ() {
        return startZ;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

    public double getEndZ() {
        return endZ;
    }
    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(edgeStart.getTreeItem());
        treeItem.getChildren().add(edgeEnd.getTreeItem());
        return treeItem;
    }
}
