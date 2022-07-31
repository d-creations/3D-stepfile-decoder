package ch.rcreations.stepdecoder.StepShapes.Surfaces;


import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Axis2Placement3D;


public class Plane extends ElementarySurface {


    public Plane(String name, Axis2Placement3D position,int lineNumber) {
        super(name,position,lineNumber, AP242Code.PLANE);
    }

}
