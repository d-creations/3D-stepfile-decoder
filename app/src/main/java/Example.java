import ch.rcreations.stepdecoder.Step3DModel;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * <p>
 * <p>
 *  A Short example how to open a Step file with the stepfile Decoder
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */
public class Example extends Application{

    // The Basic Step-file
    private static final Step3DModel step3DModel = new Step3DModel("My Shape");

    public static void main(String[] args) throws URISyntaxException {
        // load a step-file
        ClassLoader classLoader = Example.class.getClassLoader();
        URL resource = classLoader.getResource("Teil1.STEP");
        assert resource != null;
        File file = new File(resource.toURI());
        try {
            //***************
            // Open the File and Parse
            getAFile(file);
            //***************
        }catch (Exception e){
        System.err.println("Error read File"  + e.getMessage());
        }

        Application.launch();
    }

    public static void getAFile(File file) {
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            //***************
            // Open the File and Parse
            step3DModel.parseTheFile(reader);
            //**************
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e);
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }


    @Override
    public void start(Stage primaryStage) {
        // create a View and Show it
        Pane rootPane = new Pane();
        Scene scene = new Scene(rootPane, 500, 500, true);
        scene.setFill(Color.CADETBLUE);
        addStepShape(rootPane);
        // fill in scene and stage setup
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("STEP DECODER ");
        primaryStage.show();
    }

    private static void addStepShape(Pane rootPane) {
            for (MeshView shape : step3DModel.getShapes2DMesh()) {
                Rotate rotateX = new Rotate();
                Rotate rotateY = new Rotate();
                //shape.setDrawMode(DrawMode.FILL ); // Rotation not good wenn on
                shape.setCache(true);
                shape.setCacheHint(CacheHint.ROTATE);
                shape.setTranslateX(300);
                shape.setTranslateY(300);
                shape.setMaterial(new PhongMaterial(Color.CORNFLOWERBLUE));
                shape.setCullFace(CullFace.BACK);
                shape.getTransforms().add(new Scale(1, 1));
                rotateX.setAxis(new Point3D(0, 0, 0));
                rotateY.setAxis(new Point3D(1, 1, 1));

                rotateX.setAngle(150);
                rotateY.setAngle(30);
                shape.getTransforms().add(rotateX);
                shape.getTransforms().add(rotateY);
                rootPane.getChildren().add(shape);

            }
    }

}
