import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the second level of the game.
 */
public class SecondLevel {

    /**
     * ImageView for displaying the crosshair image.
     */
    static ImageView crossHairImageView;
    /**
     * MediaPlayer for displaying the sounds.
     */
    MediaPlayer mediaPlayer;

    /**
     * ImageView for displaying the foreground image.
     */
    static ImageView foregroundImageView;

    /**
     * List of active ducks in the game.
     */
    static List<Duck> ducks = new ArrayList<>();

    /**
     * List of ducks that have been killed by the player.
     */
    static List<Duck> killedDucks = new ArrayList<>();

    /**
     * The available ammunition for the player.
     */
    int ammo = 6;

    /**
     * The media player for shooting sound effects.
     */
    private MediaPlayer shootMediaPlayer;

    /**
     * Flag to indicate if the player has won the game.
     */
    boolean isWin = false;

    /**
     * The scaling factor for the game screen.
     */
    double SCALE;

    /**
     * The volume level for audio playback.
     */
    double VOLUME;


    /**
     * Constructs the SecondLevel object.
     *
     * @param backgroundImageView the ImageView for the background
     */
    public SecondLevel(ImageView backgroundImageView, double SCALE, double VOLUME) {
        this.SCALE = SCALE;
        this.VOLUME = VOLUME;

        ducks.clear();
        killedDucks.clear();

        double width = backgroundImageView.getImage().getWidth() * SecondLevel.this.SCALE;
        double height = backgroundImageView.getImage().getHeight() * SecondLevel.this.SCALE;

        // Creating duck objects and adding them to the ducks list

        Image[] flyImages = new Image[3];
        flyImages[0] = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_red\\4.png");
        flyImages[1] = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_red\\5.png");
        flyImages[2] = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_red\\6.png");
        Image deadDuckImageRed = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_red\\8.png");
        Image shotDuckImageRed = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_red\\7.png");
        Duck duck1 = new Duck(75 * SCALE, 100*SCALE, flyImages, shotDuckImageRed, deadDuckImageRed, width, height, SCALE, VOLUME);
        ducks.add(duck1);

        Image[] flyImages2 = new Image[3];
        flyImages2[0] = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_black\\4.png");
        flyImages2[1] = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_black\\5.png");
        flyImages2[2] = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_black\\6.png");
        Image deadDuckImageBlack = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_black\\8.png");
        Image shotDuckImageBlack = new Image("file:C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\duck_black\\7.png");
        Duck duck2 = new Duck(2 * SCALE, -100*SCALE, flyImages2, shotDuckImageBlack, deadDuckImageBlack, width, height, SCALE, VOLUME);
        ducks.add(duck2);

        duck2.fly(2.0, 0.0, 0 * SCALE ,0 * SCALE, SCALE);
        duck1.fly(-2.0, 0.0, 0 * SCALE ,0 * SCALE, SCALE);

    }

