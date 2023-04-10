package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uiComponents.Button;
import org.bcit.com2522.project.scuffed.uiComponents.ButtonManager;
import org.bcit.com2522.project.scuffed.uiComponents.InputBox;
import org.bcit.com2522.project.scuffed.uiComponents.Label;
import processing.core.PApplet;

/**
 * The type Host game menu state.
 */
public class HostGameMenuState extends MenuState {
    private InputBox mapWidthInput;
    private InputBox mapHeightInput;
    private InputBox numPlayersInput;
    private InputBox portInput;
    private Label portInputLabel;
    private Label mapWidthLabel;
    private Label mapHeightLabel;
    private Label numPlayersLabel;
    private Label errorMessageLabel;
    private boolean showError = false;

  /**
   * Instantiates a new Host game menu state.
   *
   * @param scene the scene
   * @param menu  the menu
   */
  public HostGameMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }
    @Override
    public void setup() {
        portInput = new InputBox(50, 50, 200, 30, scene, 1, 60000, "8080");
        mapWidthInput = new InputBox(50, 100, 200, 30, scene, 10, 100, "16");
        mapHeightInput = new InputBox(50, 150, 200, 30, scene, 10, 100, "16");
        numPlayersInput = new InputBox(50, 200, 200, 30, scene, 1, 10, "2");

        portInputLabel = new Label(50, 45, "Port:", 14, scene);
        mapWidthLabel = new Label(50, 95, "Map Width:", 14, scene);
        mapHeightLabel = new Label(50, 145, "Map Height:", 14, scene);
        numPlayersLabel = new Label(50, 195, "Number of Players:", 14, scene);
        errorMessageLabel = new Label(50, 250, "Invalid input! Please enter values within the specified range.", 14, scene);

        Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", scene);
        Button startButton = new Button(50, 600, 250, 650, () -> onStartServerClicked(), "Start Server", scene);

        buttonManager.add(backButton);
        buttonManager.add(startButton);

    }

    @Override
    public void draw() {
        super.draw();
        portInput.draw();
        mapWidthInput.draw();
        mapHeightInput.draw();
        numPlayersInput.draw();
        portInputLabel.draw();
        mapWidthLabel.draw();
        mapHeightLabel.draw();
        numPlayersLabel.draw();

        if(showError) {
            errorMessageLabel.draw();
        }
    }

    @Override
    public boolean clicked(int xpos, int ypos) {
        // Check if any buttons or inputs were clicked and perform actions
        if(super.clicked(xpos, ypos)) {
            return true;
        }
        if(mapWidthInput.isClicked(xpos, ypos)){
            mapWidthInput.setSelected(true);
            mapHeightInput.setSelected(false);
            numPlayersInput.setSelected(false);
            return true;
        }else if(mapHeightInput.isClicked(xpos, ypos)){
            mapWidthInput.setSelected(false);
            mapHeightInput.setSelected(true);
            numPlayersInput.setSelected(false);
            return true;
        }else if(numPlayersInput.isClicked(xpos, ypos)){
            mapWidthInput.setSelected(false);
            mapHeightInput.setSelected(false);
            numPlayersInput.setSelected(true);
            return true;
        } else if( portInput.isClicked(xpos, ypos) ) {
            portInput.setSelected(true);
            mapWidthInput.setSelected(false);
            mapHeightInput.setSelected(false);
            numPlayersInput.setSelected(false);
            return true;
        }
        return false;
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
            } else if( portInput.isSelected() ) {
                portInput.removeCharacter();
            }
        } else {
            if (mapWidthInput.isSelected()) {
                mapWidthInput.addCharacter(key);
            } else if (mapHeightInput.isSelected()) {
                mapHeightInput.addCharacter(key);
            } else if (numPlayersInput.isSelected()) {
                numPlayersInput.addCharacter(key);
            } else if( portInput.isSelected() ) {
                portInput.addCharacter(key);
            }

        }
    }

  /**
   * On start server clicked.
   */
// ...
    public void onStartServerClicked() {
        int port = portInput.getIntValue();
        int mapWidth = mapWidthInput.getIntValue();
        int mapHeight = mapHeightInput.getIntValue();
        int numPlayers = numPlayersInput.getIntValue();
        if (mapWidth >= 10 && mapWidth <= 100 && mapHeight >= 10 && mapHeight <= 100 && numPlayers >= 1 && numPlayers <= 10) {
            menu.scene.initOnlineGame(numPlayers, mapWidth, mapHeight, port);
            menu.setState(new ServerLobbyMenuState(scene, menu));

            showError = false;
        } else {
            showError = true;
        }
    }

    @Override
    public void onBackClicked() {
        // Change the menu state to the New Game state
        menu.setState(new OnlineMenuState(scene, menu));
    }


}
