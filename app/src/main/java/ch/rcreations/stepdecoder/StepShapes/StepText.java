package ch.rcreations.stepdecoder.StepShapes;

import javafx.scene.control.TreeItem;
import javafx.scene.shape.Shape3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StepText extends StepShapes{


    public StepText(String name) {
        super(AP242Code.STEP_TEXT,name,0);
    }

    @Override
    public AP242Code getTyp() {
        return AP242Code.STEP_TEXT;
    }

    @Override
    public String toString() {
        return name;
    }

}
