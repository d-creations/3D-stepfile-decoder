package ch.rcreations.stepdecoder.StepShapes.Surfaces;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;

public class Surface extends StepShapes {

    public Surface(String name,int lineNumber,AP242Code ap242Code) {
        super(ap242Code,name,lineNumber);
    }

    public Surface(String name,int lineNumber) {
        this(name,lineNumber,AP242Code.SURFACE);
    }
}
