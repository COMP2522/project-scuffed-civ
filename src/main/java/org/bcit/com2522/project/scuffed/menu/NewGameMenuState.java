package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uiComponents.Button;
import org.bcit.com2522.project.scuffed.uiComponents.ButtonManager;
import org.bcit.com2522.project.scuffed.uiComponents.InputBox;
import org.bcit.com2522.project.scuffed.uiComponents.Label;
import processing.core.PApplet;

/**
 * The new game menu state, where the user can create a new game.
 */
public class NewGameMenuState extends MenuState {
  protected InputBox mapWidthInput;
  protected InputBox mapHeightInput;
  protected InputBox numPlayersInput;

  protected InputBox numAIInput;

  protected Label mapWidthLabel;

  protected Label mapHeightLabel;

  protected Label numPlayersLabel;

  protected Label numAILabel;

  protected Label errorMessageLabel;

  protected boolean showError = false;

  /**
   * Instantiates a new New game menu state.
   *
   * @param menu the menu
   */
  public NewGameMenuState(Menu menu) {
    super(menu, new ButtonManager(menu.scene));
    setup();
  }

  /**
   * Sets up the menu state.
   */
  @Override
  public void setup() {
    mapWidthInput = new InputBox(50, 100, 200, 30, 10, 10000, "16");
    mapHeightInput = new InputBox(50, 150, 200, 30, 10, 10000, "16");
    numPlayersInput = new InputBox(50, 200, 200, 30, 1, 10000, "2");
    numAIInput = new InputBox(50, 250, 200, 30, 0, 10000, "0");

    mapWidthLabel = new Label(50, 95, "Map Width:", 14);
    mapHeightLabel = new Label(50, 145, "Map Height:", 14);
    numPlayersLabel = new Label(50, 195, "Number of Players:", 14);
    numAILabel = new Label(50, 245, "Number of AI Players:", 14);
    errorMessageLabel =
        new Label(50, 250, "Invalid input! Please enter values within the specified range.", 14);

    Button backButton = new Button(50, 500, 250, 550, this::onBackClicked, "back", menu.scene);
    Button startButton = new Button(50, 600, 250, 650, this::onStartClicked, "start", menu.scene);

    buttonManager.add(backButton);
    buttonManager.add(startButton);
  }

  /**
   * Sets the selected input.
   *
   * @param selectedInput
   */
  protected void setSelectedInput(InputBox selectedInput) {
    mapWidthInput.setSelected(false);
    mapHeightInput.setSelected(false);
    numPlayersInput.setSelected(false);
    numAIInput.setSelected(false);
    selectedInput.setSelected(true);
  }

  @Override
  public boolean clicked(int xpos, int ypos) {
    if (super.clicked(xpos, ypos)) {
      return true;
    }
    if (mapWidthInput.isClicked(xpos, ypos)) {
      setSelectedInput(mapWidthInput);
      return true;
    } else if (mapHeightInput.isClicked(xpos, ypos)) {
      setSelectedInput(mapHeightInput);
      return true;
    } else if (numPlayersInput.isClicked(xpos, ypos)) {
      setSelectedInput(numPlayersInput);
      return true;
    } else if (numAIInput.isClicked(xpos, ypos)) {
      setSelectedInput(numAIInput);
      return true;
    }
    return false;
  }

  public void onBackClicked() {
    // Change the menu state to the New Game state
    menu.setState(new MainMenuMenuState(menu));
  }

  @Override
  public void draw(Window scene) {
    super.draw(scene);
    mapWidthInput.draw(scene);
    mapHeightInput.draw(scene);
    numPlayersInput.draw(scene);
    mapWidthLabel.draw(scene);
    mapHeightLabel.draw(scene);
    numPlayersLabel.draw(scene);
    numAILabel.draw(scene);
    numAIInput.draw(scene);

    if (showError) {
      errorMessageLabel.draw(scene);
    }
    drawHollowGrid(mapWidthInput.getIntValue(), mapHeightInput.getIntValue(), 400, 100, 800, 500);
  }


  /**
   * Key pressed.
   *
   * @param key the key
   */
  public void keyPressed(char key) {
    if (key == PApplet.BACKSPACE) {
      if (mapWidthInput.isSelected()) {
        mapWidthInput.removeCharacter();
      } else if (mapHeightInput.isSelected()) {
        mapHeightInput.removeCharacter();
      } else if (numPlayersInput.isSelected()) {
        numPlayersInput.removeCharacter();
      } else if (numAIInput.isSelected()) {
        numAIInput.removeCharacter();
      }
    } else {
      if (mapWidthInput.isSelected()) {
        mapWidthInput.addCharacter(key);
      } else if (mapHeightInput.isSelected()) {
        mapHeightInput.addCharacter(key);
      } else if (numPlayersInput.isSelected()) {
        numPlayersInput.addCharacter(key);
      } else if (numAIInput.isSelected()) {
        numAIInput.addCharacter(key);
      }
    }
  }

  /**
   * On start clicked.
   */
  private void onStartClicked() {
    int mapWidth = mapWidthInput.getIntValue();
    int mapHeight = mapHeightInput.getIntValue();
    int numPlayers = numPlayersInput.getIntValue();
    int numAI = numAIInput.getIntValue();
    //if (mapWidth >= 10 && mapWidth <= 100 && mapHeight >= 10 && mapHeight <= 100 && numPlayers >= 1 && numPlayers <= 100) {
    menu.scene.initGame(numPlayers, mapWidth, mapHeight, numAI);
    menu.setState(new MainMenuMenuState(menu));
    menu.scene.inGame = true;
    showError = false;
    //} else {
    showError = true;
    //}
  }

  /**
   * Draw hollow grid.
   *
   * @param rows the rows
   * @param cols the cols
   * @param x1   the x 1
   * @param y1   the y 1
   * @param x2   the x 2
   * @param y2   the y 2
   */
  void drawHollowGrid(int rows, int cols, float x1, float y1, float x2, float y2) {
    float cellWidth = (x2 - x1) / cols;
    float cellHeight = (y2 - y1) / rows;

    float strokeWidth = (float) (Math.min(cellWidth, cellHeight) / 10.0);
    menu.scene.strokeWeight(strokeWidth);
    menu.scene.stroke(0);
    menu.scene.noFill();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        float xPos = x1 + (j * cellWidth);
        float yPos = y1 + (i * cellHeight);
        menu.scene.rect(xPos, yPos, cellWidth, cellHeight);
      }
    }
  }


}

