package breakout;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Block extends Rectangle {

  public Block(double x, double y, double w, double h, Image img) {
    super(x, y, w, h);
    super.setFill(new ImagePattern(img));

  }
}
