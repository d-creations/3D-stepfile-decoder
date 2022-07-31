package ch.rcreations.stepdecoder.Config;

import ch.rcreations.stepdecoder.StepShapes.Curve.IncrementalPointsD;
import ch.rcreations.stepdecoder.StepShapes.Direction;

/**
 * <p>
 * <p>
 * This is a static Class with calculations that are used in this Class
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */
public class MathCalculations {

    /**
     * Gets the Coordinates Tranformt to a new Base
     * @param x x Value
     * @param y y Value
     * @param z z Value
     * @param firstDirectionE   new Room x Direction
     * @param secondDirectionE   new Room y Direction
     * @param axis  new Room z Direction
     * @return  new Point {@link IncrementalPointsD}
     */
    public static IncrementalPointsD getCoordinatesInNewBasis(double x, double y, double z, Direction firstDirectionE, Direction secondDirectionE, Direction axis){
        double startX = y * firstDirectionE.getDirectionRatios().get(0) + x * secondDirectionE.getDirectionRatios().get(0) + z * axis.getDirectionRatios().get(0);
        double startY = y * firstDirectionE.getDirectionRatios().get(1) + x * secondDirectionE.getDirectionRatios().get(1) + z * axis.getDirectionRatios().get(1);
        double startZ = y * firstDirectionE.getDirectionRatios().get(2) + x * secondDirectionE.getDirectionRatios().get(2) + z * axis.getDirectionRatios().get(2);

        return new IncrementalPointsD(startX,startY,startZ);

    }

}
