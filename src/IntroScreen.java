import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;


public class IntroScreen {
    public Image backgroundImage;
    public Scene scene;
    public ImageView backgroundImageView;
    public static MediaPlayer mediaPlayer;
    public boolean isPlaying = false;
    public double SCALE;
    public double VOLUME;

    // Other attributes and methods specific to the intro screen

    public IntroScreen(double VOLUME) {
        this.VOLUME = VOLUME;
    }

    public void show(Stage stage, double SCALE) {
        this.SCALE = SCALE;

        // Load the background png
        backgroundImage = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\welcome\\1.png");
        backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(stage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(stage.heightProperty());

        // Loading and playing the mp3 file in loop with our method
        playWelcomeSound(isPlaying, VOLUME);

        // Creating our label
        Label label = new Label("BENİMLE ÇIKAR MISIN \n" +
                "     ");
        label.setFont(Font.font("Arial", 20*SCALE));
        label.setTextFill(Color.YELLOW);

        // Positioning our label as we want and creating StackPane for it

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(100*SCALE, 0, 0, 0));

        StackPane labelPane = new StackPane(label);
        StackPane.setAlignment(label, Pos.CENTER);

        vbox.getChildren().add(labelPane);

        // Make our label blink in each second.
        Timeline blinkTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1.0), e -> {
                    if (label.getTextFill() == Color.YELLOW) {
                        label.setTextFill(Color.TRANSPARENT);
                    } else {
                        label.setTextFill(Color.YELLOW);
                    }
                })
        );
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline.play();


        // Create the root node for the intro screen
        StackPane root = new StackPane();


        // Adding the background image
        root.getChildren().addAll(backgroundImageView, vbox);

        // Create the intro scene
        double width = backgroundImage.getWidth() * SCALE;
        double height = backgroundImage.getHeight() * SCALE;
        scene = new Scene(root, width, height);

        // Set the scene on the stage
        stage.setScene(scene);
        stage.setTitle("HUBBM Duck Hunt");

        // Adding the icon
        Image icon = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\welcome\\1.png");
        stage.getIcons().add(icon);
        stage.show();

        // Taking inputs
        setupInputHandlers(stage);
    }
    private static void playWelcomeSound(boolean isPlaying, double VOLUME) {
        File soundFile = new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\effects\\Title.mp3");

        Media media = new Media(soundFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(VOLUME);
        if (!isPlaying) {
            mediaPlayer.play();
        }
    }
    private void setupInputHandlers(Stage stage) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Switch to the selection screen
                SelectionScreen selectionScreen = new SelectionScreen(VOLUME);
                selectionScreen.show(stage, mediaPlayer, SCALE);
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                // Quitting game
                Platform.exit();
            }
        });
    }
}
