package ch.rcreations.stepdecoder.StepShapes.Face;

import ch.rcreations.stepdecoder.Config.StepConfig;
import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.Axis2Placement3D;
import ch.rcreations.stepdecoder.StepShapes.Curve.IncrementalPointsD;
import ch.rcreations.stepdecoder.StepShapes.Direction;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.Edge.Edge;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.Edge.OrientedEdge;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.FaceBound;
import ch.rcreations.stepdecoder.StepShapes.Point.CartesianPoint;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import ch.rcreations.stepdecoder.StepShapes.Surfaces.CylindricalSurface;
import ch.rcreations.stepdecoder.StepShapes.Surfaces.SphericalSurface;
import ch.rcreations.stepdecoder.StepShapes.Surfaces.Surface;
import javafx.scene.control.TreeItem;
import java.util.*;

import static ch.rcreations.stepdecoder.Config.MathCalculations.getCoordinatesInNewBasis;

public class AdvancedFace extends FaceSurface {


    public AdvancedFace(String name, Set<FaceBound> setOfFaces, Surface faceGeometrie, Boolean sameSense, int lineNumber) {
        super(name, setOfFaces, faceGeometrie, sameSense, lineNumber, AP242Code.ADVANCED_FACE);
        Map<String, String> preferencesMap = new HashMap<>();
        preferencesMap.put("Same Sense", sameSense.toString());
        preferencesMapList.add(preferencesMap);
        switch (faceGeometrie.getTyp()) {
            case PLANE -> renderPlan();
            case SPHERICAL_SURFACE ->
                    renderSphericalSurface(((SphericalSurface) faceGeometrie).getRadius(), ((SphericalSurface) faceGeometrie).getPosition());
            case CYLINDRICAL_SURFACE -> {
                for (FaceBound faceB : getFaceBound()) {
                    for (Edge edge : faceB.getEdgeLoop().getOrientedEdges()) {
                        renderACylinder(((CylindricalSurface) faceGeometrie).getRadius(), ((CylindricalSurface) faceGeometrie).getPosition(), edge);
                    }
                }
            }
        }
    }
    private void renderACylinder(double radius, Axis2Placement3D position, Edge edge) {
        // Place the Axis
        Direction axis = position.getAxis();
        Direction firstDirectionE = position.getFirstDirection();
        Direction secondDirectionE = position.getSecondDirection();
        IncrementalPointsD startPoint = getCoordinatesInNewBasis(edge.getStartX(), edge.getStartY(), edge.getStartZ(), firstDirectionE, secondDirectionE, axis);
        IncrementalPointsD endPoint = getCoordinatesInNewBasis(edge.getEndX(), edge.getEndY(), edge.getEndZ(), firstDirectionE, secondDirectionE, axis);
        int countLayers = 0; // count Layer if it is a surface like a ball // used in render Spherical Surface
        CreateAZylinderWithTriangles(radius, position, StepConfig.COUNTTRIANGLEPERLAYER, radius, radius, startPoint.z(), endPoint.z(), countLayers);
    }

    /**
     * render a Spherical Surface with triangles
     * the resolution of the Triangles is set in the Config file
     *
     * @param radius   radius of the ball
     * @param position position of the ball
     */
    private void renderSphericalSurface(double radius, Axis2Placement3D position) {
        // render a ball
        // Separate the Spherical in spherical layers Sektor    // h = höhe der Halbkugel  = a leg  r = hypotenuse
        int countLayers = StepConfig.COUNTLAYERS; // Half Spherical needs to be uneven
        int countTrianglePerLayer = StepConfig.COUNTTRIANGLEPERLAYER;
        //for each Layer calculate circumference
        for (int layer = 0; layer < countLayers; layer++) {
            double hLayerUP = radius * Math.cos(Math.toRadians(90.0 / countLayers * layer));
            double hLayerDOWN = radius * Math.cos(Math.toRadians(90.0 / countLayers * (layer + 1)));
            double distanceFromSphericalTop = radius - hLayerUP;
            double aLeg = radius - distanceFromSphericalTop;
            double layerRadiusUP = Math.sqrt(radius * radius - aLeg * aLeg);
            double distanceFromSphericalTopDown = (radius - hLayerDOWN);
            double aLegDown = radius - distanceFromSphericalTopDown;
            double layerRadiusDown = Math.sqrt(radius * radius - aLegDown * aLegDown);
            CreateAZylinderWithTriangles(radius, position, countTrianglePerLayer, layerRadiusUP, layerRadiusDown, aLeg, aLegDown, layer);
            CreateAZylinderWithTriangles(radius, position, countTrianglePerLayer, layerRadiusUP, layerRadiusDown, -aLeg, -aLegDown, layer);
        }
    }


