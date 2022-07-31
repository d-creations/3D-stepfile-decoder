package ch.rcreations.stepdecoder.StepShapes.ProductDefinitionFormat;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import ch.rcreations.stepdecoder.StepShapes.StepShapes;

public class ProductDefinitionFormationWithSpecifiedSource extends ProductDefinitionFormation{

    String make_or_buySource;

    public ProductDefinitionFormationWithSpecifiedSource(String id, String description, StepShapes productReference,String make_or_buySource,int lineNumber) {
        super(id, description, productReference, lineNumber, AP242Code.PRODUCT_DEFINITION_FORMATION_WITH_SPECIFIED_SOURCE);
        this.make_or_buySource = make_or_buySource;
    }
}

