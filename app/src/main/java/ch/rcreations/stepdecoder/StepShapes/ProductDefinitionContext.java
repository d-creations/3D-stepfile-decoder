package ch.rcreations.stepdecoder.StepShapes;



public class ProductDefinitionContext extends StepShapes {

    protected StepShapes calculationDecoding;
    protected String disciplineType;

    public ProductDefinitionContext(String name, StepShapes calculationDecoding, String disciplineType,int lineNumber) {
        super(AP242Code.PRODUCT_DEFINITION_CONTEXT,name,lineNumber);
        this.calculationDecoding = calculationDecoding;
        this.disciplineType = disciplineType;
    }


}
