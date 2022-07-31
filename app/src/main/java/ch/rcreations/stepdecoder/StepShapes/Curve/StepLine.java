package ch.rcreations.stepdecoder.StepShapes.Curve;


import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Curve.Conic.Conic;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import javafx.scene.control.TreeItem;

/**
 * <p>
 * <p>
 *  Step object a Line a subclass of {@link Conic}
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */
public class StepLine extends Curve {

    protected StepShapes coordinateSystem;
    protected StepShapes vector;

    /**
     *Constructor of a StepLine
     * @param name name of the Curve
     * @param coordinateSystem gibes The Coordinate System
     * @param vector is the Direction Vector
     * @param lineNumber line number in Step file
     */
    public StepLine(String name, StepShapes coordinateSystem, StepShapes vector,int lineNumber) {
        super(name,lineNumber,AP242Code.LINE);
        this.coordinateSystem = coordinateSystem;
        this.vector = vector;
    }
    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(coordinateSystem.getTreeItem());
        treeItem.getChildren().add(vector.getTreeItem());
        return treeItem;
    }

}
