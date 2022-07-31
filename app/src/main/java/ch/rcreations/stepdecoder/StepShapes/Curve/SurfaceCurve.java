package ch.rcreations.stepdecoder.StepShapes.Curve;

import ch.rcreations.stepdecoder.Config.StepConfig;
import ch.rcreations.stepdecoder.StepShapes.*;
import ch.rcreations.stepdecoder.StepShapes.Curve.Conic.Circle;
import ch.rcreations.stepdecoder.StepShapes.Point.CartasianAxisE;
import ch.rcreations.stepdecoder.StepShapes.Point.CartesianPoint;
import ch.rcreations.stepdecoder.StepShapes.Vertex.VertexPoint;
import javafx.scene.control.TreeItem;
import java.util.List;
import java.util.Set;
import static ch.rcreations.stepdecoder.Config.StepConfig.CIRCALRESOLUTION;


/**
 * <p>
 * <p>
 *  Step object Surface Geometric a subclass of {@link Curve}
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */

public class SurfaceCurve extends Curve {
    protected Curve curve;
    protected Set<StepShapes> items;
    protected PreferredSurfaceCurveRepresentation representation;

    /**
     * Constructor of a Surface Curve
     * @param name name of the Surface Curve
     * @param curve 3d Curve
     * @param items a {@link Set} of {@link StepShapes}
     * @param representation is the {@link PreferredSurfaceCurveRepresentation}
     * @param ap242Code {@link AP242Code} Step Code
     * @param lineNumber line number in the Step File
     */
    public SurfaceCurve(String name, Curve curve, Set<StepShapes> items, PreferredSurfaceCurveRepresentation representation, AP242Code ap242Code, int lineNumber) {
        super(name, lineNumber, ap242Code);
        this.curve = curve;
        this.items = items;
        this.representation = representation;
    }

    /**
     * Constructor of a Surface Curve
     * @param name name of the Surface Curve
     * @param curve 3d Curve
     * @param items a {@link Set} of {@link StepShapes}
     * @param representation is the {@link PreferredSurfaceCurveRepresentation}
     * @param lineNumber line number in the Step File
     */
    public SurfaceCurve(String name, Curve curve, Set<StepShapes> items, PreferredSurfaceCurveRepresentation representation, int lineNumber) {
        this(name, curve, items, representation, AP242Code.SURFACE_CURVE, lineNumber);
    }

    @Override
    public List<IncrementalPointsD> getEdgeDrawingPoints(VertexPoint start, VertexPoint end) {
        switch (curve.getTyp()) {
            case LINE -> calculateLineGeometrie(start, end);
            case CIRCLE -> calculateCurveGeometrie(start, end);
        }
        return super.getEdgeDrawingPoints(start, end);
    }


    /**
     * Calculates the Line Geometrie
     * @param start Start Point of the Geometric
     * @param end End Point of the Geometrie
     */
    private void calculateLineGeometrie(VertexPoint start, VertexPoint end) {
        StepConfig.printMessage("Line Geometrie");
        this.edgeDrawingPoints.add(new IncrementalPointsD(end.ifExistGivePoint().getPoint().get(CartasianAxisE.X), end.ifExistGivePoint().getPoint().get(CartasianAxisE.Y), end.ifExistGivePoint().getPoint().get(CartasianAxisE.Z)));

    }

    private void calculateCurveGeometrie(VertexPoint start, VertexPoint end) {
        StepConfig.printMessage("circle geometrie");
        try {
            Circle circle = (Circle) curve;
            Axis2Placement3D position = circle.getPosition();
            CartesianPoint middlePoint = position.getLocation();
            Direction axis = position.getAxis();
            Direction firstDirectionE = position.getFirstDirection();
            Direction secondDirectionE = position.getSecondDirection();
            //Basiswechsel   Translation  Start Point And Endpoint to 0,0,0 Midpoint
            double endXBasisN ,endYBasisN, endZBasisN ;
             endXBasisN = ( end.ifExistGivePoint()).getPoint().get(CartasianAxisE.X) - middlePoint.getPoint().get(CartasianAxisE.X);
            endYBasisN = ( end.ifExistGivePoint()).getPoint().get(CartasianAxisE.Y) - middlePoint.getPoint().get(CartasianAxisE.Y);
            endZBasisN = (end.ifExistGivePoint()).getPoint().get(CartasianAxisE.Z) - middlePoint.getPoint().get(CartasianAxisE.Z);
            //drawing the midpoint for the Triangle
            this.edgeDrawingPoints.add(new IncrementalPointsD(middlePoint.getPoint().get(CartasianAxisE.X), middlePoint.getPoint().get(CartasianAxisE.Y), middlePoint.getPoint().get(CartasianAxisE.Z)));

            // move in   1  direction = x   2 Direction = y  3 direction = z   as symbol e
            int i = 0;
            double DistanceToEndPoint = 0;
            while (i<360 && ((DistanceToEndPoint > (2*CIRCALRESOLUTION) && i > (5*CIRCALRESOLUTION))|| i <  50*CIRCALRESOLUTION)) {
                double x = circle.getRadius() * (-Math.sin(Math.toRadians(i)));
                double y = circle.getRadius() * (Math.cos(Math.toRadians(i)));
                double z = 0;
                //Basiswechsel Translation mit dem Einheitsnormalvektor
                double yBasisN = y * firstDirectionE.getDirectionRatios().get(1) + x * secondDirectionE.getDirectionRatios().get(1) + z * axis.getDirectionRatios().get(1);
                double xBasisN = y * firstDirectionE.getDirectionRatios().get(0) + x * secondDirectionE.getDirectionRatios().get(0) + z * axis.getDirectionRatios().get(0);
                double zBasisN = y * firstDirectionE.getDirectionRatios().get(2) + x * secondDirectionE.getDirectionRatios().get(2) + z * axis.getDirectionRatios().get(2);

                xBasisN += middlePoint.getPoint().get(CartasianAxisE.X);
                yBasisN += middlePoint.getPoint().get(CartasianAxisE.Y);
                zBasisN += middlePoint.getPoint().get(CartasianAxisE.Z);
                this.edgeDrawingPoints.add(new IncrementalPointsD(xBasisN, yBasisN, zBasisN));

                // Calculate Distance the new Point Distance to Endpoint
                DistanceToEndPoint = Math.sqrt((-xBasisN + endXBasisN) * (-xBasisN + endXBasisN) + (-yBasisN + endYBasisN) * (-yBasisN + endYBasisN) + (-zBasisN + endZBasisN) * (-zBasisN + endZBasisN));
                i += CIRCALRESOLUTION;
            }
        } catch (Exception e) {
            StepConfig.printMessage(" Could not draw the Points ");
            StepConfig.printMessage(e.getMessage());
        }
    }

    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        treeItem.getChildren().add(curve.getTreeItem());
        for (StepShapes item : items) {
            treeItem.getChildren().add(item.getTreeItem());
        }
        return treeItem;
    }
}
