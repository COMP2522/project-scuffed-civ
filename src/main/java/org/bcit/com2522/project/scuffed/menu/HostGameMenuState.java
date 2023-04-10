package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uicomponents.Button;
import org.bcit.com2522.project.scuffed.uicomponents.InputBox;
import org.bcit.com2522.project.scuffed.uicomponents.Label;
import processing.core.PApplet;

public class HostGameMenuState extends NewGameMenuState {
  private final InputBox portInput;
  private final Label portInputLabel;
  private final Label errorMessageLabel;

  public HostGameMenuState(Menu menu) {
    super(menu);

    portInput = new InputBox(50, 50, 200, 30, 1, 60000, "8080");
    portInputLabel = new Label(50, 45, "Port:", 14);
    errorMessageLabel = new Label(50, 250,
            "Invalid input! Please enter values within the specified range.", 14);

    setupHostMenu();
  }

  private void setupHostMenu() {
    buttonManager.wipe();
    Button backButton = new Button(50, 500, 250, 550,
            this::onBackClicked, "back", menu.scene);
    Button startServerButton = new Button(50, 600, 250, 650,
            this::onStartServerClicked, "Start Server", menu.scene);

    buttonManager.add(backButton);
    buttonManager.add(startServerButton);
  }

  @Override
  public void draw(Window scene) {
    super.draw(scene);
    portInput.draw(scene);
    portInputLabel.draw(scene);

    if (showError) {
      errorMessageLabel.draw(scene);
    }
  }

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