package breakout;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/*
    RESOURCES
    ImagePattern: https://stackoverflow.com/questions/22848829/how-do-i-add-an-image-inside-a-rectangle-or-a-circle-in-javafx
 */

public class Paddle extends Rectangle {
    Image image;

    public Paddle (double x, double y, double w, double h, Image img) {
        super(x, y, w, h);
        super.setFill(new ImagePattern(img));

    }
}
