package ch.rcreations.stepdecoder.StepShapes.ConnectedFaceSet;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Face.Face;
import ch.rcreations.stepdecoder.StepShapes.Point.CartasianAxisE;
import ch.rcreations.stepdecoder.StepShapes.Point.CartesianPoint;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableFloatArray;
import javafx.collections.ObservableIntegerArray;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.*;
import java.util.*;

/**
 * <p>
 * <p>
 * This contains a Closed Shell as a MeshView {@link MeshView} and implements the {@link ConnectedFaceSet}
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */

public class ClosedShell extends ConnectedFaceSet {

    /**
     *
     * @param name Name of the Closed Shell
     * @param setOfFaces a {@link Set} of faces
     * @param lineNumber the linenumber in the Step-file
     */
    public ClosedShell(String name, Set<Face> setOfFaces, int lineNumber) {
        super(name,setOfFaces,lineNumber,AP242Code.CLOSED_SHELL);
        this.mesh = createMesh(setOfFaces);
      }



    private final Mesh mesh;

    /**
     * creats a Meshview with a set of Faces
     * @param setOfFaces a {@link Set} of Faces {@link Face}
     * @return
     */
    private Mesh createMesh(Set<Face> setOfFaces){
        TriangleMesh mesh = new TriangleMesh(VertexFormat.POINT_TEXCOORD);
        ObservableFloatArray vertexArray = FXCollections.observableFloatArray();
        ObservableIntegerArray facesArray = FXCollections.observableIntegerArray();
        List<Double> axis = new ArrayList<>();
        for (Face stepDrawFace : setOfFaces) {
                for (CartesianPoint stepDrawLine : stepDrawFace.getStepDrawTriangleLines()){
                    for (int i = 0; i < 1; i++) {
                        Double x1 = stepDrawLine.getPoint().get(CartasianAxisE.X);
                        Double y1 = stepDrawLine.getPoint().get(CartasianAxisE.Y);
                        Double z1 = stepDrawLine.getPoint().get(CartasianAxisE.Z);
                        axis.add(x1);
                        axis.add(y1);
                        axis.add(z1);
                    }
            }
        }
        int i=0;
        for (int k = 0;k<axis.size();k = k+9) {
            vertexArray.addAll(axis.get(k).floatValue(),axis.get(k+1).floatValue(),axis.get(k+2).floatValue());
            vertexArray.addAll(axis.get(k+3).floatValue(),axis.get(k+4).floatValue(),axis.get(k+5).floatValue());
            vertexArray.addAll(axis.get(k+6).floatValue(),axis.get(k+7).floatValue(),axis.get(k+8).floatValue());
            //vertexArray.addAll(axis.get(axis.size()-(i+3)).floatValue(),axis.get(axis.size()-(i+2)).floatValue(),axis.get(axis.size()-(i+1)).floatValue());
            // ONE FACE WITH 3 Points

            facesArray.addAll(i, 0);
            facesArray.addAll(i + 1, 0);
            facesArray.addAll(i + 2, 0);
            i = i+3;
        }

        mesh.getPoints().addAll(// Verticals
                vertexArray
        );
        mesh.getTexCoords().addAll(//textur
                0f,0f//UV1
        );
        mesh.getFaces().addAll(//Faces
                facesArray
        );
        return mesh;
    }


    public Mesh getMeshView() {
        return this.mesh;
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        for(Face face : this.getSetOfFaces()){
            treeItem.getChildren().add(face.getTreeItem());
        }
        return treeItem;
    }

    @Override
    public Shape3D getShape() {
        return new MeshView(mesh);
    }


}
