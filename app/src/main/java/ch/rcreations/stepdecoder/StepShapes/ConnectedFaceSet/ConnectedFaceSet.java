package ch.rcreations.stepdecoder.StepShapes.ConnectedFaceSet;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Face.Face;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import javafx.scene.control.TreeItem;

import java.util.*;

/**
 * <p>
 * <p>
 * Is a Abstract Class for Shells used in {@link ClosedShell}
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */


abstract class ConnectedFaceSet extends StepShapes {

    protected final Set<Face> setOfFaces;

    /**
     *
     * @param name Name of the Closed Shell
     * @param setOfFaces a {@link Set} of faces
     * @param lineNumber the linenumber in the Step-file
     */
    public ConnectedFaceSet(String name, Set<Face> setOfFaces,int lineNumber) {
        this(name,setOfFaces,lineNumber,AP242Code.CONNECTED_FACE_SET);
    }

    /**
     *
     * @param name Name of the Closed Shell
     * @param setOfFaces a {@link Set} of faces
     * @param lineNumber the linenumber in the Step-file
     * @param ap242Code the Code of {@link AP242Code} Step Code
     */
    public ConnectedFaceSet(String name, Set<Face> setOfFaces,int lineNumber,AP242Code ap242Code) {
        super(ap242Code,name,lineNumber);
        this.name = name;
        this.setOfFaces = setOfFaces;
    }


    public Set<Face> getSetOfFaces() {
        return Set.copyOf(setOfFaces);
    }


    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        for(Face face : this.getSetOfFaces()){
            treeItem.getChildren().add(face.getTreeItem());
        }
        return treeItem;
    }
}
