package ch.rcreations.stepdecoder.StepShapes.Point;

import ch.rcreations.stepdecoder.StepShapes.AP242Code;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

import java.util.*;

public class CartesianPoint extends Point {

    protected Map<CartasianAxisE,Double> directionValueMap = new TreeMap<>();
    public CartesianPoint(String name, List<Double> directionRatios,int lineNumber) {
        super(name,lineNumber,AP242Code.CARTESIAN_POINT);
        List<CartasianAxisE> axisEList = new ArrayList<>();
        axisEList.add(CartasianAxisE.X);
        axisEList.add(CartasianAxisE.Y);
        axisEList.add(CartasianAxisE.Z);
        Iterator<CartasianAxisE> i = axisEList.iterator();
        for (Double directions: directionRatios){
            directionValueMap.put(i.next(), directions);
        }
        Map<String,String> preferencesMap = new HashMap<>();
        preferencesMap.put("X",Double.toString(directionValueMap.get(CartasianAxisE.X)));
        preferencesMap.put("Y", Double.toString(directionValueMap.get(CartasianAxisE.Y)));
        preferencesMap.put("Z",Double.toString(directionValueMap.get(CartasianAxisE.Z)));
        preferencesMapList.add(preferencesMap);
    }


    @Override
    public Map<CartasianAxisE,Double> getPoint() {
        return Map.copyOf(this.directionValueMap);
    }
    @Override
    public Shape3D getShape() {
        Sphere sphere = new Sphere(4);
        sphere.setTranslateX(directionValueMap.get(CartasianAxisE.X));
        sphere.setTranslateY(directionValueMap.get(CartasianAxisE.Y));
        sphere.setTranslateZ(directionValueMap.get(CartasianAxisE.Z));
        return sphere;
    }

}
