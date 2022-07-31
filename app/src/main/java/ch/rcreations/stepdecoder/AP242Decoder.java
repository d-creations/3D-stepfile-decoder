package ch.rcreations.stepdecoder;

import ch.rcreations.stepdecoder.Config.StepConfig;
import ch.rcreations.stepdecoder.StepShapes.*;
import ch.rcreations.stepdecoder.StepShapes.ConnectedFaceSet.ClosedShell;
import ch.rcreations.stepdecoder.StepShapes.Curve.Conic.Circle;
import ch.rcreations.stepdecoder.StepShapes.Curve.StepLine;
import ch.rcreations.stepdecoder.StepShapes.Curve.Curve;
import ch.rcreations.stepdecoder.StepShapes.Curve.SeamCurve;
import ch.rcreations.stepdecoder.StepShapes.Curve.SurfaceCurve;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.Edge.EdgeCurve;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.Edge.OrientedEdge;
import ch.rcreations.stepdecoder.StepShapes.Face.AdvancedFace;
import ch.rcreations.stepdecoder.StepShapes.Face.Face;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.EdgeLoop;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.FaceBound;
import ch.rcreations.stepdecoder.StepShapes.FaceBoundLoop.FaceOuterBound;
import ch.rcreations.stepdecoder.StepShapes.Point.CartesianPoint;
import ch.rcreations.stepdecoder.StepShapes.ProductDefinitionFormat.ProductDefinitionFormation;
import ch.rcreations.stepdecoder.StepShapes.ProductDefinitionFormat.ProductDefinitionFormationWithSpecifiedSource;
import ch.rcreations.stepdecoder.StepShapes.Surfaces.CylindricalSurface;
import ch.rcreations.stepdecoder.StepShapes.Surfaces.Plane;
import ch.rcreations.stepdecoder.StepShapes.Surfaces.SphericalSurface;
import ch.rcreations.stepdecoder.StepShapes.Surfaces.Surface;
import ch.rcreations.stepdecoder.StepShapes.Vertex.SimpleVertexD;
import ch.rcreations.stepdecoder.StepShapes.Vertex.Vertex;
import ch.rcreations.stepdecoder.StepShapes.Vertex.VertexPoint;
import java.util.*;

/**
 * <p>
 * <p>
 * The Decoder   reads each Line of the Step file and decode it
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */

public class AP242Decoder {
    Map<Integer, String> dataMap;
    List<StepShapes> stepShapes = new ArrayList<>();
    List<ClosedShell> drawingShells = new ArrayList<>();

    /**
     * But a Map with the #-- Code of the Step File and the Data behind e
     * @param dataMap Map with the Code Number and the Data of the Code
     */
    public AP242Decoder(Map<Integer, String> dataMap) {
        this.dataMap = dataMap;
    }

    public void decode() {
        dataMap.keySet().stream().filter( k ->(decodeKey(dataMap.get(k))[0].equals("SHAPE_DEFINITION_REPRESENTATION"))).forEach(k -> calculateDecoding(dataMap.get(k),k));
        StepConfig.printMessage("END");
    }

    private String[] decodeKey(String value) {
        String[] code = new String[2];
        if (value.startsWith("(GEOMETRIC_REPRESENTATION_CONTEXT")) {
            code[0] = value;
            code[1] = "";
        } else {
            String topCode = value.substring(0, value.indexOf("("));
            String contend = value.substring(value.indexOf("(") + 1, value.length() - 1);
            code[0] = topCode;
            code[1] = contend;
        }
        return code;
    }

