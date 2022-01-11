package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class Breakout {
    public static final String RESOURCE_PATH = "/";
    public static final String BALL_IMAGE = RESOURCE_PATH + "snowball.png";
    public static final String PADDLE_IMAGE = RESOURCE_PATH + "paddle.png";
    public static final String WOOD_BLOCK_IMAGE = RESOURCE_PATH + "wood-block.png";
    public static final int BALL_SPEED = 80;


    private Ball ball;
    private Paddle paddle;
    private Block wood_blk;
    private ArrayList<Block> blocks = new ArrayList<>();
    private int wWidth;
    private int wHeight;

    public Scene setupGame(int width, int height, Paint background) {
        wWidth = width;
        wHeight = height;
        Group root = new Group();
        Image ball_img = new Image(getClass().getResourceAsStream(BALL_IMAGE));
        Image paddle_img = new Image(getClass().getResourceAsStream(PADDLE_IMAGE));
        Image wood_blk_img = new Image(getClass().getResourceAsStream(WOOD_BLOCK_IMAGE));

        //NOTE: NEED TO REPLACE ALL IMAGES EXCEPT BALL WITH PROPERLY SIZED ONES
            //CAN'T HAVE ADDITIONAL BLANK SPACE
        ball = new Ball(20, BALL_SPEED, 30, ball_img, 50, 50);
        paddle = new Paddle(175, 300, 75, 50, paddle_img);
        wood_blk = new Block(100, 100, 50, 50, wood_blk_img);
        blocks.add(wood_blk);

        root.getChildren().add(ball);
        root.getChildren().add(paddle);
        root.getChildren().add(wood_blk);

        Scene scene = new Scene(root, width, height, background);

        return scene;
    }

    public void step (double elapsedTime) {
        boolean intersect = isIntersecting(blocks, paddle, ball);
        ball.move(wWidth, wHeight, elapsedTime, intersect);
    }

    private boolean isIntersecting(ArrayList<Block> blks, Paddle p, Ball b) {
        for (Block blk : blks) {
            if (blk.getBoundsInParent().intersects(b.getBoundsInParent())) {
                return true;
            }
        }
        if (p.getBoundsInParent().intersects(b.getBoundsInParent())) {
            return true;
        }
        return false;
    }

    // Name for a potentially complex comparison to make code more readable
//    private boolean isIntersecting (Bouncer a, Rectangle b) {
//        // with images can only check bounding box (as it is calculated in container with other objects)
//        return b.getBoundsInParent().intersects(a.getBoundsInParent());
//        // with shapes, can check precisely (in this case, it is easy because the image is circular)
//    }
}
