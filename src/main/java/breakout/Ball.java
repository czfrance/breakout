package breakout;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.lang.Math;

public class Ball extends ImageView{
    int speed;
    double angle;
    //boolean right;

    public Ball (int sz, int sp, double a, Image im, double x, double y){
        super(im);
        super.setFitWidth(sz);
        super.setFitHeight(sz);
        super.setX(x);
        super.setY(y);
        speed = sp;
        angle = a;
    }

    public void move(int w, int h, double elapsedTime, boolean intersectHoriz, boolean intersectVert) {
//        if (intersect) {
//            angle = 360 - angle;
//        }
        if (onVertBorder(w) || intersectVert) {
            //right = !right;
            if (angle <= 180) {
                angle = 180 - angle;
            } else {
                angle = 540 - angle;
            }
            //System.out.println("horizontal bounce");
            System.out.println(intersectHoriz + " " + intersectVert + " " + angle);
        }

        if (onHorizBorder(h) || intersectHoriz) {
            angle = 360 - angle;
            //System.out.println("vertical bounce");
            System.out.println(intersectHoriz + " " + intersectVert);
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
}