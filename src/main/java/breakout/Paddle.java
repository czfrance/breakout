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
  private static final double MAX_DEVIATION = 0.5;
  private static final double DEVIATION_INCREMENT = 0.016; //31.25 bounces to get to full deviation

  private Image[] images;
  private double percentDeviationRange = 0;
  private boolean slippery = false;

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

  public void hit() {
    if (percentDeviationRange < MAX_DEVIATION-DEVIATION_INCREMENT) {
      percentDeviationRange += DEVIATION_INCREMENT;
    }
  }

  public void makeSlippery(boolean active) {
    if (active) {
      slippery = true;
      this.setFill(new ImagePattern(images[1]));
    }
    else {
      slippery = false;
      this.setFill(new ImagePattern(images[0]));
    }
  }

  public double getPercentDeviation() {
    return 0;
    //return percentDeviationRange;
  }

  public boolean isSlippery() {
    return slippery;
  }

}