    @Override
    public TreeItem<StepShapes> getTreeItem() {
        TreeItem<StepShapes> treeItem = new TreeItem<>(this);
        for (FaceBound faceBound : this.FaceBound) {
            treeItem.getChildren().add(faceBound.getTreeItem());
        }
        treeItem.getChildren().add(faceGeometrie.getTreeItem());
        return treeItem;
    }


    public void renderPlan() {
        for (FaceBound face : getFaceBound()) {
            //Draw a face With Triangles
            if (face.getEdgeLoop() != null) {
                List<OrientedEdge> tempAllEdges = new ArrayList<>(face.getEdgeLoop().getOrientedEdges());
                for (int edgeIndex = 0; edgeIndex < tempAllEdges.size(); edgeIndex++) {
                    for (int indexOfEdgeDrawingPoints = 0; indexOfEdgeDrawingPoints < tempAllEdges.get(edgeIndex).getEdgeElement().getEdgeDrawingPoints().size(); indexOfEdgeDrawingPoints++) {
                        //Draw a face With Triangles
                        int indexOfNextDrawingPoint = (indexOfEdgeDrawingPoints + 1 >= tempAllEdges.get(edgeIndex).getEdgeElement().getEdgeDrawingPoints().size()) ? 0 : indexOfEdgeDrawingPoints + 1;
                        IncrementalPointsD trianglePointA = tempAllEdges.get(0).getEdgeElement().getEdgeDrawingPoints().get(0);
                        IncrementalPointsD TrianglePointB = tempAllEdges.get(0).getEdgeElement().getEdgeDrawingPoints().get(indexOfNextDrawingPoint);
                        IncrementalPointsD TrianglePointC = tempAllEdges.get(edgeIndex).getEdgeElement().getEdgeDrawingPoints().get(indexOfEdgeDrawingPoints);
                        double x1 = (indexOfEdgeDrawingPoints == 0) ? tempAllEdges.get(0).getStartX() : trianglePointA.x();
                        double y1 = (indexOfEdgeDrawingPoints == 0) ? tempAllEdges.get(0).getStartY() : trianglePointA.y();
                        double z1 = (indexOfEdgeDrawingPoints == 0) ? tempAllEdges.get(0).getStartZ() : trianglePointA.z();
                        double x2 = TrianglePointB.x(); // tempAllEdges.get(0).getEndX();
                        double y2 = TrianglePointB.y(); //tempAllEdges.get(0).getEndY();
                        double z2 = TrianglePointB.z(); // tempAllEdges.get(0).getEndZ();
                        double x3 = tempAllEdges.get(edgeIndex).getStartX();
                        double y3 = tempAllEdges.get(edgeIndex).getStartY();
                        double z3 = tempAllEdges.get(edgeIndex).getStartZ();
                        double x4 = TrianglePointC.x();//tempAllEdges.get(edgeIndex).getEndX();
                        double y4 = TrianglePointC.y();//tempAllEdges.get(edgeIndex).getEndY();
                        double z4 = TrianglePointC.z();//tempAllEdges.get(edgeIndex).getEndZ();

                        DrawQuaderFace(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);

                        // Set Poin tto End Edge
                        x1 = tempAllEdges.get(tempAllEdges.size() - 1).getStartX();
                        y1 = tempAllEdges.get(tempAllEdges.size() - 1).getStartY();
                        z1 = tempAllEdges.get(tempAllEdges.size() - 1).getStartZ();
                        x2 = tempAllEdges.get(tempAllEdges.size() - 1).getEndX();
                        y2 = tempAllEdges.get(tempAllEdges.size() - 1).getEndY();
                        z2 = tempAllEdges.get(tempAllEdges.size() - 1).getEndZ();
                        x3 = TrianglePointC.x();//tempAllEdges.get(edgeIndex).getEndX();
                        y3 = TrianglePointC.y();//tempAllEdges.get(edgeIndex).getEndY();
                        z3 = TrianglePointC.z();//tempAllEdges.get(edgeIndex).getEndZ();
                        x4 = tempAllEdges.get(edgeIndex).getStartX();
                        y4 = tempAllEdges.get(edgeIndex).getStartY();
                        z4 = tempAllEdges.get(edgeIndex).getStartZ();
                        DrawQuaderFace(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
                    }
                }
            }
        }
    }

    private void DrawQuaderFace(Double x1, Double y1, Double z1, Double x2, Double y2, Double z2, Double x3, Double y3, Double z3, Double x4, Double y4, Double z4) {

        this.stepDrawLinesForTriangle.add(getCartesianPoint(x1, y1, z1));//p1   -1
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x2, y2, z2));//p2    -2
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x3, y3, z3));//p3      -END
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x1, y1, z1));//p4     -1
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x2, y2, z2));//P5      -2
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x4, y4, z4));//P6      -START
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x3, y3, z3));//p7     END
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x4, y4, z4));//p8     STAR
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x1, y1, z1));//p9        -1
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x3, y3, z3));//p10     END
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x4, y4, z4));//p11    sTART
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x2, y2, z2));//p12  -2
    }

    private CartesianPoint getCartesianPoint(Double x, Double y, Double z) {
        List<Double> directions = new ArrayList<>();
        directions.add(x);
        directions.add(y);
        directions.add(z);
        return new CartesianPoint("", directions, fileLineNumber);
    }


    /**
     * Draws a Cylinder with Triangles
     *
     * @param radius                Radius of the Cylinder
     * @param position              Position of the Cylinder
     * @param countTrianglePerLayer Resolution
     * @param layerRadiusUP         Radius to of Cylinder
     * @param layerRadiusDown       Radius down of the Cylinder
     * @param z1                    Position of the Top layer
     * @param z2                    Z Position of the down  Layer
     * @param indexIfCylinder       index of Layer if the Surface haves mor then 1 Cylinder
     */
    private void CreateAZylinderWithTriangles(double radius, Axis2Placement3D position, int countTrianglePerLayer, double layerRadiusUP, double layerRadiusDown, double z1, double z2, int indexIfCylinder) {
        Direction axis = position.getAxis();
        Direction firstDirectionE = position.getFirstDirection();
        Direction secondDirectionE = position.getSecondDirection();
        for (int y = 0; y < countTrianglePerLayer; y++) {
            double layerCircumferenceSequenzUP = (2 * Math.PI * radius) / countTrianglePerLayer;//      A    D
            double layerCircumferenceSequenzDown = (2 * Math.PI * radius) / countTrianglePerLayer;// B /\/ C
            double pointA = (layerCircumferenceSequenzUP / 2) * (indexIfCylinder % 2) + y * layerCircumferenceSequenzUP;
            double pointD = (layerCircumferenceSequenzUP / 2) * (indexIfCylinder % 2) + (y + 1) * layerCircumferenceSequenzUP;
            double pointB = -(layerCircumferenceSequenzDown / 2) * ((indexIfCylinder + 1) % 2) + layerCircumferenceSequenzDown * y;
            double pointC = -(layerCircumferenceSequenzDown / 2) * ((indexIfCylinder + 1) % 2) + layerCircumferenceSequenzDown * (y + 1);
            // Calculate Coordinates
            // Point A
            double angleBPointA = layerRadiusUP == 0 ? 0 : ((360 / (2 * Math.PI * layerRadiusUP)) * pointA);
            double angleBPointD = layerRadiusUP == 0 ? 0 : ((360 / (2 * Math.PI * layerRadiusUP)) * pointD);
            double angleBPointB = layerRadiusDown == 0 ? 0 : ((360 / (2 * Math.PI * layerRadiusDown)) * pointB);
            double angleBPointC = layerRadiusDown == 0 ? 0 : ((360 / (2 * Math.PI * layerRadiusDown)) * pointC);
            //Transformation X Y
            double x1 = layerRadiusUP * (-Math.sin(Math.toRadians(angleBPointA)));
            double y1 = layerRadiusUP * (Math.cos(Math.toRadians(angleBPointA)));
            double x4 = layerRadiusUP * (-Math.sin(Math.toRadians(angleBPointD)));
            double y4 = layerRadiusUP * (Math.cos(Math.toRadians(angleBPointD)));
            double x2 = layerRadiusDown * (-Math.sin(Math.toRadians(angleBPointB)));
            double y2 = layerRadiusDown * (Math.cos(Math.toRadians(angleBPointB)));
            double x3 = layerRadiusDown * (-Math.sin(Math.toRadians(angleBPointC)));
            double y3 = layerRadiusDown * (Math.cos(Math.toRadians(angleBPointC)));
            double z4 = z1;
            double z3 = z2;
            // Triangle 1
            double x1BasisN = y1 * firstDirectionE.getDirectionRatios().get(0) + x1 * secondDirectionE.getDirectionRatios().get(0) + z1 * axis.getDirectionRatios().get(0);
            double y1BasisN = y1 * firstDirectionE.getDirectionRatios().get(1) + x1 * secondDirectionE.getDirectionRatios().get(1) + z1 * axis.getDirectionRatios().get(1);
            double z1BasisN = y1 * firstDirectionE.getDirectionRatios().get(2) + x1 * secondDirectionE.getDirectionRatios().get(2) + z1 * axis.getDirectionRatios().get(2);
            double x2BasisN = y2 * firstDirectionE.getDirectionRatios().get(0) + x2 * secondDirectionE.getDirectionRatios().get(0) + z2 * axis.getDirectionRatios().get(0);
            double y2BasisN = y2 * firstDirectionE.getDirectionRatios().get(1) + x2 * secondDirectionE.getDirectionRatios().get(1) + z2 * axis.getDirectionRatios().get(1);
            double z2BasisN = y2 * firstDirectionE.getDirectionRatios().get(2) + x2 * secondDirectionE.getDirectionRatios().get(2) + z2 * axis.getDirectionRatios().get(2);
            double x3BasisN = y3 * firstDirectionE.getDirectionRatios().get(0) + x3 * secondDirectionE.getDirectionRatios().get(0) + z3 * axis.getDirectionRatios().get(0);
            double y3BasisN = y3 * firstDirectionE.getDirectionRatios().get(1) + x3 * secondDirectionE.getDirectionRatios().get(1) + z3 * axis.getDirectionRatios().get(1);
            double z3BasisN = y3 * firstDirectionE.getDirectionRatios().get(2) + x3 * secondDirectionE.getDirectionRatios().get(2) + z3 * axis.getDirectionRatios().get(2);
            double x4BasisN = y4 * firstDirectionE.getDirectionRatios().get(0) + x4 * secondDirectionE.getDirectionRatios().get(0) + z4 * axis.getDirectionRatios().get(0);
            double y4BasisN = y4 * firstDirectionE.getDirectionRatios().get(1) + x4 * secondDirectionE.getDirectionRatios().get(1) + z4 * axis.getDirectionRatios().get(1);
            double z4BasisN = y4 * firstDirectionE.getDirectionRatios().get(2) + x4 * secondDirectionE.getDirectionRatios().get(2) + z4 * axis.getDirectionRatios().get(2);
            drawTriangle(z1BasisN, z2BasisN, z3BasisN, x1BasisN, y1BasisN, x2BasisN, y2BasisN, x3BasisN, y3BasisN);
            // Triangle Opposite D B A
            drawTriangle(z3BasisN, z1BasisN, z4BasisN, x3BasisN, y3BasisN, x1BasisN, y1BasisN, x4BasisN, y4BasisN);


        }
    }

    /**
     * Drawing a Face with Triangles Create
     *
     * @param z1 position Triangle 1
     * @param z2 position Triangle 2
     * @param z3 position Triangle 3
     * @param x1 position Triangle 1
     * @param y1 position Triangle 1
     * @param x2 position Triangle 2
     * @param y2 position Triangle 2
     * @param x3 position Triangle 3
     * @param y3 position Triangle 3
     */
    private void drawTriangle(double z1, double z2, double z3, double x1, double y1, double x2, double y2, double x3, double y3) {
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x1, y1, z1));//p1   1
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x2, y2, z2));//p2
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x3, y3, z3));//p3

        this.stepDrawLinesForTriangle.add(getCartesianPoint(x2, y2, z2));//p2    2
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x3, y3, z3));//p3
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x1, y1, z1));//p1

        this.stepDrawLinesForTriangle.add(getCartesianPoint(x3, y3, z3));//p3
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x2, y2, z2));//p2
        this.stepDrawLinesForTriangle.add(getCartesianPoint(x1, y1, z1));//p1  //
    }
}
