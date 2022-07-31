package ch.rcreations.stepdecoder.StepShapes.Surfaces;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Axis2Placement3D;


public class ElementarySurface extends Surface {

    protected Axis2Placement3D position;

    public ElementarySurface(String name,Axis2Placement3D position,int lineNumber,AP242Code ap242Code) {
        super(name,lineNumber,ap242Code);
        this.position = position;
    }
    public ElementarySurface(String name,Axis2Placement3D position,int lineNumber) {
        this(name,position,lineNumber,AP242Code.ELEMENTARY_SURFACE);
    }

    public Axis2Placement3D getPosition() {
        return position;
    }
}
