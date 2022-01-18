package breakout;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends ImageView {

  private static final int SLIP_ANGLE = 10;
  private static final int MIN_BALL_ANGLE = 10;
  private static final int SPEED_INCREMENT = 30;
  private int speed;
  private double angle;
  private int size;
  private int avalancheSize;

  public Ball(int sz, int sp, double a, Image im, double x, double y) {
    super(im);
    this.setFitWidth(sz);
    this.setFitHeight(sz);
    this.setX(x);
    this.setY(y);
    speed = sp;
    angle = a;
    size = sz;
    avalancheSize = sz * 2;
  }

  public void setSpecifics(double x, double y, int sz, int sp) {
    this.setX(x);
    this.setY(y);
    this.setFitWidth(sz);
    this.setFitHeight(sz);
    speed = sp;
  }

  public void move(int w, int h, int margin, boolean intersectHoriz, boolean intersectVert,
      double elapsedTime) {
    if (onVertBorder(w) || intersectVert) {
      if (angle <= 180) {
        angle = 180 - angle;
      } else {
        angle = 540 - angle;
      }
    }

    if (onHorizBorder(h, margin) || intersectHoriz) {
      angle = 360 - angle;
    }

    this.setX(this.getX() + (speed * Math.cos(Math.toRadians(angle))) * elapsedTime);
    this.setY(this.getY() - (speed * Math.sin(Math.toRadians(angle))) * elapsedTime);
  }

  public List<Boolean> intersects(Node object) {
    List<Boolean> ret = new ArrayList<>();
    ret.add(false);
    ret.add(false);
    if (object == null) {
      return ret;
    }
    Bounds ballBounds = this.getBoundsInParent();
    Bounds otherObjBounds = object.getBoundsInParent();
    if (ballBounds.intersects(otherObjBounds)) {
      if (intersectsOnHoriz(ballBounds, otherObjBounds)) {
        ret.set(0, true);
      }
      if (intersectsOnVert(ballBounds, otherObjBounds)) {
        ret.set(1, true);
      }
    }
    return ret;
  }

  private boolean intersectsOnHoriz(Bounds ball, Bounds otherObj) {
    double ballCenterX = ball.getCenterX();
    double ballCenterY = ball.getCenterY();
    double extraXSpacing = Math.min(Math.abs(ballCenterX - otherObj.getMaxX()),
        Math.abs(ballCenterX - otherObj.getMinX()));
    double minX = otherObj.getMinX() - extraXSpacing;
    double maxX = otherObj.getMaxX() + extraXSpacing;
    if (!contains(otherObj.getMinY(), otherObj.getMaxY(), ballCenterY)) {
      if (contains(otherObj.getMinX(), otherObj.getMaxX(), ballCenterX)) {
        return true;
      } else {
        return contains(minX, maxX, ballCenterX);
      }
    }
    return false;
    //if y coord of ball is not in bounds of block y
    //and if center x of ball is within (block min x - abs(ballcenter-blockmaxx)) or
    //(block )block maxx + abs(ballcenter-blockmaxx)) then horz intersect
  }

  private boolean intersectsOnVert(Bounds a, Bounds b) {
    double centerX = a.getCenterX();
    double centerY = a.getCenterY();
    double extraY = Math.min(Math.abs(centerY - b.getMaxY()), Math.abs(centerY - b.getMinY()));
    double minY = b.getMinY() - extraY;
    double maxY = b.getMaxY() + extraY;
    if (!contains(b.getMinX(), b.getMaxX(), centerX)) {
      if (contains(b.getMinY(), b.getMaxY(), centerY)) {
        return true;
      } else {
        return contains(minY, maxY, centerY);
      }
    }
    return false;
  }

  private boolean contains(double a1, double a2, double b) {
    return (a1 <= b && a2 >= b);
  }

  private boolean onVertBorder(int w) {
    return this.getX() >= (w - this.getFitWidth()) || this.getX() <= 0;
  }

  private boolean onHorizBorder(int h, int margin) {
    return this.getY() >= (h - this.getFitHeight()) || this.getY() <= margin;
  }

  public void deviatePath(double percentDev) {
    Random rand = new Random();
    double maxDeviation = percentDev * 180;
    double minAngle = angle - maxDeviation;
    double angleDev = rand.nextDouble(maxDeviation * 2 + 1);
    angle = calcNewDeviationAngle(angleDev, minAngle);
  }

  public void slip() {
    //angle of ball coming into paddle is always between 180 and 360
    double newAngle;
    if (angle < 270) {
      newAngle = angle - SLIP_ANGLE;
      angle = (newAngle > 180 + MIN_BALL_ANGLE) ? newAngle : 180 + MIN_BALL_ANGLE + 1;
    } else if (angle < 270) {
      newAngle = angle + SLIP_ANGLE;
      angle = (newAngle < 360 - MIN_BALL_ANGLE) ? newAngle : 360 - MIN_BALL_ANGLE - 1;
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

  public void makeAvalanche() {
    this.setFitWidth(avalancheSize);
    this.setFitHeight(avalancheSize);
  }

  public void unAvalanche() {
    this.setFitWidth(size);
    this.setFitHeight(size);
  }

  public void incSpeed() {
    speed += SPEED_INCREMENT;
  }
}
