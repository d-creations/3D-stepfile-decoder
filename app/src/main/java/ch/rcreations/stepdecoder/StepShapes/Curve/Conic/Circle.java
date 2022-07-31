package ch.rcreations.stepdecoder.StepShapes.Curve.Conic;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Axis2Placement3D;

/**
 * <p>
 * <p>
 *  Step object Circle a subclass of {@link Conic}
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */
public class Circle extends Conic{

    double radius;

    /**
     * Constructor of a Circle
     * @param name name of the Circle
     * @param position position of the Circle {@link Axis2Placement3D}
     * @param radius radius of the Circle
     * @param fileLineNumber line number in the Step File
     * @param ap242Code Step file Code {@link AP242Code}
     */
    public Circle(String name, Axis2Placement3D position, double radius, int fileLineNumber, AP242Code ap242Code) {
        super(name, position, fileLineNumber, ap242Code);
        this.radius = radius;
    }

    /**
     * Constructor of a Circle
     * @param name name of the Circle
     * @param position position of the Circle {@link Axis2Placement3D}
     * @param radius radius of the Circle
     * @param fileLineNumber line number in the Step File
     */
    public Circle(String name, Axis2Placement3D position,double radius, int fileLineNumber) {
        this(name, position,radius, fileLineNumber,AP242Code.CIRCLE);
    }

    public double getRadius() {
        return radius;
    }
}
