package breakout;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Block extends Rectangle {
  private Image[] images;
  private int hitsToBreak;
  private boolean isIced = false;
  private boolean isBroken;
  private int speed;
  private int currSpeed;
  private double angle;
  double speedFraction = 0.001;

  public Block(double x, double y, double w, double h, Image[] imgs, int hitsNeeded, int sp,
      double a) {
    super(x+1, y+1, w, h);
    hitsToBreak = hitsNeeded;
    speed = sp;
    currSpeed = speed;
    images = imgs;
    angle = a;
    this.setFill(new ImagePattern(images[0]));
  }

  public void move(int w, int h, double elapsedTime) {
    if (onVertBorder(w)) {
      if (angle <= 180) {
        angle = 180 - angle;
      } else {
        angle = 540 - angle;
      }
    }

    if (onHorizBorder(h)) {
      angle = 360 - angle;
    }

    this.setX(this.getX() + (currSpeed*speedFraction*Math.cos(Math.toRadians(angle)))*elapsedTime);
    this.setY(this.getY() - (currSpeed*speedFraction*Math.sin(Math.toRadians(angle)))*elapsedTime);

    if (speedFraction < 1) {speedFraction += 0.0001;}
  }

  private boolean onVertBorder(int w) {
    return this.getX() >= (w - this.getWidth()) || this.getX() <= 0;
  }

  private boolean onHorizBorder(int h) {
    return this.getY() >= (h - this.getHeight()) || this.getY() <= 0;
  }

  public void hit() {
    if (isIced) {
      hitsToBreak -= 2;
    }
    else {
      hitsToBreak--;
    }
  }

  public boolean broken() {
    return hitsToBreak <= 0;
  }

  public void makeIced() {
    this.setFill(new ImagePattern(images[1]));
  }

  public void freeze() {
    currSpeed = 0;
  }

  public void unfreeze() {
    currSpeed = speed;
  }
}
