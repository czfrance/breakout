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
  public static final int WIDTH = 600;
  public static final int HEIGHT = WIDTH + 150;
  public static final Color VERYDARKGRAY = Color.rgb(51, 51, 51);
  public static final Paint BACKGROUND = VERYDARKGRAY;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  // instance variables
  private Breakout myGame;
  private SplashScreen mySplash;
  private ResultsScreen myResults;

  private int currLvl = 1;
  private boolean win = false;

  /**
   * Initialize what will be displayed.
   */
  @Override
  public void start(Stage stage) {
    displayResults(stage);
//    displaySplash(stage);
//    startBreakoutLevel(stage);
  }

  private void displaySplash(Stage stage) {
    mySplash = new SplashScreen();

    Scene scene = mySplash.drawScene(WIDTH, HEIGHT, BACKGROUND, currLvl);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();

    while (!mySplash.begin()) {
      continue;
    }
    return;
  }

  private void displayResults(Stage stage) {
    myResults = new ResultsScreen();

    Scene scene = myResults.drawScene(WIDTH, HEIGHT, BACKGROUND, win);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();
  }

  private void startBreakoutLevel(Stage stage) {
    myGame = new Breakout();

    // attach scene to the stage and display it
    Scene scene = myGame.setupGame(WIDTH, HEIGHT, BACKGROUND);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly
    // forever)
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> myGame.step(SECOND_DELAY)));
    animation.play();
    //playAnimation(animation);
//    while (myGame.gameIsRunning()) {
//      System.out.println("running");
//      continue;
//    }
//    animation.stop();
  }

  private void playAnimation(Timeline animation) {
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> myGame.step(SECOND_DELAY)));
    animation.play();
  }
}

//    myGame = new Breakout();
//
//    // attach scene to the stage and display it
//    Scene scene = myGame.setupGame(WIDTH, HEIGHT, BACKGROUND);
//    stage.setScene(scene);
//    stage.setTitle(TITLE);
//    stage.show();
//    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
//    Timeline animation = new Timeline();
//    animation.setCycleCount(Timeline.INDEFINITE);
//    animation.getKeyFrames()
//        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> myGame.step(SECOND_DELAY)));
//    animation.play();
//    animation.stop();


/*
if (myGame.gameIsRunning()) {
      animation.setCycleCount(Timeline.INDEFINITE);
      animation.getKeyFrames()
          .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> myGame.step(SECOND_DELAY)));
      animation.play();
    }
    animation.stop();
 */