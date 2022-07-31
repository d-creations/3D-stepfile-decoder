package ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;


public class Loop extends StepShapes {
    protected String name;

    public Loop(String name,int lineNumber,AP242Code ap242Code) {
        super(ap242Code,name,lineNumber);
    }
    public Loop(String name,int lineNumber) {
        this(name,lineNumber,AP242Code.LOOP);
    }
}
