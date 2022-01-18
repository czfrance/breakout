package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This is the class that manages all Text for the Breakout game (ie lives left, block hits, blocks
 * destroyed, etc.)
 * <p>
 * Its purpose is to keep track of all text, load and create Text objects, set and reset their
 * content/locations to facilitate game play
 * <p>
 * Depends on Group, Color, Font, Text
 * <p>
 * to create: BreakoutText myText = new BreakoutText();
 * <p>
 * Use the methods below to draw/display the text on the screen and update their contents
 *
 * @author Cynthia France
 */
public class BreakoutText {

  public static final int TEXT_MARGIN_SIZE = 25;
  public static final int BASIC_FONT_SIZE = 18;
  public static final int TITLE_FONT_SIZE = 50;

  private final Text lives = new Text();
  private final Text hit = new Text();
  private final Text destroyed = new Text();
  private final Text currPower = new Text();
  private final Text currDisAdv = new Text();
  private final Text winMsg = new Text();
  private final Text nextLvlMsg = new Text();
  private final Text loseMsg = new Text();
  private final Text lvlText = new Text();

  private double[] hitsTextLoc;
  private double[] livesTextLoc;
  private double[] destroyedTextLoc;
  private double[] powerupTextLoc;
  private double[] disadvTextLoc;
  private double[] lvlTextLoc;

  private int windowWidth;
  private int windowHeight;

  /**
   * Creates an instance of BreakoutText
   * <p>
   * Loads and creates all Text and specifies their location in the screen
   *
   * @param width  the width of the window
   * @param height the height of the window
   */
  public BreakoutText(int width, int height) {
    windowWidth = width;
    windowHeight = height;

    setUpText();
  }

  private void setUpText() {
    addGameTextLocations();
  }

  private void addGameTextLocations() {
    int marginSpacer = 5;

    lvlTextLoc = new double[]{marginSpacer, TEXT_MARGIN_SIZE - marginSpacer};
    livesTextLoc = new double[]{windowWidth / 5, TEXT_MARGIN_SIZE - marginSpacer};
    hitsTextLoc = new double[]{windowWidth / 3 + 30, TEXT_MARGIN_SIZE - marginSpacer};
    destroyedTextLoc = new double[]{2 * (windowWidth / 3), TEXT_MARGIN_SIZE - marginSpacer};
    powerupTextLoc = new double[]{marginSpacer, windowHeight - marginSpacer};
    disadvTextLoc = new double[]{windowWidth / 2, windowHeight - marginSpacer};
  }

  /**
   * Sets the content and attributes of each Text object and sets their location in the screen. Adds
   * all Text needed to play the game to root to be displayed
   *
   * @param root         the Group of Nodes that will be displayed in the screen
   * @param level        the current level of game being played
   * @param livesLeft    the amount of lives the player has left
   * @param blocksHit    the number of blocks hit
   * @param blocksBroken the number of blocks broken
   */
  public void drawText(Group root, int level, int livesLeft, int blocksHit, int blocksBroken) {

    setBasicTextOptions(lvlText, "Level: " + level, BASIC_FONT_SIZE, Color.LIGHTBLUE);
    lvlText.setX(lvlTextLoc[0]);
    lvlText.setY(lvlTextLoc[1]);

    setBasicTextOptions(lives, "Lives: " + livesLeft, BASIC_FONT_SIZE, Color.LIGHTBLUE);
    lives.setX(livesTextLoc[0]);
    lives.setY(livesTextLoc[1]);

    setBasicTextOptions(hit, "Blocks Hit: " + blocksHit, BASIC_FONT_SIZE, Color.LIGHTBLUE);
    hit.setX(hitsTextLoc[0]);
    hit.setY(hitsTextLoc[1]);

    setBasicTextOptions(destroyed, "Blocks Destroyed: " + blocksBroken,
        BASIC_FONT_SIZE, Color.LIGHTBLUE);
    destroyed.setX(destroyedTextLoc[0]);
    destroyed.setY(destroyedTextLoc[1]);

    setBasicTextOptions(currPower, "Power Up: --", BASIC_FONT_SIZE, Color.LIGHTBLUE);
    currPower.setX(powerupTextLoc[0]);
    currPower.setY(powerupTextLoc[1]);

    setBasicTextOptions(currDisAdv, "Disadvantage: --", BASIC_FONT_SIZE, Color.LIGHTBLUE);
    currDisAdv.setX(disadvTextLoc[0]);
    currDisAdv.setY(disadvTextLoc[1]);

    setBasicTextOptions(winMsg, "YOU WIN!", TITLE_FONT_SIZE, Color.MEDIUMSPRINGGREEN);
    winMsg.setX(windowWidth / 2 - winMsg.getBoundsInParent().getWidth() / 2);
    winMsg.setY(2 * (windowHeight / 5));

    setBasicTextOptions(nextLvlMsg, "Press enter to continue", BASIC_FONT_SIZE,
        Color.MEDIUMSPRINGGREEN);
    nextLvlMsg.setX(windowWidth / 2 - nextLvlMsg.getBoundsInParent().getWidth() / 2);
    nextLvlMsg.setY(2 * (windowHeight / 5) + 2 * winMsg.getBoundsInParent().getHeight());

    setBasicTextOptions(loseMsg, "YOU LOSE", TITLE_FONT_SIZE, Color.MEDIUMSPRINGGREEN);
    loseMsg.setX(windowWidth / 2 - loseMsg.getBoundsInParent().getWidth() / 2);
    loseMsg.setY(2 * (windowHeight / 5));

    root.getChildren().add(lives);
    root.getChildren().add(hit);
    root.getChildren().add(destroyed);
    root.getChildren().add(currPower);
    root.getChildren().add(currDisAdv);
    root.getChildren().add(lvlText);
  }

  /**
   * Updates the text contents to reflect the current state of the game. If the player has won/lost,
   * it will also display the winning/losing message
   *
   * @param root          the Group of Nodes that will be displayed in the screen
   * @param livesLeft     the amount of lives the player has left
   * @param blocksHit     the number of blocks hit
   * @param blocksBroken  the number of blocks broken
   * @param powerUpActive the power up currently in play, if any
   * @param disAdvActive  the disadvantage currently in play, if any
   * @param won           whether the player has won the game. True if they have won, false
   *                      otherwise
   */
  public void updateText(Group root, int livesLeft, int blocksHit, int blocksBroken,
      String powerUpActive, String disAdvActive, boolean won) {

    lives.setText("Lives: " + livesLeft);
    hit.setText("Blocks Hit: " + blocksHit);
    destroyed.setText("Blocks Destroyed: " + blocksBroken);

    if (powerUpActive == null) {
      currPower.setText("Power Up: --");
    } else {
      currPower.setText("Power Up: " + powerUpActive);
    }

    if (disAdvActive == null) {
      currDisAdv.setText("Disadvantage: --");
    } else {
      currDisAdv.setText("Disadvantage: " + disAdvActive);
    }

    if (won) {
      root.getChildren().add(winMsg);
      root.getChildren().add(nextLvlMsg);
    }

    if (livesLeft <= 0) {
      root.getChildren().add(loseMsg);
      root.getChildren().add(nextLvlMsg);
    }
  }

  private void setBasicTextOptions(Text textName, String text, int fontSize, Color color) {
    textName.setText(text);
    textName.setFont(new Font(fontSize));
    textName.setFill(color);
  }

}
