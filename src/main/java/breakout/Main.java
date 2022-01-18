package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Breakout: Winter Wonderland's Main class The purpose of this class is to create & start the
 * Breakout game Extends Application
 *
 * @author Cynthia France
 */
public class Main extends Application {

  public static final String TITLE = "Breakout: Winter Wonderland";
  public static final int WIDTH = 600;
  public static final int HEIGHT = WIDTH + 150;
  public static final Color VERY_DARK_GRAY = Color.rgb(51, 51, 51);
  public static final Paint BACKGROUND = VERY_DARK_GRAY;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final int NUM_LVLS = 3;

  private Breakout myGame;

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
    SplashScreen mySplash = new SplashScreen();

    Scene scene = mySplash.drawScene(WIDTH, HEIGHT, BACKGROUND, currLvl);
    setupAndDisplayScene(stage, scene, TITLE);

    scene.setOnKeyReleased(e -> handleSplashKeyInput(e.getCode(), stage));
  }

  private void displayResults(Stage stage) {
    ResultsScreen myResults = new ResultsScreen();

    Scene scene = myResults.drawScene(WIDTH, HEIGHT, BACKGROUND, win);
    setupAndDisplayScene(stage, scene, TITLE);
  }

  private void startBreakoutLevel(Stage stage) {
    myGame = new Breakout();
    // attach scene to the stage and display it
    Scene scene = myGame.setupGame(WIDTH, HEIGHT, BACKGROUND, currLvl);
    setupAndDisplayScene(stage, scene, TITLE);

    Timeline animation = new Timeline();
    playAnimation(animation);

    scene.setOnKeyReleased(e -> handleBreakoutKeyInput(e.getCode(), stage, animation));
  }

  private void handleSplashKeyInput(KeyCode code, Stage stage) {
    switch (code) {
      case ENTER -> startBreakoutLevel(stage);
      case DIGIT1 -> jumpToLvl(1, stage, null);
      case DIGIT2 -> jumpToLvl(2, stage, null);
      case DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9 -> jumpToLvl(3, stage, null);
      default -> {
      }
    }
  }

  private void handleBreakoutKeyInput(KeyCode code, Stage stage, Timeline animation) {
    switch (code) {
      case ENTER -> {
        if (!myGame.gameIsRunning() && !myGame.gameIsWon()) {
          win = false;
          animation.stop();
          displayResults(stage);
        } else if (!myGame.gameIsRunning() && myGame.gameIsWon() && currLvl < NUM_LVLS) {
          currLvl++;
          animation.stop();
          displaySplash(stage);
        } else if (!myGame.gameIsRunning() && myGame.gameIsWon() && currLvl == NUM_LVLS) {
          win = true;
          animation.stop();
          displayResults(stage);
        }
      }
      case DIGIT1 -> jumpToLvl(1, stage, animation);
      case DIGIT2 -> jumpToLvl(2, stage, animation);
      case DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9 -> jumpToLvl(3, stage, animation);
      default -> {
      }
    }
  }

  private void jumpToLvl(int lvl, Stage stage, Timeline animation) {
    currLvl = lvl;
    if (animation != null) {
      animation.stop();
    }
    displaySplash(stage);
  }

  private void playAnimation(Timeline animation) {
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> myGame.step(SECOND_DELAY)));
    animation.play();
  }

  private void setupAndDisplayScene(Stage stage, Scene scene, String title) {
    stage.setScene(scene);
    stage.setTitle(title);
    stage.show();
  }
}