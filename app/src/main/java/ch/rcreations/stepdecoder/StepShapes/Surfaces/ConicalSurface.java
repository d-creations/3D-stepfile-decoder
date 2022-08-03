package ch.rcreations.stepdecoder.StepShapes.Surfaces;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Axis2Placement3D;

public class ConicalSurface extends ElementarySurface{

    private double radius;
    private double semiAngle;
    public ConicalSurface(String name, Axis2Placement3D position,double radius,double semiAngle, int lineNumber) {
        super(name, position, lineNumber, AP242Code.CONICAL_SURFACE);
        this.radius = radius;
        this.semiAngle = semiAngle;
    }

    public double getRadius() {
        return radius;
    }

    public double getSemiAngle() {
        return semiAngle;
    }
}
