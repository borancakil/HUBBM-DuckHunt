import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectionScreen {


    /**
     * List of background image files available for selection.
     */
    List<File> backgroundImageFiles;

    /**
     * List of crosshair image files available for selection.
     */
    List<File> crossHairFiles;

    /**
     * List of foreground image files.
     */
    List<File> foregroundFiles;

    /**
     * Index of the currently selected background image.
     */
    int currentBackgroundIndex = 0;

    /**
     * Index of the currently selected crosshair image.
     */
    int currentCrossHairIndex = 0;

    /**
     * ImageView for displaying the entrance background image.
     */
    ImageView entranceImageView;

    /**
     * ImageView for displaying the selected crosshair image.
     */
    ImageView crossHairImageView;

    /**
     * ImageView for displaying the selected foreground image.
     */
    ImageView foregroundImageView;

    /**
     * Scene for the entrance screen.
     */
    Scene entranceScene;

    /**
     * Scaling factor for the images.
     */
    double SCALE;

    /**
     * Volume level for audio playback.
     */
    double VOLUME;

    /**
     * Flag to indicate if the game is in play mode.
     */
    static boolean isPlay = false;

    /**
     * Constructs a SelectionScreen object with the specified volume level.
     *
     * @param VOLUME The volume level for audio playback.
     */

    public SelectionScreen(double VOLUME) {
        this.VOLUME = VOLUME;
    }

    public void show(Stage stage, MediaPlayer mediaPlayer, double SCALE) {
        this.SCALE = SCALE;

        // Adding our background and cross-hair images to Arraylists
        backgroundImageFiles = new ArrayList<>();
        backgroundImageFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\background\\1.png"));
        backgroundImageFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\background\\2.png"));
        backgroundImageFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\background\\3.png"));
        backgroundImageFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\background\\4.png"));
        backgroundImageFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\background\\5.png"));
        backgroundImageFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\background\\6.png"));


        crossHairFiles = new ArrayList<>();
        crossHairFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\crosshair\\1.png"));
        crossHairFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\crosshair\\2.png"));
        crossHairFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\crosshair\\3.png"));
        crossHairFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\crosshair\\4.png"));
        crossHairFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\crosshair\\5.png"));
        crossHairFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\crosshair\\6.png"));
        crossHairFiles.add(new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\crosshair\\7.png"));

        foregroundFiles = new ArrayList<>();

        // Turning images to imageWiew and setting their size

        crossHairImageView = new ImageView();
        crossHairImageView.setFitWidth(10*SCALE);
        crossHairImageView.setFitHeight(10*SCALE);
        crossHairImageView.setImage(new Image(crossHairFiles.get(currentCrossHairIndex).toURI().toString()));


        entranceImageView = new ImageView();
        entranceImageView.fitWidthProperty().bind(stage.widthProperty());
        entranceImageView.fitHeightProperty().bind(stage.heightProperty());

        entranceImageView.setImage(new Image(backgroundImageFiles.get(currentBackgroundIndex).toURI().toString()));

        // Creating our label and setting size

        Label label = new Label("USE ARROW KEYS TO NAVIGATE\n" +
                "\tPRESS ENTER TO START\n" +
                "\t    PRESS ESC TO EXIT");
        label.setFont(Font.font("Arial", 8*SCALE));
        label.setTextFill(Color.YELLOW);
        label.setStyle("-fx-font-weight: bold;");

        // Creating StackPane for background and crosshair.

        StackPane imagePaneBackground = new StackPane(entranceImageView);
        StackPane.setAlignment(entranceImageView, Pos.CENTER);

        StackPane imagePaneCrossHair = new StackPane(crossHairImageView);
        StackPane.setAlignment(crossHairImageView, Pos.CENTER);

        // Creting VBox and StackPane for our label.

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(0, 0, 200*SCALE, 0));

        StackPane labelPane = new StackPane(label);
        StackPane.setAlignment(label, Pos.CENTER);

        vbox.getChildren().add(labelPane);

        // Creating our final StackPane to create our scene

        StackPane layout = new StackPane();
        layout.getChildren().addAll(imagePaneBackground, imagePaneCrossHair, vbox);

        double width = entranceImageView.getImage().getWidth() * SCALE;
        double height = entranceImageView.getImage().getHeight() * SCALE;
        entranceScene = new Scene(layout, width, height);

        stage.setScene(entranceScene);
        stage.show();
        setupInputHandlers(stage, mediaPlayer);


    }

    // Methods for navigating through background and crosshair images
    //
    private void navigateForwardBackground() {
        currentBackgroundIndex = (currentBackgroundIndex + 1) % backgroundImageFiles.size();
        loadImageBackground();
    }

    private void navigateBackwardBackground() {
        currentBackgroundIndex = (currentBackgroundIndex - 1 + backgroundImageFiles.size()) % backgroundImageFiles.size();
        loadImageBackground();
    }

    /**
     * Loads the currently selected background image into the entranceImageView.
     */
    private void loadImageBackground() {
        File imageFile = backgroundImageFiles.get(currentBackgroundIndex);
        entranceImageView.setImage(new Image(imageFile.toURI().toString()));
    }
    private void navigateForwardCrosshair() {
        currentCrossHairIndex = (currentCrossHairIndex + 1) % crossHairFiles.size();
        loadImageCrosshair();
    }
    private void navigateBackwardCrosshair() {
        currentCrossHairIndex = (currentCrossHairIndex - 1 + crossHairFiles.size()) & crossHairFiles.size() ;
        loadImageCrosshair();
    }
    private void loadImageCrosshair() {
        File imageFilecross = crossHairFiles.get(currentCrossHairIndex);
        crossHairImageView.setImage(new Image(imageFilecross.toURI().toString()));
    }

    /**
     * Sets up the input handlers for the entranceScene.
     *
     * @param stage       The stage where the entrance scene is displayed.
     * @param mediaPlayer The media player for audio playback.
     */
    private void setupInputHandlers(Stage stage, MediaPlayer mediaPlayer) {
        entranceScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                mediaPlayer.stop();

                File soundFile = new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\effects\\Intro.mp3");

                Media media = new Media(soundFile.toURI().toString());
                MediaPlayer mediaPlayer1 = new MediaPlayer(media);
                mediaPlayer1.setVolume(VOLUME);

                Image foregroundImage = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\foreground\\" + (currentBackgroundIndex + 1) + ".png");
                foregroundImageView = new ImageView(foregroundImage);
                mediaPlayer1.setOnEndOfMedia(() -> {
                    FirstLevel level1 = new FirstLevel(entranceImageView, SCALE, VOLUME);
                    level1.show(stage, entranceImageView, crossHairImageView, foregroundImageView, SCALE);
                });
                if (!isPlay) {
                    mediaPlayer1.play();
                    isPlay = true;
                }
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                IntroScreen introScreen = new IntroScreen(VOLUME);
                introScreen.isPlaying = true;
                introScreen.show(stage, SCALE);
            }
            if (event.getCode() == KeyCode.UP) {
                navigateForwardCrosshair();
            }
            if (event.getCode() == KeyCode.DOWN) {
                navigateBackwardCrosshair();

            }
            if (event.getCode() == KeyCode.RIGHT) {
                navigateForwardBackground();

            }
            if (event.getCode() == KeyCode.LEFT) {
                navigateBackwardBackground();
            }
        });
    }
}