    /**
     * Displays the second level.
     *
     * @param stage               the primary stage
     * @param backgroundImageView the ImageView for the background
     * @param crossHairView       the ImageView for the crosshair
     * @param foregroundView      the ImageView for the foreground
     */
    public void show(Stage stage, ImageView backgroundImageView, ImageView crossHairView, ImageView foregroundView, double SCALE) {
        this.SCALE = SCALE;


        crossHairImageView = crossHairView;
        foregroundImageView = foregroundView;

        // Loading shoot effect
        File shootSoundFile = new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\effects\\Gunshot.mp3");
        Media shootMedia = new Media(shootSoundFile.toURI().toString());
        shootMediaPlayer = new MediaPlayer(shootMedia);

        // Adjusting the background and crosshair
        backgroundImageView.fitWidthProperty().bind(stage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(stage.heightProperty());
        foregroundView.fitWidthProperty().bind(stage.widthProperty());
        foregroundView.fitHeightProperty().bind(stage.heightProperty());
        crossHairImageView.setFitWidth(10*SecondLevel.this.SCALE);
        crossHairImageView.setFitHeight(10*SecondLevel.this.SCALE);

        // Creating our labels
        Label label1 = new Label("Level 2/6");
        label1.setFont(Font.font("Arial", 8*SecondLevel.this.SCALE));
        label1.setTextFill(Color.YELLOW);
        label1.setStyle("-fx-font-weight: bold;");

        Label label2 = new Label("Ammo Left: " + ammo);
        label2.setFont(Font.font("Arial", 8*SecondLevel.this.SCALE));
        label2.setTextFill(Color.YELLOW);
        label2.setStyle("-fx-font-weight: bold;");

        // Creating Stackpanes
        StackPane imagePaneBackground = new StackPane(backgroundImageView);
        StackPane.setAlignment(imagePaneBackground, Pos.CENTER);

        StackPane labelPane1 = new StackPane(label1);
        StackPane.setAlignment(label1, Pos.TOP_CENTER);

        StackPane labelPane2 = new StackPane(label2);
        StackPane.setAlignment(label2, Pos.TOP_RIGHT);

        StackPane root = new StackPane(imagePaneBackground, labelPane1, labelPane2);

        // Calculating width and height according to SCALE value

        double width = backgroundImageView.getImage().getWidth() * SecondLevel.this.SCALE;
        double height = backgroundImageView.getImage().getHeight() * SecondLevel.this.SCALE;

        for (Duck duck : ducks) {
            root.getChildren().addAll(duck, duck.getHitbox());
        }
        root.getChildren().add(foregroundImageView);


        // Creating scene
        Scene level1Scene = new Scene(root, width, height);


        // Setting our cursor to chosen cross-hair

        ImageView scaledCrosshair = new ImageView(crossHairView.getImage());
        scaledCrosshair.setFitWidth(crossHairView.getImage().getWidth() * SecondLevel.this.SCALE);
        scaledCrosshair.setFitHeight(crossHairView.getImage().getHeight() * SecondLevel.this.SCALE);
        ImageCursor cursor = new ImageCursor(scaledCrosshair.getImage());
        level1Scene.setCursor(cursor);
        stage.setScene(level1Scene);
        stage.show();

        MouseEventHandler(level1Scene, label2);


        // The scene if the user loses the round
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (ammo == 0 && (ducks.size() != killedDucks.size())) {
                    File soundFile = new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\effects\\GameOver.mp3");

                    Media media = new Media(soundFile.toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setVolume(VOLUME);
                    mediaPlayer.play();
                    // Display "Game Over" label
                    Label gameOverLabel = new Label("GAME OVER!");
                    gameOverLabel.setFont(Font.font("Arial", 15 * SecondLevel.this.SCALE));
                    gameOverLabel.setTextFill(Color.YELLOW);
                    gameOverLabel.setStyle("-fx-font-weight: bold;");

                    gameOverLabel.setTranslateX(0.6 * SecondLevel.this.SCALE);
                    gameOverLabel.setTranslateY(-17.5 * SecondLevel.this.SCALE);

                    Label enter = new Label("Press ENTER to play again");
                    enter.setFont(Font.font("Arial", 15 * SecondLevel.this.SCALE));
                    enter.setTextFill(Color.YELLOW);
                    enter.setStyle("-fx-font-weight: bold;");

                    enter.setTranslateX(0.6 * SecondLevel.this.SCALE);
                    enter.setTranslateY(0);

                    Label escape = new Label("Press ESC to exit");
                    escape.setFont(Font.font("Arial", 15 * SecondLevel.this.SCALE));
                    escape.setTextFill(Color.YELLOW);
                    escape.setStyle("-fx-font-weight: bold;");

                    escape.setTranslateX(0.6 * SecondLevel.this.SCALE);
                    escape.setTranslateY(15 * SecondLevel.this.SCALE);


                    Timeline blinkTimelineEnter = new Timeline(
                            new KeyFrame(Duration.seconds(1.0), e -> {

                                if (enter.getTextFill() == Color.YELLOW) {
                                    enter.setTextFill(Color.TRANSPARENT);
                                }
                                else {
                                    enter.setTextFill(Color.YELLOW);
                                }
                            })
                    );
                    blinkTimelineEnter.setCycleCount(Timeline.INDEFINITE);
                    blinkTimelineEnter.play();

                    Timeline blinkTimelineEscape = new Timeline(
                            new KeyFrame(Duration.seconds(1.0), e -> {

                                if (escape.getTextFill() == Color.YELLOW) {
                                    escape.setTextFill(Color.TRANSPARENT);
                                }
                                else {
                                    escape.setTextFill(Color.YELLOW);
                                }
                            })
                    );
                    blinkTimelineEscape.setCycleCount(Timeline.INDEFINITE);
                    blinkTimelineEscape.play();


                    root.getChildren().addAll(gameOverLabel, enter, escape);
                    stop(); // Stop the timer to avoid continuous updates
                }
            }
        };
        timer.start();

        // The scene if the user wins the round
        AnimationTimer timer1 = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (killedDucks.size() == ducks.size()) {
                    File soundFile = new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\effects\\LevelCompleted.mp3");

                    Media media = new Media(soundFile.toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setVolume(VOLUME);
                    mediaPlayer.play();
                    // Display "YOU WIN!" label
                    Label youWinLabel = new Label("YOU WIN!");
                    youWinLabel.setFont(Font.font("Arial", 15 * SecondLevel.this.SCALE));
                    youWinLabel.setTextFill(Color.YELLOW);
                    youWinLabel.setStyle("-fx-font-weight: bold;");

                    youWinLabel.setTranslateX(0.6 * SecondLevel.this.SCALE);
                    youWinLabel.setTranslateY(-17.5 * SecondLevel.this.SCALE);

                    Label enter = new Label("Press ENTER to play next level");
                    enter.setFont(Font.font("Arial", 15 * SecondLevel.this.SCALE));
                    enter.setTextFill(Color.YELLOW);
                    enter.setStyle("-fx-font-weight: bold;");

                    enter.setTranslateX(0.6 * SecondLevel.this.SCALE);
                    enter.setTranslateY(0);


                    Timeline blinkTimelineEnter = new Timeline(
                            new KeyFrame(Duration.seconds(1.0), e -> {

                                if (enter.getTextFill() == Color.YELLOW) {
                                    enter.setTextFill(Color.TRANSPARENT);
                                }
                                else {
                                    enter.setTextFill(Color.YELLOW);
                                }
                            })
                    );
                    blinkTimelineEnter.setCycleCount(Timeline.INDEFINITE);
                    blinkTimelineEnter.play();

                    root.getChildren().addAll(youWinLabel, enter);
                    stop(); // Stop the timer to avoid continuous updates
                }
            }
        };
        timer1.start();


