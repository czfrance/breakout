package breakout;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * ResultsScreen is the screen that is when the Breakout is over. It contains the "Game Over"
 * message as well as the player's results (win or lose)
 * <p>
 * This is a very bare-bones class solely for the purpose of displaying text
 * <p>
 * Extends Screen, depends on Scene, Color, Paint, and Text.
 * <p>
 * create a ResultsScreen instance: ResultsScreen results = new ResultsScreen(); display its
 * contents: results.drawScene(windowWidth, windowHeight, backgroundColor, gameIsWon)
 *
 * @author Cynthia France
 * @see Screen
 */
public class ResultsScreen extends Screen {

  public static final int STATUS_TEXT_SIZE = 30;
  public static final int RESULT_TEXT_SIZE = 50;
  public static final String SNOWFLAKE_EMOJI = new String(Character.toString('\u2744'));

  private Text gameOver = new Text();
  private Text winText = new Text();
  private Text loseText = new Text();

  /**
   * Draws/creates the scene specified.
   * <p>
   * This screen contains the game over message as well as the player's results (win/lose)
   *
   * @param width      the width of the window the scene will be displayed in
   * @param height     the height of the window the scene will be displayed in
   * @param background the background color of the scene
   * @param won        the player's results, true if they won, false if they lost
   * @return the Scene created
   * @see Scene
   */
  public Scene drawScene(int width, int height, Paint background, boolean won) {
    setBasicTextOptions(gameOver, "GAME OVER", STATUS_TEXT_SIZE, Color.LIGHTBLUE);
    gameOver.setX(width / 2 - gameOver.getBoundsInParent().getWidth() / 2);
    gameOver.setY(2 * (height / 5));

    setBasicTextOptions(winText, SNOWFLAKE_EMOJI + "  YOU WIN!  " + SNOWFLAKE_EMOJI,
        RESULT_TEXT_SIZE, Color.LIGHTBLUE);
    winText.setX(width / 2 - winText.getBoundsInParent().getWidth() / 2);
    winText.setY(2 * (height / 5) + 2 * gameOver.getBoundsInParent().getHeight());

    setBasicTextOptions(loseText, SNOWFLAKE_EMOJI + "  YOU LOSE  " + SNOWFLAKE_EMOJI,
        RESULT_TEXT_SIZE, Color.LIGHTBLUE);
    loseText.setX(width / 2 - loseText.getBoundsInParent().getWidth() / 2);
    loseText.setY(2 * (height / 5) + 2 * gameOver.getBoundsInParent().getHeight());

    root.getChildren().add(gameOver);
    if (won) {
      root.getChildren().add(winText);
    } else {
      root.getChildren().add(loseText);
    }

    return new Scene(root, width, height, background);
  }
}
