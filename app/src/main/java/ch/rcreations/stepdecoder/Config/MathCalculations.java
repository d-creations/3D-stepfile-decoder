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
     *
     * @param x                x Value
     * @param y                y Value
     * @param z                z Value
     * @param firstDirectionE  new Room x Direction
     * @param secondDirectionE new Room y Direction
     * @param axis             new Room z Direction
     * @return new Point {@link IncrementalPointsD}
     */
    public static IncrementalPointsD getCoordinatesInNewBasis(double x, double y, double z, Direction firstDirectionE, Direction secondDirectionE, Direction axis) {
        double startX = x * firstDirectionE.getDirectionRatios().get(0) + y * secondDirectionE.getDirectionRatios().get(0) + z * axis.getDirectionRatios().get(0);
        double startY = x * firstDirectionE.getDirectionRatios().get(1) + y * secondDirectionE.getDirectionRatios().get(1) + z * axis.getDirectionRatios().get(1);
        double startZ = x * firstDirectionE.getDirectionRatios().get(2) + y * secondDirectionE.getDirectionRatios().get(2) + z * axis.getDirectionRatios().get(2);

        return new IncrementalPointsD(startX, startY, startZ);

    }

    public static double getDistanceBetweenPoints(IncrementalPointsD startPoint, IncrementalPointsD endPoint) {
        return Math.sqrt((endPoint.x() - startPoint.x()) * (endPoint.x() - startPoint.x()) + (endPoint.y() - startPoint.y()) * (endPoint.y() - startPoint.y()) + (endPoint.z() - startPoint.z()) * (endPoint.z() - startPoint.z()));
    }

    public static double distanceOfProjectionVectorAtoVecorB(IncrementalPointsD vectorA, IncrementalPointsD vectorB) {

        return distanceToPoint(vectorCrossMutiplication(vectorB,vectorA)) / distanceToPoint(vectorA);
    }

    public static IncrementalPointsD vectorCrossMutiplication(IncrementalPointsD vectorA, IncrementalPointsD vectorB) {
        return new IncrementalPointsD((vectorA.y() * vectorB.z() - vectorA.z() * vectorB.y()), (vectorA.z() * vectorB.x() - vectorA.x() * vectorB.z()), (vectorA.x() * vectorB.y() - vectorA.y() * vectorB.x()));
    }

    public static double distanceToPoint(IncrementalPointsD point) {
        return Math.sqrt(point.x() * point.x() + point.y() * point.y() + point.z() * point.z());
    }
}
