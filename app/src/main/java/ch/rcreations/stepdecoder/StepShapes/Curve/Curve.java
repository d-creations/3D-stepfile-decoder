package ch.rcreations.stepdecoder.StepShapes.Curve;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import ch.rcreations.stepdecoder.StepShapes.Vertex.VertexPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * <p>
 * Is a Class for a Edge geometer  Shells
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */

public class Curve extends StepShapes {

    protected List<IncrementalPointsD> edgeDrawingPoints = new ArrayList<>();

    /**
     *
     * @param name name of the Curve
     * @param fileLineNumber line number in Step file
     */
    public Curve(String name, int fileLineNumber) {
        this(name, fileLineNumber,AP242Code.CURVE);
    }

    /**
     *
     * @param name name of the Curve
     * @param fileLineNumber line number in Step file
     * @param ap242Code Step Code {@link AP242Code}
     */
    protected Curve(String name, int fileLineNumber,AP242Code ap242Code) {
        super(ap242Code, name, fileLineNumber);
    }




    public List<IncrementalPointsD> getEdgeDrawingPoints(VertexPoint start,VertexPoint end) {
        return Collections.unmodifiableList(edgeDrawingPoints);
    }


}
