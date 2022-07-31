package ch.rcreations.stepdecoder.StepShapes.Point;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;


public abstract class Point extends StepShapes {


    public Point(String name,int lineNumber,AP242Code ap242Code) {
        super(ap242Code,name,lineNumber);
    }
    public Point(String name,int lineNumber) {
        this(name,lineNumber,AP242Code.POINT);
    }

    public abstract <T> T getPoint();


}
