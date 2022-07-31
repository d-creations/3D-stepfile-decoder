package ch.rcreations.stepdecoder.StepShapes.Surfaces;

import ch.rcreations.stepdecoder.Config.StepConfig;
import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Axis2Placement3D;

public class SphericalSurface extends ElementarySurface{

    protected double radius;

    public SphericalSurface(String name, Axis2Placement3D position,double radius, int lineNumber) {
        super(name, position, lineNumber, AP242Code.SPHERICAL_SURFACE);
        this.radius = radius;
        StepConfig.printMessage("Radius " + this.radius);
    }

    public double getRadius() {
        return radius;
    }
}
