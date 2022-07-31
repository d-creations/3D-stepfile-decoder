package ch.rcreations.stepdecoder.StepShapes.Face;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.FaceBound;
import ch.rcreations.stepdecoder.StepShapes.Point.CartesianPoint;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import javafx.scene.control.TreeItem;

import java.util.*;

public abstract class Face extends StepShapes {
   protected Set<FaceBound> FaceBound;
  protected List<CartesianPoint> stepDrawLinesForTriangle = new ArrayList<>();




    public Face(String name, Set<FaceBound> faceBounds,int lineNumber) {
        super(AP242Code.FACE_BOUND,name,lineNumber);
        this.FaceBound = faceBounds;
    }


    public Face(String name, Set<FaceBound> faceBounds,int lineNumber,AP242Code ap242Code) {
        super(ap242Code,name,lineNumber);
        this.FaceBound = faceBounds;
    }

    protected Set<FaceBound> getFaceBound() {
        return Set.copyOf(FaceBound);
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        for (FaceBound faceBound : FaceBound){
            treeItem.getChildren().add(faceBound.getTreeItem());
        }
        return treeItem;
    }

    public List<CartesianPoint> getStepDrawTriangleLines() {
        return stepDrawLinesForTriangle;
    }
}
