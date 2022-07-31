package ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.Edge.Edge;
import ch.rcreations.stepdecoder.StepShapes.Point.CartesianPoint;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.Shape3D;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FaceBound extends StepShapes {
    Loop faceLoop;
    EdgeLoop edgeLoop = null;
    Boolean orientation;


    public FaceBound(String name, Loop faceLoop, Boolean orientation,int lineNumber,AP242Code ap242Code) {
        super(ap242Code,name,lineNumber);
        this.name = name;
        this.faceLoop = faceLoop;
        this.orientation = orientation;
    }
    public FaceBound(String name, Loop faceLoop, Boolean orientation,int lineNumber){
        this(name,faceLoop,orientation,lineNumber,AP242Code.FACE_BOUND);
    }

    public FaceBound(String name, EdgeLoop faceLoop, Boolean orientation,int lineNumber){
        this(name,faceLoop,orientation,lineNumber,AP242Code.FACE_BOUND);
    }

    public FaceBound(String name, EdgeLoop faceLoop, Boolean orientation,int lineNumber,AP242Code ap242Code) {
        this(name,(Loop) faceLoop,orientation,lineNumber,ap242Code);
        this.edgeLoop = faceLoop;
    }

    @Override
    public AP242Code getTyp() {
        return AP242Code.FACE_BOUND;
    }

// DRAWING






    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        //treeItem.getChildren().add(faceLoop.getTreeItem());
        for(Edge cartesianPoint : this.edgeLoop.getOrientedEdges()){
            treeItem.getChildren().add(cartesianPoint.getTreeItem());
        }
        return treeItem;
    }

    @Override
    public String toString() {
        return AP242Code.FACE_BOUND + " " + name;
    }

    @Override
    public List<Map<String, String>> getPreferencesList() {
        return Collections.unmodifiableList(this.preferencesMapList);
    }

    @Override
    public boolean setPreference(Map<String, String> preference) {
        return false;
    }


    @Override
    public Shape3D getShape() {
        return null;
    }

    public EdgeLoop getEdgeLoop() {
        return edgeLoop;
    }
}
