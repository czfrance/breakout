package breakout;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.lang.Math;

public class Ball extends ImageView {

  private int speed;
  private double angle;

  public Ball(int sz, int sp, double a, Image im, double x, double y) {
    super(im);
    this.setFitWidth(sz);
    this.setFitHeight(sz);
    this.setX(x);
    this.setY(y);
    speed = sp;
    angle = a;
  }

  public void move(int w, int h, double elapsedTime, boolean intersectHoriz,
      boolean intersectVert) {
    if (onVertBorder(w) || intersectVert) {
      if (angle <= 180) {
        angle = 180 - angle;
      } else {
        angle = 540 - angle;
      }
    }

    if (onHorizBorder(h) || intersectHoriz) {
      angle = 360 - angle;
    }

    this.setX(this.getX() + (speed * Math.cos(Math.toRadians(angle))) * elapsedTime);
    this.setY(this.getY() - (speed * Math.sin(Math.toRadians(angle))) * elapsedTime);
  }

  private boolean onVertBorder(int w) {
    return this.getX() >= (w - this.getFitWidth()) || this.getX() <= 0;
  }

  private boolean onHorizBorder(int h) {
    return this.getY() >= (h - this.getFitHeight()) || this.getY() <= 0;
  }

  public boolean lostLife(int h) {
    return this.getY() >= (h - this.getFitHeight());
  }
}
