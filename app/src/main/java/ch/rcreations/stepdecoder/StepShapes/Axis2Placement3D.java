package ch.rcreations.stepdecoder.StepShapes;

import ch.rcreations.stepdecoder.StepShapes.Point.CartesianPoint;

import java.util.ArrayList;
import java.util.List;

public class Axis2Placement3D extends StepShapes {

    protected CartesianPoint location;// Cartesian
    protected Direction axis ; // Direction
    protected Direction firstDirection; // Direction

    protected Direction secondDirection;

    public Axis2Placement3D(String name, CartesianPoint location, Direction axis, Direction direction,int lineNumber) {
        super(AP242Code.AXIS2_PLACEMENT_3D,name,lineNumber);
        this.location = location;
        this.axis = axis;
        this.firstDirection = direction;
        if (axis != null && direction != null){
            // Kreuzprodukt der zwei achsen gibt die Senkrechte
            List<Double> secondDirectionList = new ArrayList<>();
            secondDirectionList.add(axis.getDirectionRatios().get(1) * direction.getDirectionRatios().get(2) - axis.getDirectionRatios().get(2) * direction.getDirectionRatios().get(1)); // add X
            secondDirectionList.add(axis.getDirectionRatios().get(2) * direction.getDirectionRatios().get(0) - axis.getDirectionRatios().get(0) * direction.getDirectionRatios().get(2)); // add Y
            secondDirectionList.add(axis.getDirectionRatios().get(0) * direction.getDirectionRatios().get(1) - axis.getDirectionRatios().get(1) * direction.getDirectionRatios().get(0)); // add Z
            secondDirection = new Direction("", secondDirectionList, 0);

        }
    }

    public CartesianPoint getLocation() {
        return location;
    }

    public Direction getAxis() {
        return axis;
    }

    public Direction getFirstDirection() {
        return firstDirection;
    }

    public Direction getSecondDirection() {
        return secondDirection;
    }
}
