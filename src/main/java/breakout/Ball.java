package breakout;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.lang.Math;
import java.util.Random;

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

  public void deviatePath(double percentDev) {
    Random rand = new Random();
    double maxDeviation = percentDev * 180;
    double minAngle = angle - maxDeviation;
    double idk = rand.nextDouble(maxDeviation*2);
    angle = calcNewAngle(idk, minAngle);
  }

  private double calcNewAngle(double value, double minAngle) {
    double newAngle = minAngle + value;
    if (angle < 180) {
      if (newAngle <= 0) {
        return 1;
      }
      else if (newAngle >= 180) {
        return 179;
      }
    }
    else {
      if (newAngle <= 180) {
        return 181;
      }
      else if (newAngle >= 360) {
        return 359;
      }
    }
    return newAngle;
  }

  public boolean lostLife(int h) {
    return this.getY() >= (h - this.getFitHeight());
  }
}
