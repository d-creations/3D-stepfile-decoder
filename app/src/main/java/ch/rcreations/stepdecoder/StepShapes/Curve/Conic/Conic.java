package ch.rcreations.stepdecoder.StepShapes.Curve.Conic;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Axis2Placement3D;
import ch.rcreations.stepdecoder.StepShapes.Curve.Curve;

/**
 * <p>
 * <p>
 * Is a Class for a Conic Shells used in {@link Curve}
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */
public class Conic extends Curve {

    protected Axis2Placement3D position;


    /**
     * Constructor of a Circle
     * @param name name of the Circle
     * @param position position of the Circle {@link Axis2Placement3D}
     * @param fileLineNumber line number in the Step File
     * @param ap242Code Step file Code {@link AP242Code}
     */
    protected Conic(String name,Axis2Placement3D position, int fileLineNumber, AP242Code ap242Code) {
        super(name, fileLineNumber, ap242Code);
        this.position = position;
    }
    /**
     * Constructor of a Circle
     * @param name name of the Circle
     * @param position position of the Circle {@link Axis2Placement3D}
     * @param fileLineNumber line number in the Step File
     */
    protected Conic(String name, Axis2Placement3D position,int fileLineNumber) {
        this(name,position, fileLineNumber, AP242Code.CONIC);
    }

    public Axis2Placement3D getPosition() {
        return position;
    }
}