        // Checking the user's actions
        level1Scene.setOnKeyPressed(event -> {

            if (ammo == 0 && (killedDucks.size() != ducks.size())) {

                if (event.getCode() == KeyCode.ESCAPE) {
                    mediaPlayer.stop();
                    IntroScreen introScreen = new IntroScreen(VOLUME);
                    introScreen.show(stage, SCALE);
                }

                if (event.getCode() == KeyCode.ENTER) {
                    mediaPlayer.stop();
                    FirstLevel innerLevel = new FirstLevel(backgroundImageView, SCALE, VOLUME);
                    innerLevel.show(stage,  backgroundImageView, crossHairView, foregroundImageView, SCALE);
                }
            }

            if (killedDucks.size() == ducks.size()) {

                if (event.getCode() == KeyCode.ENTER) {
                    mediaPlayer.stop();
                    ThirdLevel thirdLevel = new ThirdLevel(backgroundImageView, SCALE, VOLUME
                    );
                    thirdLevel.show(stage, backgroundImageView, crossHairView, foregroundView, SCALE);
                }
            }
        });
    }

    /**
     * Handles the mouse event for the given scene and label.
     *
     * @param scene The scene to handle mouse events for.
     * @param label The label to update with ammo information.
     */
    public void MouseEventHandler(Scene scene, Label label) {
        scene.setOnMouseClicked(event -> {
            if (!isWin && event.getButton() == MouseButton.PRIMARY) {

                if (ammo > 0) {
                    shootMediaPlayer.setVolume(VOLUME);
                    shootMediaPlayer.stop();
                    shootMediaPlayer.play();
                    ammo--;
                    label.setText("Ammo Left: " + ammo);

                    for (Duck duck : ducks) {
                        double hitboxX = duck.getHitbox().getTranslateX();
                        double hitboxWidth = duck.getHitbox().getWidth();
                        double hitboxHeight = duck.getHitbox().getHeight();
                        double hitboxY = duck.getHitbox().getTranslateY();

                        double mouseX = event.getSceneX() - (scene.getWidth() / 2) + (hitboxWidth / 2) ;
                        double mouseY = -(event.getSceneY() - (scene.getHeight() / 2) + (hitboxHeight / 2));


                        if ((duck.isAlive()) && (mouseX >= hitboxX) && (mouseX <= (hitboxX + hitboxWidth)) && (mouseY <= hitboxY) && (mouseY >= hitboxY - hitboxHeight)) {
                            killedDucks.add(duck);
                        }

                    }
                    for (Duck duck : killedDucks) {
                        duck.getFlying().stop();
                        duck.getAnimation().stop();
                        duck.die(duck.Shot, duck.deadDuck);
                    }

                    if (killedDucks.size() == ducks.size()) {
                        isWin = true;
                    }
                }
            }
        });
    }
}
