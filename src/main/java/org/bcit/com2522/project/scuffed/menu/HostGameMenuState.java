package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uiComponents.Button;
import org.bcit.com2522.project.scuffed.uiComponents.InputBox;
import org.bcit.com2522.project.scuffed.uiComponents.Label;
import processing.core.PApplet;

/**
 * The HostGameMenuState class, which extends the NewGameMenuState class, represents
 * the menu state for hosting an online game.
 */
public class HostGameMenuState extends NewGameMenuState {
  private final InputBox portInput;
  private final Label portInputLabel;
  private final Label errorMessageLabel;

  /**
   * Constructs a new HostGameMenuState with the specified menu.
   *
   * @param menu the menu associated with this menu state
   */
  public HostGameMenuState(Menu menu) {
    super(menu);

    portInput = new InputBox(50, 50, 200, 30, 1, 60000, "8080");
    portInputLabel = new Label(50, 45, "Port:", 14);
    errorMessageLabel = new Label(50, 250,
        "Invalid input! Please enter values within the specified range.", 14);

    setupHostMenu();
  }

  /**
   * Sets up the host game menu by adding the back button and start server button.
   */
  private void setupHostMenu() {
    buttonManager.wipe();
    Button backButton = new Button(50, 500, 250, 550,
        this::onBackClicked, "back", menu.scene);
    Button startServerButton = new Button(50, 600, 250, 650,
        this::onStartServerClicked, "Start Server", menu.scene);

    buttonManager.add(backButton);
    buttonManager.add(startServerButton);
  }

  /**
   * Draws the host game menu state on the given scene.
   *
   * @param scene the scene to draw on
   */
  @Override
  public void draw(Window scene) {
    super.draw(scene);
    portInput.draw(scene);
    portInputLabel.draw(scene);

    if (showError) {
      errorMessageLabel.draw(scene);
    }
  }

  /**
   * Handles mouse clicks on this menu state.
   *
   * @param xpos the x position of the mouse click
   * @param ypos the y position of the mouse click
   * @return true if a component is clicked, false otherwise
   */
  @Override
  public boolean clicked(int xpos, int ypos) {
    if (super.clicked(xpos, ypos)) {
      return true;
    }
    if (portInput.isClicked(xpos, ypos)) {
      setSelectedInput(portInput);
      return true;
    }
    return false;
  }

  /**
   * Handles key presses for the input boxes.
   *
   * @param key the key pressed
   */
  @Override
  public void keyPressed(char key) {
    super.keyPressed(key);
    if (key == PApplet.BACKSPACE) {
      if (portInput.isSelected()) {
        portInput.removeCharacter();
      }
    } else {
      if (portInput.isSelected()) {
        portInput.addCharacter(key);
      }
    }
  }

  /**
   * Handles the start server button click and initiates the online game.
   */
  public void onStartServerClicked() {
    System.out.println("Starting server...");
    int port = portInput.getIntValue();
    int mapWidth = mapWidthInput.getIntValue();
    int mapHeight = mapHeightInput.getIntValue();
    int numPlayers = numPlayersInput.getIntValue();
    if (mapWidth >= 10 && mapWidth <= 100 && mapHeight >= 10 && mapHeight <= 100
        && numPlayers >= 1 && numPlayers <= 10) {
      menu.scene.initOnlineGame(numPlayers, mapWidth, mapHeight, port);
      menu.setState(new ServerLobbyMenuState(menu));
      showError = false;
    } else {
      showError = true;
    }
  }
}