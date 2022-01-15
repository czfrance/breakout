package breakout;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/*
    RESOURCES
    ImagePattern: https://stackoverflow.com/questions/22848829/how-do-i-add-an-image-inside-a-rectangle-or-a-circle-in-javafx
 */

public class Paddle extends Rectangle {

  private Image[] images;
  //private int

  public Paddle(double x, double y, double w, double h, Image[] imgs) {
    super(x, y, w, h);
    images = imgs;
    this.setFill(new ImagePattern(images[0]));
  }

  public boolean atBorder(int w, boolean rightBorder) {
    if (rightBorder) {
      return this.getX() >= (w - this.getWidth());
    }
    return this.getX() <= 0;
  }

  public double newPaddleX(boolean right, int width, int speed) {
    if (right && !this.atBorder(width, true)) {
      return this.getX() + speed;
    } else if ((!right) && !this.atBorder(width, false)) {
      return this.getX() - speed;
    } else {
      return this.getX();
    }
  }

  public void makeSlippery() {
    this.setFill(new ImagePattern(images[1]));
  }

  private void updatePaddle() {

  }
}
