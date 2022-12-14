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
        IncrementalPointsD newVector = new IncrementalPointsD(endPoint.x() - startPoint.x(), endPoint.y() - startPoint.y(), endPoint.z() - startPoint.z());
        return Math.sqrt((newVector.x() * newVector.x()) + (newVector.y() * newVector.y()) + (newVector.z() * newVector.z()));
    }

    public static double distanceOfProjectionVectorAtoVecorB(IncrementalPointsD vectorA, IncrementalPointsD vectorB) {

        return distanceToPoint(vectorCrossMutiplication(vectorB, vectorA)) / distanceToPoint(vectorA);
    }

    public static IncrementalPointsD vectorCrossMutiplication(IncrementalPointsD vectorA, IncrementalPointsD vectorB) {
        return new IncrementalPointsD((vectorA.y() * vectorB.z() - vectorA.z() * vectorB.y()), (vectorA.z() * vectorB.x() - vectorA.x() * vectorB.z()), (vectorA.x() * vectorB.y() - vectorA.y() * vectorB.x()));
    }

    public static double distanceToPoint(IncrementalPointsD point) {
        return Math.sqrt(point.x() * point.x() + point.y() * point.y() + point.z() * point.z());
    }

    public static IncrementalPointsD inverseVector(IncrementalPointsD startPoint) {
        return new IncrementalPointsD(-startPoint.x(), -startPoint.y(), -startPoint.z());
    }

    public static IncrementalPointsD vectorAddition(IncrementalPointsD positionPoint, IncrementalPointsD axisVector) {
        return new IncrementalPointsD(positionPoint.x() + axisVector.x(), positionPoint.y() + axisVector.y(), positionPoint.z() + axisVector.z());
    }

    public static IncrementalPointsD vectorSubstraction(IncrementalPointsD startPoint, IncrementalPointsD positionPoint) {
        return new IncrementalPointsD(startPoint.x() - positionPoint.x(), startPoint.y() - positionPoint.y(), startPoint.z() - positionPoint.z());
    }
}
