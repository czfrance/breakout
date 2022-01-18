package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/*
    RESOURCES
    ImagePattern: https://stackoverflow.com/questions/22848829/how-do-i-add-an-image-inside-a-rectangle-or-a-circle-in-javafx
 */

/**
 * Paddle is the class that holds all information regarding the paddle being used in the game. The
 * paddle moves horizontally at the bottom of the playable window. The goal of the player is to use
 * this to prevent the ball from hitting the bottom of the screen.
 * <p>
 * It contains 2 states: normal and "slippery". Slippery paddle increases the ball's takeoff angle
 * measured from vertical. For implementation, see Ball.slip()
 * <p>
 * NOTE: this is not just a normal surface. The percent deviation of the ball's resulting bounce-off
 * angle is increased as the paddle is hit more and more times. FOr implementation, see
 * Ball.deviatePath()
 * <p>
 * Extends Rectangle, depends on Image and ImagePattern.
 * <p>
 * To create: Paddle p = new Paddle(initialXCoord, initialYCoord, paddleWidth, paddleHeight,
 * paddleImages)
 * <p>
 * Call any of its methods to perform actions.
 *
 * @author Cynthia France
 * @see Ball
 */
public class Paddle extends Rectangle {

  public static final double MAX_DEVIATION = 0.5;
  public static final double DEVIATION_INCREMENT = 0.016; //31.25 bounces to get to full deviation
  public static final int SIZE_INCREASE = 30;

  private final Image[] images;
  private final int mySpeed;
  private double percentDeviationRange = 0;
  private boolean slippery = false;


  /**
   * Creates an instance of Paddle
   *
   * @param x      initial X coordinate of Paddle object
   * @param y      initial Y coordinate of Paddle object
   * @param width  width of the paddle
   * @param height height of the paddle
   * @param imgs   array of 2 Images: index 0 contains the paddle's normal look and index 1 contains
   *               the paddle's "slippery" look (effect of a disadvantage)
   * @param speed  the speed at which the paddle travels
   */
  public Paddle(double x, double y, double width, double height, Image[] imgs, int speed) {
    super(x, y, width, height);
    images = imgs;
    mySpeed = speed;
    this.setFill(new ImagePattern(images[0]));
  }

  /**
   * Sets basic characteristics of paddle: x and y positions, paddle width
   *
   * @param x     the x coordinate of the paddle
   * @param y     the y coordinate of the paddle
   * @param width the width fo the paddle
   */
  public void setSpecifics(double x, double y, double width) {
    this.setX(x);
    this.setY(y);
    this.setWidth(width);
  }

  /**
   * sets the new x position of the paddle
   *
   * @param right       true if the paddle is moving to the right, false if to the left
   * @param windowWidth the width of the window
   */
  public void setNewXPos(boolean right, int windowWidth) {
    if (right && !this.atBorder(windowWidth, true)) {
      this.setX(this.getX() + mySpeed);
    } else if ((!right) && !this.atBorder(windowWidth, false)) {
      this.setX(this.getX() - mySpeed);
    } else {
      this.setX(this.getX());
    }
  }

  /**
   * Tells the paddle that it has been hit by the ball, increases the percentDeviationRange by
   * DEVIATION_INCREMENT
   */
  public void hit() {
    if (percentDeviationRange < MAX_DEVIATION - DEVIATION_INCREMENT) {
      percentDeviationRange += DEVIATION_INCREMENT;
    }
  }

  /**
   * updates the "slippery" aspect & image of the paddle based on active
   *
   * @param active true if the paddle should be slippery, false if it should be normal
   */
  public void makeSlippery(boolean active) {
    if (active) {
      slippery = true;
      this.setFill(new ImagePattern(images[1]));
    } else {
      slippery = false;
      this.setFill(new ImagePattern(images[0]));
    }
  }

  /**
   * gives the percentDeviationRange
   *
   * @return percentDeviationRange, the percentage with which the ball bounce off angle should
   * deviate from the norm
   */
  public double getPercentDeviation() {
    return percentDeviationRange;
  }

  /**
   * tells the slippery status of the paddle
   *
   * @return true if the paddle is slippery, false if it is normal
   */
  public boolean isSlippery() {
    return slippery;
  }

  /**
   * Increases the width of the paddle by SIZE_INCREASE
   *
   * @param windowWidth the width of the window
   */
  public void enlargePaddle(int windowWidth) {
    if (this.getWidth() < windowWidth) {
      double newWidth = this.getWidth() + SIZE_INCREASE;
      newWidth = (newWidth > windowWidth) ? windowWidth : newWidth;
      this.setX(calcNewXLocation(newWidth));
      this.setWidth(newWidth);
    }
  }

  private boolean atBorder(int windowWidth, boolean rightBorder) {
    if (rightBorder) {
      return this.getX() >= (windowWidth - this.getWidth());
    }
    return this.getX() <= 0;
  }

  private double calcNewXLocation(double newWidth) {
    return this.getX() + (this.getWidth() / 2) - (newWidth / 2);
  }
}
