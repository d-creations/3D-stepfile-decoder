package ch.rcreations.stepdecoder.StepShapes.Face;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.FaceBound;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import ch.rcreations.stepdecoder.StepShapes.Surfaces.Surface;
import javafx.scene.control.TreeItem;

import java.util.Set;

public class FaceSurface extends Face {
    protected boolean sameSense;
    protected Surface faceGeometrie;

    public FaceSurface(String name, Set<FaceBound> faceBounds, Surface faceGeometrie, boolean sameSense,int lineNumber) {
        this(name,faceBounds,faceGeometrie,sameSense,lineNumber,AP242Code.FACE_SURFACE);
    }
    public FaceSurface(String name, Set<FaceBound> faceBounds, Surface faceGeometrie, boolean sameSense,int lineNumber,AP242Code ap242Code) {
        super(name, faceBounds,lineNumber,ap242Code);
        this.faceGeometrie = faceGeometrie;
        this.sameSense = sameSense;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(faceGeometrie.getTreeItem());
        for (FaceBound faceBound : FaceBound){
            treeItem.getChildren().add(faceBound.getTreeItem());
        }
        return treeItem;
    }
}
