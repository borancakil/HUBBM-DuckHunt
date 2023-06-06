import javafx.application.Application;
import javafx.stage.Stage;

/**

 The main class for the Duck Hunt game application.
 */
public class DuckHunt extends Application {

    /**

     The scaling factor for the game graphics.
     */
    public static final double SCALE = 4.0;
    /**

     The volume for audio playback.
     */
    public final double VOLUME = 0.25;
    /**

     The main method that launches the application.
     @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**

     The overridden start method of the Application class.
     @param primaryStage The primary stage of the JavaFX application.
     @throws Exception If an exception occurs during application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        IntroScreen introScreen = new IntroScreen(VOLUME);
        introScreen.show(primaryStage, SCALE);
    }

}