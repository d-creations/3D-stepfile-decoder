package ch.rcreations.stepdecoder.StepShapes;

import java.util.ArrayList;
import java.util.List;

public class Direction extends StepShapes {

    protected List<Double> directionRatios = new ArrayList<>();
    public Direction(String name, List<Double> directionRatios,int lineNumber) {
        super(AP242Code.DIRECTION,name,lineNumber);
        this.name = name;
        this.directionRatios.addAll(directionRatios);
    }

    public List<Double> getDirectionRatios() {
        return directionRatios;
    }
}
