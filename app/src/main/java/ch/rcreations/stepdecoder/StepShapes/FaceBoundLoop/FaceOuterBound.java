package ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;

public class FaceOuterBound extends FaceBound {
    public FaceOuterBound(String name, Loop faceLoop, Boolean orientation, int lineNumber) {
        super(name, faceLoop, orientation, lineNumber,AP242Code.FACE_OUTER_BOUND);
    }

    public FaceOuterBound(String name, EdgeLoop faceLoop, Boolean orientation, int lineNumber) {
        super(name, faceLoop, orientation, lineNumber,AP242Code.FACE_OUTER_BOUND);
    }
}
