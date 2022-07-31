package ch.rcreations.stepdecoder.StepShapes.Vertex;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import javafx.scene.control.TreeItem;

public class SimpleVertexD extends Vertex{
    public SimpleVertexD(String name,int lineNumber) {
        super(name,lineNumber,AP242Code.VERTEX);
    }

    @Override
    public <T> T ifExistGivePoint() {
        return null;
    }




}
