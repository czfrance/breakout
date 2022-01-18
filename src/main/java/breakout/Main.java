package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
  public static final int NUM_LVLS = 3;

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
    displaySplash(stage);
  }

  private void displaySplash(Stage stage) {
    mySplash = new SplashScreen();

    Scene scene = mySplash.drawScene(WIDTH, HEIGHT, BACKGROUND, currLvl);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();
    scene.setOnKeyReleased(e -> handleSplashKeyInput(e.getCode(), stage));
  }

  private void handleSplashKeyInput(KeyCode code, Stage stage) {
    switch (code) {
      case ENTER -> startBreakoutLevel(stage);
      case DIGIT1 -> jumpToLvl(1, stage, null);
      case DIGIT2 -> jumpToLvl(2, stage, null);
      case DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9 -> jumpToLvl(3, stage, null);
    }
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
    Scene scene = myGame.setupGame(WIDTH, HEIGHT, BACKGROUND, currLvl);
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

    scene.setOnKeyReleased(e -> handleBreakoutKeyInput(e.getCode(), stage, animation));
  }

  private void handleBreakoutKeyInput(KeyCode code, Stage stage, Timeline animation) {
    switch(code) {
      case ENTER -> {
        if (!myGame.gameIsRunning() && !myGame.gameIsWon()) {
          win = false;
          animation.stop();
          displayResults(stage);
        }
        else if (!myGame.gameIsRunning() && myGame.gameIsWon() && currLvl < NUM_LVLS) {
          currLvl++;
          animation.stop();
          displaySplash(stage);
        }
        else if (!myGame.gameIsRunning() && myGame.gameIsWon() && currLvl == NUM_LVLS) {
          win = true;
          animation.stop();
          displayResults(stage);
        }
      }
      case DIGIT1 -> jumpToLvl(1, stage, animation);
      case DIGIT2 -> jumpToLvl(2, stage, animation);
      case DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9 -> jumpToLvl(3, stage, animation);
    }
  }

  private void jumpToLvl(int lvl, Stage stage, Timeline animation) {
    currLvl = lvl;
    if (animation != null) {animation.stop();}
    displaySplash(stage);
  }

  private void playAnimation(Timeline animation) {
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> myGame.step(SECOND_DELAY)));
    animation.play();
  }
}