    private StepShapes calculateDecoding(String value,int lineNumber) {
        String[] received = decodeKey(value);
        String topCode = received[0];
        String contend = received[1];
        String[] numbers = contend.split(",");
        String name = numbers[0].replace("\"","");
        switch (topCode) {
            case "SHAPE_DEFINITION_REPRESENTATION" -> {
                int number1 = Integer.parseInt(numbers[0].replace("#", ""));
                int number2 = Integer.parseInt(numbers[1].replace("#", ""));
                String code1 = dataMap.get(number1);
                String code2 = dataMap.get(number2);
                ShapeDefinitionRepesentation s = new ShapeDefinitionRepesentation(calculateDecoding(code1,number1), calculateDecoding(code2,number2),lineNumber);
                stepShapes.add(s);
                return s;
            }
            case "ADVANCED_BREP_SHAPE_REPRESENTATION" -> {
                int number1 = Integer.parseInt(numbers[numbers.length - 1].replace("#", ""));
                String code1 = dataMap.get(number1);
                Set<StepShapes> items = new HashSet<>();
                for (int i = 1; i < numbers.length - 1; i++) {
                    int number = Integer.parseInt(numbers[i].replace("#", "").replace("(", "").replace(")", ""));
                    String code = dataMap.get(number);
                    items.add(calculateDecoding(code,number));
                }
                return new AdvancedBrepShapeRepresentation(name, items, calculateDecoding(code1,number1),lineNumber);
            }
            case "PRODUCT_DEFINITION_SHAPE" -> {
                String description = numbers[1];
                int codeLineNumber = Integer.parseInt(numbers[2].replace("#", ""));
                String code1 = dataMap.get(codeLineNumber);
                return new ProductDefinitionShape(name, description, calculateDecoding(code1,codeLineNumber),lineNumber);
            }
            case "PRODUCT_DEFINITION" -> {
                String description = numbers[1];
                int code1LineNumber = Integer.parseInt(numbers[2].replace("#", ""));
                int code2LineNumber =  Integer.parseInt(numbers[3].replace("#", ""));
                String code1 = dataMap.get(code1LineNumber);
                String code2 = dataMap.get(code2LineNumber);
                return new ProductDefinition(name, description, calculateDecoding(code1,code1LineNumber), calculateDecoding(code2,code2LineNumber),lineNumber);
            }
            case "PRODUCT" -> {
                String id = numbers[0];
                name = numbers[1];
                String description = numbers[2];
                int code1LineNumber  =  Integer.parseInt(numbers[3].replace("#", "").replace("(", "").replace(")", ""));
                String code1 = dataMap.get(code1LineNumber);
                Set<StepShapes> frameSet = new HashSet<>();
                calculateDecoding(code1,code1LineNumber);
                return new Product(id, name, description, frameSet,lineNumber);
            }
            case "PRODUCT_DEFINITION_FORMATION" -> {
                String description = numbers[1];
                int code1LineNumber = Integer.parseInt(numbers[2].replace("#", ""));
                String code1 = dataMap.get(code1LineNumber);
                return new ProductDefinitionFormation(name, description, calculateDecoding(code1,code1LineNumber),lineNumber);
            }
            case "PRODUCT_DEFINITION_FORMATION_WITH_SPECIFIED_SOURCE" -> {
                String description = numbers[1];
                int code1LineNumber = Integer.parseInt(numbers[2].replace("#", ""));
                String source = numbers[3];
                String code1 = dataMap.get(code1LineNumber);
                return new ProductDefinitionFormationWithSpecifiedSource(name, description, calculateDecoding(code1,code1LineNumber),source,lineNumber);
            }
            case "PRODUCT_DEFINITION_CONTEXT" -> {
                String disciplineType = numbers[2];
                int code1LineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                String code1 = dataMap.get(code1LineNumber);
                return new ProductDefinitionContext(name, calculateDecoding(code1,code1LineNumber), disciplineType,lineNumber);
            }
            case "PRODUCT_CONTEXT" -> {
                String disciplineType = numbers[2];
                int code1LineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                String code1 = dataMap.get(code1LineNumber);
                return new ProductContext(name, calculateDecoding(code1,code1LineNumber), disciplineType,lineNumber);
            }
            case "APPLICATION_CONTEXT" -> {
                return new ApplicationContext(name,lineNumber);
            }
            case "AXIS2_PLACEMENT_3D" -> {
                int locationNameNumber = Integer.parseInt(numbers[1].replace("#", ""));
                int axisLineNumber = Integer.parseInt(numbers[2].replace("#", ""));
                int refDirectionNumber = Integer.parseInt(numbers[3].replace("#", ""));
                String location = dataMap.get(locationNameNumber);
                String axis1 = dataMap.get(axisLineNumber);
                String refDirection = dataMap.get(refDirectionNumber);
                try {
                    return new Axis2Placement3D(name, (CartesianPoint) calculateDecoding(location,locationNameNumber), (Direction) calculateDecoding(axis1,axisLineNumber),(Direction) calculateDecoding(refDirection,refDirectionNumber),lineNumber);
                } catch (Exception e) {
                    StepConfig.printError("AXIS2_PLACEMENT_3D parameter Error");
                    return null;
                }
            }
            case "CARTESIAN_POINT" -> {
                return new CartesianPoint(name, directionRaitiosToList(numbers),lineNumber);
            }
            case "MANIFOLD_SOLID_BREP" -> {
                int shapeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                String shapeData = dataMap.get(shapeLineNumber);
                return new MainfoldSolidBrep(name, calculateDecoding(shapeData,shapeLineNumber),lineNumber);
            }
            case "DIRECTION" -> {
                List<Double> directionRatios = directionRaitiosToList(numbers);
                return new Direction(name, directionRatios,lineNumber);
            }
            case "CLOSED_SHELL" -> {
                try {
                    Set<Face> setOfFaces = getFacesSet(numbers);
                    ClosedShell closedShell = new ClosedShell(name, setOfFaces,lineNumber);
                    drawingShells.add(closedShell);
                    return closedShell;
                }catch (Exception e) {
                    StepConfig.printError("CLOSED_SHELL ERROR");
                    StepConfig.printError(e.getMessage());
                    return null;
                }
            }
            case "ADVANCED_FACE" -> {
                int codeNumber = Integer.parseInt(numbers[numbers.length - 2].replace("#", "").replace("(", "").replace(")", ""));
                String code = dataMap.get(codeNumber);
                try {
                    Surface faceGeometrie = (Surface) calculateDecoding(code,codeNumber);
                    Boolean sameSense = !Objects.equals(numbers[numbers.length - 1], ".F.");
                    return new AdvancedFace(name, getItemsSet(numbers), faceGeometrie, sameSense,lineNumber);
                } catch (Exception e) {StepConfig.printError("ADVANCED FACE faceGeometrie was not a Surface"+code + codeNumber);
                    StepConfig.printError(e.getMessage());
                    return null;
                }
            }
            case "FACE_BOUND" -> {
                try {
                    int codeNumber = Integer.parseInt(numbers[1].replace("#", ""));
                    String code = dataMap.get(codeNumber);
                    EdgeLoop faceLoop = (EdgeLoop)calculateDecoding(code,codeNumber);
                    Boolean orientation = !Objects.equals(numbers[numbers.length - 1], ".F.");
                    return new FaceBound(name, faceLoop, orientation,lineNumber);
                } catch (Exception e) {
                    StepConfig.printError("FaceBound failed");
                    StepConfig.printError(e.getMessage());
                    return null;
                }
            }
            case "FACE_OUTER_BOUND" -> {
                try {
                    int codeNumber = Integer.parseInt(numbers[1].replace("#", ""));
                    String code = dataMap.get(codeNumber);
                    EdgeLoop faceLoop = (EdgeLoop)calculateDecoding(code,codeNumber);
                    Boolean orientation = !Objects.equals(numbers[numbers.length - 1], ".F.");
                    return new FaceOuterBound(name, faceLoop, orientation,lineNumber);
                } catch (Exception e) {
                    StepConfig.printError("FaceOuterBound failed");
                    StepConfig.printError(e.getMessage());
                    return null;
                }
            }
            case "EDGE_LOOP" -> {
                try {
                return new EdgeLoop(name,getFacesSet(numbers),lineNumber);
            } catch (Exception e) {
                StepConfig.printError("EDGE_LOOP failed");
                StepConfig.printError(e.getMessage());
                return null;
            }
            }
            case "ORIENTED_EDGE" -> {
                try {
                    String edgeStart = numbers[1];
                    String edgeEnd = numbers[2];
                    int codeNumber = Integer.parseInt(numbers[3].replace("#", ""));
                    String code = dataMap.get(codeNumber);
                    EdgeCurve edgeElement = (EdgeCurve) calculateDecoding(code,codeNumber);
                    Boolean orientation = !Objects.equals(numbers[numbers.length - 1], ".F.");
                    if (edgeElement != null) {
                        return new OrientedEdge(name, new SimpleVertexD(edgeStart, lineNumber), new SimpleVertexD(edgeEnd, lineNumber), edgeElement, orientation, lineNumber);
                    }else return null;
                    } catch (Exception e) {
                    StepConfig.printError("ORIENTATED EDGE Vertex or EdgeElement was Wrong" + "Exception" + e.getMessage());
                    return null;
                }
            }
            case "EDGE_CURVE" -> {
                try {
                    int code1LineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                    int code2LineNumber = Integer.parseInt(numbers[2].replace("#", ""));
                    int code3LineNumber = Integer.parseInt(numbers[3].replace("#", ""));
                    String code1 = dataMap.get(code1LineNumber);
                    String code2 = dataMap.get(code2LineNumber);
                    String code3 = dataMap.get(code3LineNumber);
                    Vertex edgeStart = (Vertex) calculateDecoding(code1,code1LineNumber);//vertex
                    Vertex edgeEnd = (Vertex) calculateDecoding(code2,code2LineNumber);//vertex
                    Curve edgeGeometrie = (Curve) calculateDecoding(code3,code3LineNumber);//vertex
                    Boolean sameSense = !Objects.equals(numbers[numbers.length - 1], ".F.");
                    assert edgeStart != null;
                    assert edgeEnd != null;
                    if ( (edgeStart.getTyp() == AP242Code.VERTEX_POINT && edgeEnd.getTyp() == AP242Code.VERTEX_POINT)) {
                        return new EdgeCurve(name, (VertexPoint) calculateDecoding(code1,code1LineNumber), (VertexPoint) calculateDecoding(code2,code2LineNumber), edgeGeometrie, sameSense,lineNumber);
                    }else {
                        return new EdgeCurve(name, edgeStart, edgeEnd, edgeGeometrie, sameSense, lineNumber);
                    }
                    } catch (Exception e) {
                    StepConfig.printError("PLANE position was not a Position");
                    return null;
                }
            }
            case "VERTEX_POINT" -> {
                try {
                    int codeNumber = Integer.parseInt(numbers[1].replace("#", ""));
                    String code = dataMap.get(codeNumber);
                    StepShapes point = calculateDecoding(code,codeNumber);//vertex
                    assert point != null;
                    if (point.getTyp() == AP242Code.CARTESIAN_POINT)
                        return new VertexPoint(name, (CartesianPoint) calculateDecoding(code,codeNumber),lineNumber);
                    if (point.getTyp() == AP242Code.POINT)
                        return new VertexPoint(name, (CartesianPoint) calculateDecoding(code,codeNumber),lineNumber);
                    throw new IllegalArgumentException(" Point ");
                } catch (Exception e) {
                    StepConfig.printError("PLANE position was not a Position");
                    return null;
                }
            }
            case "VECTOR" -> {
                int codeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                String code = dataMap.get(codeLineNumber);
                StepShapes orientation = calculateDecoding(code,codeLineNumber);//vertex
                Double length = Double.valueOf(dataMap.get(Integer.valueOf(numbers[1].replace("#", ""))));
                return new StepVector(name, orientation, length,lineNumber);
            }

            case "LINE" -> {
                int codeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                String code = dataMap.get(codeLineNumber);
                StepShapes coordinateSystem = calculateDecoding(code,codeLineNumber);//vertex
                code = dataMap.get(codeLineNumber);
                StepShapes vector = calculateDecoding(code,codeLineNumber);//vertex
                return new StepLine(name, coordinateSystem, vector,lineNumber);
            }
            case "PLANE" -> {
                int codeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                String code = dataMap.get(codeLineNumber);
                try {
                    Axis2Placement3D position = (Axis2Placement3D) calculateDecoding(code,codeLineNumber);//vertex
                    return new Plane(name, position,lineNumber);
                } catch (Exception e) {
                    StepConfig.printError("PLANE position was not a Position");
                    return null;
                }
            }
            case "SPHERICAL_SURFACE" -> {
                int PositionCodeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                double radius = Double.parseDouble(numbers[2].replace("#", ""));
                String PositionCode = dataMap.get(PositionCodeLineNumber);
                try {
                    Axis2Placement3D position = (Axis2Placement3D) calculateDecoding(PositionCode,PositionCodeLineNumber);//vertex
                    return new SphericalSurface(name, position,radius,lineNumber);
                } catch (Exception e) {
                    StepConfig.printError("SPHERICAL SURFACE CONSTRUCTION FAILED Construction Failed");
                    StepConfig.printError(e.getMessage());
                    return null;
                }
            }

            case "CYLINDRICAL_SURFACE" -> {
                int PositionCodeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                double radius = Double.parseDouble(numbers[2].replace("#", ""));
                String PositionCode = dataMap.get(PositionCodeLineNumber);
                try {
                    Axis2Placement3D position = (Axis2Placement3D) calculateDecoding(PositionCode,PositionCodeLineNumber);//vertex
                    return new CylindricalSurface(name, position,radius,lineNumber);
                } catch (Exception e) {
                    StepConfig.printError("Cylindrical Construction Failed");
                    StepConfig.printError(e.getMessage());
                    return null;
                }
            }

            case "CIRCLE" -> {
                int PositionCodeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                double radius = Double.parseDouble(numbers[2].replace("#", ""));
                String PositionCode = dataMap.get(PositionCodeLineNumber);
                try {
                    Axis2Placement3D position = (Axis2Placement3D) calculateDecoding(PositionCode,PositionCodeLineNumber);//vertex
                    return new Circle(name, position,radius,lineNumber);
                } catch (Exception e) {
                    StepConfig.printError("CIRCLE Construction Failed");
                    StepConfig.printError(e.getMessage());
                    return null;
                }
            }

            case "SEAM_CURVE" ->  {
                int codeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                String code = dataMap.get(codeLineNumber);
                StepShapes curve = calculateDecoding(code,codeLineNumber);//vertex
                Set<StepShapes> items = new HashSet<>();
                for (int i = 2; i < numbers.length - 1; i++) {
                    int codeNumber = Integer.parseInt(numbers[i].replace("#", "").replace("(", "").replace(")", ""));
                    code = dataMap.get(codeNumber);
                    items.add(calculateDecoding(code,codeNumber));
                }
                PreferredSurfaceCurveRepresentation representation = getPreferredEnum(numbers[numbers.length - 1].replace("#", ""));
                return new SeamCurve(name, (Curve) curve, items, representation,lineNumber);
            }

            case "PCURVE" -> {
                int codeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                String code = dataMap.get(codeLineNumber);
                StepShapes basis = calculateDecoding(code,codeLineNumber);//vertex
                code = dataMap.get(codeLineNumber);
                StepShapes curve = calculateDecoding(code,codeLineNumber);//vertex
                return new Pcurve(name, basis, curve,lineNumber);
            }
            case "DEFINITIONAL_REPRESENTATION" -> {
                Set<StepShapes> items = getItemsSet(numbers);
                int codeLineNumber = Integer.parseInt(numbers[numbers.length - 1].replace("#", ""));
                String code = dataMap.get(codeLineNumber);
                StepShapes representationContext = calculateDecoding(code,codeLineNumber);//vertex
                return new DefinitionalRepresentation(name, items, representationContext,lineNumber);
            }
            case "SURFACE_CURVE" -> {
                int codeLineNumber = Integer.parseInt(numbers[1].replace("#", ""));
                String code = dataMap.get(codeLineNumber);
                StepShapes curve = calculateDecoding(code,codeLineNumber);//vertex
                Set<StepShapes> items = new HashSet<>();
                for (int i = 2; i < numbers.length - 1; i++) {
                    int codeNumber = Integer.parseInt(numbers[i].replace("#", "").replace("(", "").replace(")", ""));
                    code = dataMap.get(codeNumber);
                    items.add(calculateDecoding(code,codeNumber));
                }
                PreferredSurfaceCurveRepresentation representation = getPreferredEnum(numbers[numbers.length - 1].replace("#", ""));
                return new SurfaceCurve(name, (Curve) curve, items, representation,lineNumber);
            }

            default -> {
                StepConfig.printError("CODE NOT FOUND" + value+ lineNumber);
                return null;
            }
        }

    }

