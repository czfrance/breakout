package breakout;

import java.util.Arrays;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * SplashScreen is the screen that is displayed at the beginning of Breakout as well as between
 * levels. It contains the game name, explains the rules of the game, and displays the level about
 * to be played. Stays up until the "enter" key is clicked
 * <p>
 * This is a very bare-bones class solely for the purpose of displaying text
 * <p>
 * Extends Screen, depends on Arrays, List, Scene, Color, Paint, and Text.
 * <p>
 * create a SplashScreen instance: SplashScreen splash = new SplashScreen(); display its contents:
 * splash.drawScene(windowWidth, windowHeight, backgroundColor, gameLevel)
 *
 * @author Cynthia France
 * @see Screen
 */
public class SplashScreen extends Screen {

  private static final int titleTextSize = 30;
  private static final int descTextSize = 18;

  private Text title = new Text();
  private Text rules = new Text();
  private Text lvlText = new Text();
  private Text instructions = new Text();
  private List<Text> allText;

  /**
   * Draws/creates the scene specified.
   * <p>
   * This screen contains the game title, basic rules, level to be played, and instructions on how
   * to continue.
   *
   * @param width      the width of the window the scene will be displayed in
   * @param height     the height of the window the scene will be displayed in
   * @param background the background color of the scene
   * @param lvl        the Breakout level the player is about to play
   * @return the Scene created
   * @see Scene
   */
  public Scene drawScene(int width, int height, Paint background, int lvl) {
    allText = Arrays.asList(title, rules, lvlText, instructions);

    setBasicTextOptions(title, "BREAKOUT: WINTER WONDERLAND", titleTextSize, Color.LIGHTBLUE);
    title.setX(width / 2 - title.getBoundsInParent().getWidth() / 2);
    title.setY(height / 4);

    setBasicTextOptions(rules,
        "Move the paddle (arrow keys) to catch the ball before it hits the ground \n"
            + "Different blocks require different numbers of hits to break \n"
            + "Some blocks perform special actions when broken \n" + "Break blocks to win \n \n"
            + "Good Luck!", descTextSize, Color.LIGHTBLUE);
    rules.setX(width / 2 - rules.getBoundsInParent().getWidth() / 2);
    rules.setY(height / 4 + 2 * title.getBoundsInParent().getHeight());

    setBasicTextOptions(lvlText, "LEVEL " + lvl + getLevelName(lvl), titleTextSize,
        Color.LIGHTBLUE);
    lvlText.setX(width / 2 - lvlText.getBoundsInParent().getWidth() / 2);
    lvlText.setY(
        height / 4 + 2 * title.getBoundsInParent().getHeight() + 1.5 * rules.getBoundsInParent()
            .getHeight());

    setBasicTextOptions(instructions, "Press enter to begin", descTextSize, Color.LIGHTBLUE);
    instructions.setX(width / 2 - instructions.getBoundsInParent().getWidth() / 2);
    instructions.setY(
        height / 4 + 2 * title.getBoundsInParent().getHeight() + 1.5 * rules.getBoundsInParent()
            .getHeight() + 1.5 * lvlText.getBoundsInParent().getHeight());

    for (Text t : allText) {
      root.getChildren().add(t);
    }

    Scene scene = new Scene(root, width, height, background);
    return scene;
  }

  private String getLevelName(int lvl) {
    switch (lvl) {
      case 1 -> {
        return ": NOVEMBER";
      }
      case 2 -> {
        return ": DECEMBER";
      }
      default -> {
        return ": JANUARY";
      }
    }
  }
}
