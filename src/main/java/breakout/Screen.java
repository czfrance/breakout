package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Screen {

  protected Group root = new Group();

  protected void setBasicTextOptions(Text textName, String text, int fontSize, Color color) {
    textName.setText(text);
    textName.setFont(new Font(fontSize));
    textName.setFill(color);
  }
}
