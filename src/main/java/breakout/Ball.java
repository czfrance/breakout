package breakout;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.lang.Math;
import java.util.Random;

public class Ball extends ImageView {
  private static final int SLIP_ANGLE = 10;
  private static final int MIN_BALL_ANGLE = 10;
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
    angle = calcNewDeviationAngle(idk, minAngle);
  }

  public void slip() {
    //angle of ball coming into paddle is always between 180 and 360
    double newAngle;
    if (angle < 270) {
      newAngle = angle-SLIP_ANGLE;
      angle = (newAngle > 180+MIN_BALL_ANGLE) ? newAngle : 180+MIN_BALL_ANGLE+1;
    }
    else if (angle < 270) {
      newAngle = angle+SLIP_ANGLE;
      angle = (newAngle < 360-MIN_BALL_ANGLE) ? newAngle : 360-MIN_BALL_ANGLE-1;
    }
  }

  private double calcNewDeviationAngle(double value, double minAngle) {
    //angle of ball coming into paddle is always between 180 and 360
    double newAngle = minAngle + value;

    if (newAngle <= 180 + MIN_BALL_ANGLE) {
      return 180 + MIN_BALL_ANGLE + 1;
    } else if (newAngle >= 360 - MIN_BALL_ANGLE) {
      return 360 - MIN_BALL_ANGLE - 1;
    }
    return newAngle;
  }

  public boolean lostLife(int h) {
    return this.getY() >= (h - this.getFitHeight());
  }
}
