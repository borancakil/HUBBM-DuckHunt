import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;

/**

 Represents a duck in the Duck Hunt game.
 */
public class Duck extends ImageView {
    public Image Shot;
    public Image deadDuck;
    private Rectangle hitbox;
    private boolean isAlive = true;
    private Timeline dieAnimation;
    private Timeline flying;
    private Timeline animation;
    private int currentImageIndex = 0;
    private double height;
    private Image[] flyImages;
    private double width;
    private double SCALE;
    private double VOLUME;
    private double velocityY;

    /**

     Creates a new instance of the Duck class.

     @param x The initial x position of the duck.

     @param y The initial y position of the duck.

     @param flyImages An array of images representing the flying animation of the duck.

     @param Shot The image representing the duck when shot.

     @param DeadDuck The image representing the dead duck.

     @param width The width of the game screen.

     @param height The height of the game screen.

     @param SCALE The scaling factor for the duck's graphics.

     @param VOLUME The volume for audio playback.
     */
    public Duck(double x, double y, Image[] flyImages, Image Shot, Image DeadDuck, double width, double height, double SCALE, double VOLUME) {
        this.SCALE = SCALE;
        this.VOLUME = VOLUME;
        setImage(flyImages[0]);
        this.flyImages = flyImages;
        this.Shot = Shot;
        this.deadDuck = DeadDuck;
        this.width = width;
        this.height = height;
        setScaleX(-1);
        setFitWidth(flyImages[0].getWidth() * SCALE);
        setFitHeight(flyImages[0].getHeight() * SCALE);
        setVisible(true);

// Create hitbox with the same dimensions as the duck's image
        hitbox = new Rectangle(getTranslateX(), getTranslateY(), getFitWidth(), getFitHeight());
        hitbox.setFill(Color.BLACK);
        hitbox.setVisible(false);

// Register mouse click event handler
    }

    /**

     Makes the duck fly with the specified velocity and position.

     @param xVelocity The x velocity of the duck's flight.

     @param yVelocity The y velocity of the duck's flight.

     @param xPosition The x position of the duck's initial position.

     @param yPosition The y position of the duck's initial position.

     @param SCALE The scaling factor for the duck's graphics.
     */
    public void fly(double xVelocity, double yVelocity, double xPosition, double yPosition, double SCALE) {
        this.velocityY = yVelocity;
        if (xVelocity > 0) {
            setScaleX(-getScaleX());
        }
        if (yVelocity > 0) {
            setScaleY(-getScaleY());
        }
        double duckWidth = getBoundsInLocal().getWidth();
        double duckHeight = getBoundsInLocal().getHeight();

        double initialX = duckWidth / 2 - width / 2;
        double initialY = duckHeight / 2 - height / 2;

        double maxX = width / 2 - duckWidth / 2;
        double maxY = height / 2 - duckHeight / 2;

        DoubleProperty velocityX = new SimpleDoubleProperty(xVelocity * SCALE);
        DoubleProperty velocityY = new SimpleDoubleProperty(yVelocity * SCALE);

        setTranslateX(xPosition);
        hitbox.setTranslateX(xPosition);
        setTranslateY(yPosition);
        hitbox.setTranslateY(yPosition);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            setTranslateX(getTranslateX() + velocityX.get());
            setTranslateY(getTranslateY() + velocityY.get());
            hitbox.setTranslateX(getTranslateX() + velocityX.get());
            hitbox.setTranslateY(getTranslateY() + velocityY.get());
            if (getTranslateX() <= initialX || getTranslateX() >= maxX) {
                velocityX.set(-velocityX.get());
                setScaleX(-getScaleX());
            }
            if (getTranslateY() <= initialY || getTranslateY() >= maxY) {
                velocityY.set(-velocityY.get());
                setScaleY(-getScaleY());
            }}));

        timeline.setCycleCount(Animation.INDEFINITE);
        flying = timeline;
        flying.play();

        Duration frameDuration = Duration.millis(100);
        KeyValue keyValue1 = new KeyValue(imageProperty(), flyImages[0]);
        KeyValue keyValue2 = new KeyValue(imageProperty(), flyImages[1]);
        KeyValue keyValue3 = new KeyValue(imageProperty(), flyImages[2]);

        KeyFrame keyFrame1 = new KeyFrame(frameDuration, keyValue1);
        KeyFrame keyFrame2 = new KeyFrame(frameDuration.multiply(2), keyValue2);
        KeyFrame keyFrame3 = new KeyFrame(frameDuration.multiply(3), keyValue3);
        KeyFrame keyFrame4 = new KeyFrame(frameDuration.multiply(4), keyValue2);

        Timeline imageTimeline = new Timeline();
        imageTimeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, keyFrame4);
        imageTimeline.setCycleCount(Animation.INDEFINITE);

        animation = imageTimeline;
        animation.play();
    }

    /**

     Makes the duck die and fall to the ground.

     @param shotDuck The image representing the duck when shot.

     @param deadDuck The image representing the dead duck.
     */
    public void die(Image shotDuck, Image deadDuck) {
        if (isAlive) {
            File soundFile = new File("C:\\Users\\boran\\IdeaProjects\\duckhunt\\src\\assets\\effects\\DuckFalls.mp3");
            Media media = new Media(soundFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(VOLUME);
            mediaPlayer.play();

            double duckHeight = getBoundsInLocal().getHeight();
            isAlive = false;
            setImage(shotDuck);
            double hitboxY = getHitbox().getY();

            PauseTransition pauseTransition = new PauseTransition(Duration.ZERO);
            pauseTransition.setOnFinished(event -> {
                if (velocityY == 0) {
                    setImage(deadDuck);
                } else {
                    setImage(deadDuck);
                    setScaleY(-getScaleY());
                }
            });

            dieAnimation = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(translateYProperty(), hitboxY - duckHeight)),
                    new KeyFrame(Duration.seconds(0.3), new KeyValue(translateYProperty(), hitboxY - duckHeight)),
                    new KeyFrame(Duration.seconds(1.5), new KeyValue(translateYProperty(), height / 2 - duckHeight / 2))
            );

            dieAnimation.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, event -> setImage(shotDuck)),
                    new KeyFrame(Duration.seconds(0.3), event -> pauseTransition.play())
            );

            dieAnimation.setCycleCount(1);
            dieAnimation.setAutoReverse(false);
            dieAnimation.play();
        }
    }

    /**

     Checks if the duck is alive.
     @return true if the duck is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }
    /**

     Gets the hitbox rectangle of the duck.
     @return The hitbox rectangle.
     */
    public Rectangle getHitbox() {
        return hitbox;
    }
    /**

     Gets the flying timeline of the duck.
     @return The flying timeline.
     */
    public Timeline getFlying() {
        return flying;
    }
    /**

     Gets the animation timeline of the duck.
     @return The animation timeline.
     */
    public Timeline getAnimation() {
        return animation;
    }
}