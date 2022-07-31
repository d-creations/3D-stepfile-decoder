package ch.rcreations.stepdecoder.StepShapes.Curve;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.PreferredSurfaceCurveRepresentation;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;
import java.util.Set;

/**
 * <p>
 * <p> For a Seam Curve Geometrie
 * Is a subClass of {@link SurfaceCurve}
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */

public class SeamCurve extends SurfaceCurve{


     /**
     * Constructor of a SeamCurve
     * @param name name of the SeamCurve
     * @param curve 3d Curve
     * @param items a {@link Set} of {@link StepShapes}
     * @param representation is the {@link PreferredSurfaceCurveRepresentation}
     * @param lineNumber line number in the Step File
     */
    public SeamCurve(String name, Curve curve, Set<StepShapes> items, PreferredSurfaceCurveRepresentation representation, int lineNumber) {
        super(name, curve, items, representation, AP242Code.SEAM_CURVE ,lineNumber);
    }
}
