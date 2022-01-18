package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Screen is the parent class of SplashScreen and ResultsScreen. A basic class that creates a Group
 * root and contains protected function setBasicTextOptions(), which sets basic properties of Text
 * objects, namely its text, font size, and text color
 * <p>
 * This class is not to be created on its own. Its subclasses are the classes that are meant to be
 * used
 * <p>
 * Depends on Group, Color, Font, and Text
 * <p>
 * To use Screen, create instances of its child classes, SplashScreen and ResultsScreen
 *
 * @author Cynthia France
 * @see SplashScreen
 * @see ResultsScreen
 */
public class Screen {

  protected Group root = new Group();

  /**
   * Sets the basic properties (text, font size, text color) of a Text object
   *
   * @param textName the Text object
   * @param text     what the Text will display
   * @param fontSize the size of the Text
   * @param color    the color of the Text
   */
  protected void setBasicTextOptions(Text textName, String text, int fontSize, Color color) {
    textName.setText(text);
    textName.setFont(new Font(fontSize));
    textName.setFill(color);
  }
}
