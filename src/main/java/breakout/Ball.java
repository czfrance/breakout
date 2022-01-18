package breakout;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Ball is the class that holds all information regarding the ball being used in the game. The ball
 * bounces around the playable window destroying blocks.
 * <p>
 * This class extends ImageView, not Circle
 * <p>
 * Extends ImageView, depends on Math, ArrayList, List, Random, Bounds, Node, Image.
 * <p>
 * To create: Ball b = new Ball(ballDiameter, ballSpeed, initialAngle, ballImage, initialXCoord,
 * initialYCoord)
 * <p>
 * Call any of its methods to perform actions.
 *
 * @author Cynthia France
 */
public class Ball extends ImageView {

  public static final int SLIP_ANGLE = 10;
  public static final int MIN_BALL_ANGLE = 10;
  public static final int SPEED_INCREMENT = 30;
  public static final int AVALANCHE_MULTIPLE = 2;

  private int speed;
  private double angle;
  private final int size;
  private final int avalancheSize;

  /**
   * Creates an instance of Ball
   *
   * @param size  diameter of ball
   * @param speed speed of ball
   * @param angle initial angle to travel in. Is measured increasing counterclockwise with 0 degrees
   *              being to the right.
   * @param img   the ball's image/what it looks like
   * @param x     initial X coordinate of Ball object
   * @param y     initial Y coordinate of Ball object
   */
  public Ball(int size, int speed, double angle, Image img, double x, double y) {
    super(img);
    this.setFitWidth(size);
    this.setFitHeight(size);
    this.setX(x);
    this.setY(y);
    this.speed = speed;
    this.angle = angle;
    this.size = size;
    avalancheSize = size * AVALANCHE_MULTIPLE;
  }

  /**
   * Sets basic characteristics of ball: x and y positions, ball size, ball speed
   *
   * @param x     the x coordinate of the ball
   * @param y     the y coordinate of the ball
   * @param size  the diameter of the ball
   * @param speed the speed at which the ball travels
   */
  public void setSpecifics(double x, double y, int size, int speed) {
    this.setX(x);
    this.setY(y);
    this.setFitWidth(size);
    this.setFitHeight(size);
    this.speed = speed;
  }

  /**
   * Calculates the position of the ball after elapsedTime seconds and moves the ball there
   *
   * @param width          width of the playable area
   * @param height         height of the playable area
   * @param margin         the Text margin, to be subtracted from height to calculate the ball's
   *                       movement
   * @param intersectHoriz true if the ball is intersecting the horizontal component of an object
   * @param intersectVert  true if the ball is intersecting the vertical component of an object
   * @param elapsedTime    the amount of time that has passed since the last frame (in seconds)
   */
  public void move(int width, int height, int margin, boolean intersectHoriz, boolean intersectVert,
      double elapsedTime) {
    if (onVertBorder(width) || intersectVert) {
      if (angle <= 180) {
        angle = 180 - angle;
      } else {
        angle = 540 - angle;
      }
    }

    if (onHorizBorder(height, margin) || intersectHoriz) {
      angle = 360 - angle;
    }

    this.setX(this.getX() + (speed * Math.cos(Math.toRadians(angle))) * elapsedTime);
    this.setY(this.getY() - (speed * Math.sin(Math.toRadians(angle))) * elapsedTime);
  }

  /**
   * Checks if the ball intersects/touches the passed in object/Node
   *
   * @param object the object of interest. Check if ball is in contact with it
   * @return List of 2 booleans, the first is true if the ball intersects with the object's
   * horizontal sides, the second is true if the ball intersects with the object's vertical sides.
   */
  public List<Boolean> intersectsWith(Node object) {
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

  /**
   * Calculates the new angle with which the ball with travel
   *
   * @param percentDev The maximum percent of 180 degrees the new angle can deviate from its current
   *                   angle.
   */
  public void deviatePath(double percentDev) {
    Random rand = new Random();
    double maxDeviation = percentDev * 180;
    double minAngle = angle - maxDeviation;
    double angleDev = rand.nextDouble(maxDeviation * 2 + 1);
    angle = calcNewDeviationAngle(angleDev, minAngle);
  }

  /**
   * Calculates the new ball angle, angle +- SLIP_ANGLE, from the current ball angle
   */
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

  /**
   * Calculates if the ball has hit the bottom of the playable area (and thus lost a life)
   *
   * @param windowHeight the height of the playable area
   * @return true if ball has hit the bottom, false otherwise
   */
  public boolean lostLife(int windowHeight) {
    return this.getY() >= (windowHeight - this.getFitHeight());
  }

  /**
   * Increases the size of the ball to avalancheSize, which is AVALANCHE_MULTIPLE times the normal
   * size of the ball.
   */
  public void makeAvalanche() {
    this.setFitWidth(avalancheSize);
    this.setFitHeight(avalancheSize);
  }

  /**
   * Resets the size of the ball to its normal size following expiration of avalanche power up
   */
  public void unAvalanche() {
    this.setFitWidth(size);
    this.setFitHeight(size);
  }

  /**
   * Increases the speed at which the ball travels by SPEED_INCREMENT
   */
  public void incSpeed() {
    speed += SPEED_INCREMENT;
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
}
