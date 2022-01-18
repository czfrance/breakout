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

  private static final int statusTextSize = 30;
  private static final int resultTextSize = 50;

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
    setBasicTextOptions(gameOver, "GAME OVER", statusTextSize, Color.LIGHTBLUE);
    gameOver.setX(width / 2 - gameOver.getBoundsInParent().getWidth() / 2);
    gameOver.setY(2 * (height / 5));

    setBasicTextOptions(winText, "YOU WIN!", resultTextSize, Color.LIGHTBLUE);
    winText.setX(width / 2 - winText.getBoundsInParent().getWidth() / 2);
    winText.setY(2 * (height / 5) + 2 * gameOver.getBoundsInParent().getHeight());

    setBasicTextOptions(loseText, "YOU LOSE", resultTextSize, Color.LIGHTBLUE);
    loseText.setX(width / 2 - loseText.getBoundsInParent().getWidth() / 2);
    loseText.setY(2 * (height / 5) + 2 * gameOver.getBoundsInParent().getHeight());

    root.getChildren().add(gameOver);
    if (won) {
      root.getChildren().add(winText);
    } else {
      root.getChildren().add(loseText);
    }

    Scene scene = new Scene(root, width, height, background);
    return scene;
  }

}
