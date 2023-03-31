package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PApplet;

public class NewGameMenuState extends MenuState {
    private InputBox mapWidthInput;
    private InputBox mapHeightInput;
    private InputBox numPlayersInput;

    private Label mapWidthLabel;

    private Label mapHeightLabel;

    private Label numPlayersLabel;

    private Label errorMessageLabel;

    private boolean showError = false;

    public NewGameMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }
    @Override
    public void setup() {
        mapWidthInput = new InputBox(50, 100, 200, 30, scene, 10, 10000, "16");
        mapHeightInput = new InputBox(50, 150, 200, 30, scene, 10, 10000, "16");
        numPlayersInput = new InputBox(50, 200, 200, 30, scene, 1, 10000, "2");

        mapWidthLabel = new Label(50, 95, "Map Width:", 14, scene);
        mapHeightLabel = new Label(50, 145, "Map Height:", 14, scene);
        numPlayersLabel = new Label(50, 195, "Number of Players:", 14, scene);
        errorMessageLabel = new Label(50, 250, "Invalid input! Please enter values within the specified range.", 14, scene);

        Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", scene);
        Button startButton = new Button(50, 600, 250, 650, () -> onStartClicked(), "start", scene);

        buttonManager.add(backButton);
        buttonManager.add(startButton);
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        menu.setState(new MainMenuMenuState(scene, menu));
    }

    @Override
    public void draw() {
        super.draw();
        mapWidthInput.draw();
        mapHeightInput.draw();
        numPlayersInput.draw();
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
        }
        return false;
    }


    public void keyPressed(char key) {
        if (key == PApplet.BACKSPACE) {
            if (mapWidthInput.isSelected()) {
                mapWidthInput.removeCharacter();
            } else if (mapHeightInput.isSelected()) {
                mapHeightInput.removeCharacter();
            } else if (numPlayersInput.isSelected()) {
                numPlayersInput.removeCharacter();
            }
        } else {
            if (mapWidthInput.isSelected()) {
                mapWidthInput.addCharacter(key);
            } else if (mapHeightInput.isSelected()) {
                mapHeightInput.addCharacter(key);
            } else if (numPlayersInput.isSelected()) {
                numPlayersInput.addCharacter(key);
            }
        }
    }

    // ...

    public void onStartClicked() {
        int mapWidth = mapWidthInput.getIntValue();
        int mapHeight = mapHeightInput.getIntValue();
        int numPlayers = numPlayersInput.getIntValue();
        //if (mapWidth >= 10 && mapWidth <= 100 && mapHeight >= 10 && mapHeight <= 100 && numPlayers >= 1 && numPlayers <= 100) {
            scene.initGame(numPlayers, mapWidth, mapHeight);
            menu.setState(new MainMenuMenuState(scene, menu));
            scene.inGame = true;
            showError = false;
        //} else {
            showError = true;
        //}
    }


}