    private PreferredSurfaceCurveRepresentation getPreferredEnum(String s) {
        switch (s) {
            case ".PCURVE_3D." -> {
                return PreferredSurfaceCurveRepresentation.CURVE3D;
            }
            case ".PCURVE_S2." -> {
                return PreferredSurfaceCurveRepresentation.pCURVE_s2;
            }
            default -> {
                return PreferredSurfaceCurveRepresentation.PCURVE_s1;
            }
        }
    }

    private <T> Set<T> getItemsSet(String[] numbers) {
        Set<T> items = new HashSet<>();
        for (int i = 1; i < numbers.length - 2; i++) {
            int codeLineNumber = Integer.parseInt(numbers[i].replace("#", "").replace("(", "").replace(")", ""));
            String code = dataMap.get(codeLineNumber);
            items.add((T) calculateDecoding(code,codeLineNumber));
        }
        return items;
    }

    private <T> Set<T> getFacesSet(String[] numbers) {
        Set<T> setOfFaces = new HashSet<>();
        for (int i = 1; i < numbers.length; i++) {
            int codeLineNumber = Integer.parseInt(numbers[i].replace("#", "").replace("(", "").replace(")", ""));
            String code = dataMap.get(codeLineNumber);
            setOfFaces.add((T)calculateDecoding(code,codeLineNumber));
        }
        return setOfFaces;
    }

    private List<Double> directionRaitiosToList(String[] numbers) {
        List<Double> directionRatios = new ArrayList<>();
        for (int i = 1; i < numbers.length; i++) {
            Double code = Double.valueOf(numbers[i].replace("#", "").replace("(", "").replace(")", ""));
            directionRatios.add(code);
        }
        return directionRatios;
    }


    public List<ClosedShell> getDrawingShells() {
        return drawingShells;
    }

    public List<StepShapes> getStepShapes() {
        return stepShapes;
    }
}
