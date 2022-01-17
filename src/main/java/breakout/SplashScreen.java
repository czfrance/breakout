package breakout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SplashScreen {

  private Group root = new Group();

  private Text title = new Text();
  private Text rules = new Text();
  private Text lvlText = new Text();
  private Text instructions = new Text();

  public Scene drawScene(int width, int height, Paint background, int lvl) {
    title.setText("BREAKOUT: WINTER WONDERLAND");
    title.setFont(new Font(30));
    title.setFill(Color.LIGHTBLUE);
    title.setX(width/2 - title.getBoundsInParent().getWidth()/2);
    title.setY(height/4);

    rules.setText("Move the paddle (arrow keys) to catch the ball before it hits the ground \n"
        + "Different blocks require different numbers of hits to break \n"
        + "Some blocks perform special actions when broken \n"
        + "Break blocks to win \n \n"
        + "Good Luck!");
    rules.setY(height/4 + 75);
    rules.setFont(new Font(18));
    rules.setFill(Color.LIGHTBLUE);
    rules.setX(width/2 - rules.getBoundsInParent().getWidth()/2);
    rules.setY(height/4 + 2*title.getBoundsInParent().getHeight());

    lvlText.setText("LEVEL " + lvl + getLevelName(lvl));
    lvlText.setFont(new Font(30));
    lvlText.setFill(Color.LIGHTBLUE);
    lvlText.setX(width/2 - lvlText.getBoundsInParent().getWidth()/2);
    lvlText.setY(height/4 + 2*title.getBoundsInParent().getHeight()
        + 1.5*rules.getBoundsInParent().getHeight());

    instructions.setText("Press enter to begin");
    instructions.setFont(new Font(18));
    instructions.setFill(Color.LIGHTBLUE);
    instructions.setX(width/2 - instructions.getBoundsInParent().getWidth()/2);
    instructions.setY(height/4 + 2*title.getBoundsInParent().getHeight()
        + 1.5*rules.getBoundsInParent().getHeight() + 1.5*lvlText.getBoundsInParent().getHeight());

    root.getChildren().add(title);
    root.getChildren().add(rules);
    root.getChildren().add(lvlText);
    root.getChildren().add(instructions);

    Scene scene = new Scene(root, width, height, background);
    return scene;
  }

  private String getLevelName(int lvl) {
    switch (lvl) {
      case 1 -> {return ": NOVEMBER";}
      case 2 -> {return ": DECEMBER";}
      default -> {return ": JANUARY";}
    }
  }
}
