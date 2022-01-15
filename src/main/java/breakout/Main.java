package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {

  // useful names for constant values used
  public static final String TITLE = "Breakout: Winter Wonderland";
  public static final int WIDTH = 400;
  public static final int HEIGHT = WIDTH + 100;
  public static final Color VERYDARKGRAY = Color.rgb(51, 51, 51);
  public static final Paint BACKGROUND = VERYDARKGRAY;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  // instance variables
  private Breakout myGame;


  /**
   * Initialize what will be displayed.
   */
  @Override
  public void start(Stage stage) {
    myGame = new Breakout();

    // attach scene to the stage and display it
    Scene scene = myGame.setupGame(WIDTH, HEIGHT, BACKGROUND);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> myGame.step(SECOND_DELAY)));
    animation.play();
  }
}
