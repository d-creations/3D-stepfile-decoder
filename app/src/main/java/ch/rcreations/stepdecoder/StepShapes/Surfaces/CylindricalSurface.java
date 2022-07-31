package ch.rcreations.stepdecoder.StepShapes.Surfaces;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Axis2Placement3D;

public class CylindricalSurface extends ElementarySurface{

    protected double radius;

    public CylindricalSurface(String name, Axis2Placement3D position, double radius,int lineNumber) {
        super(name, position, lineNumber, AP242Code.CYLINDRICAL_SURFACE);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
