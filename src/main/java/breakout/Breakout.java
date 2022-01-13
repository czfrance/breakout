package breakout;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.Shape;
import java.util.ArrayList;

public class Breakout {
    public static final String RESOURCE_PATH = "/";
    public static final String BALL_IMAGE = RESOURCE_PATH + "snowball.png";
    public static final String PADDLE_IMAGE = RESOURCE_PATH + "paddle.png";
    public static final String WOOD_BLOCK_IMAGE = RESOURCE_PATH + "wood-block.png";
    public static final int BALL_SPEED = 80;
    public static final int PADDLE_SPEED = 8;

    private Ball ball;
    private Paddle paddle;
    private Block wood_blk;
    private Block blk2;
    private ArrayList<Block> blocks = new ArrayList<>();
    private int wWidth;
    private int wHeight;
    private Group root = new Group();

    public Scene setupGame(int width, int height, Paint background) {
        wWidth = width;
        wHeight = height;
        //Group root = new Group();
        Image ball_img = new Image(getClass().getResourceAsStream(BALL_IMAGE));
        Image paddle_img = new Image(getClass().getResourceAsStream(PADDLE_IMAGE));
        Image wood_blk_img = new Image(getClass().getResourceAsStream(WOOD_BLOCK_IMAGE));

        ball = new Ball(50, BALL_SPEED, 30, ball_img, 200, 200);
        paddle = new Paddle(175, 360, 50, 20, paddle_img);
        wood_blk = new Block(100, 100, 30, 15, wood_blk_img);
        blk2 = new Block(250, 250, 30, 15, wood_blk_img);
        blocks.add(wood_blk);
        blocks.add(blk2);

        root.getChildren().add(ball);
        root.getChildren().add(paddle);
        root.getChildren().add(wood_blk);
        root.getChildren().add(blk2);

        Scene scene = new Scene(root, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    public void step (double elapsedTime) {
        ArrayList<Boolean> intersect = isIntersecting(blocks, paddle, ball);
        ball.move(wWidth, wHeight, elapsedTime, intersect.get(0), intersect.get(1));
    }
//ERROR WHEN HIT AND BALL IS NOT COMPLETELY WITHIN BLOCK (NEAR CORNERS)
    private ArrayList<Boolean> isIntersecting(ArrayList<Block> blks, Paddle p, Ball b) {
        for (Block blk : blks) {
            ArrayList<Boolean> ret = intersect(blk, b);
            if (ret.get(0) || ret.get(1)){
                root.getChildren().remove(blk);
                blocks.remove(blk);
                return ret;
            }
        }
        return intersect(p, b);
    }

    private ArrayList<Boolean> intersect(Node a, Node b) {
        ArrayList<Boolean> ret = new ArrayList<>();
        ret.add(false);
        ret.add(false);
        Bounds aBounds = a.getBoundsInParent();
        Bounds bBounds = b.getBoundsInParent();
        if (aBounds.intersects(bBounds)) {
            if (contains(aBounds.getMinX(), aBounds.getMaxX(), bBounds.getMinX(), bBounds.getMaxX())) {
                ret.set(0, true);
                return ret;
            }
            ret.set(1, true);
            return ret;
        }
        return ret;
    }

    private boolean contains(double a1, double a2, double b1, double b2) {
        return ((a1<b1&&a2>b2) || (b1<a1&&b2>a2));
    }

    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT -> paddle.setX(newPaddleX(true));
            case LEFT -> paddle.setX(newPaddleX(false));
            case UP, DOWN -> paddle.setX(paddle.getX());
        }
    }

    private double newPaddleX(boolean right) {
        if (right && !paddle.atBorder(wWidth, true)) {
            return paddle.getX() + PADDLE_SPEED;
        }
        else if ((!right) && !paddle.atBorder(wWidth, false)) {
            return paddle.getX() - PADDLE_SPEED;
        }
        else {
            return paddle.getX();
        }
    }

}
