package ch.rcreations.stepdecoder.Config;

/**
 * <p>
 * <p>
 * This is the Config file for the Step Decoder
 * <p>
 *
 * @author Damian www.d-creations.org
 * @version 1.0
 * @since 2022-07-31
 */
public class StepConfig {

    //SPHERICAL RESOLUTOIN
    public static final int COUNTLAYERS = 30; // Half Spherical needs to be uneven
    public static final int COUNTTRIANGLEPERLAYER = 40;

    public static final int CIRCALRESOLUTION = 1;//1 DEGRE

    public static boolean DEBUG_MODE = true;

    public static boolean isDebugModeON() {
        return DEBUG_MODE;
    }

    public static void setDebugMode(boolean debugMode) {
        DEBUG_MODE = debugMode;
    }

    /**
     * Prints a Message
     * @param message Message String
     */
    public static final void printMessage(String message){
        if (DEBUG_MODE) System.out.println(message);
    }

    /**
     * Prints an Error
     * @param message Message String
     */
    public static final void printError(String message){
        if (DEBUG_MODE) System.out.println(message);
    }

}
