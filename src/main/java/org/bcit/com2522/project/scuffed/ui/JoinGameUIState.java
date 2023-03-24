package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PApplet;

public class JoinGameUIState extends UIState {

    private InputBox portInput;
    private InputBox hostIPInput;
    private Label hostIPInputLabel;
    private Label portInputLabel;
    private Label errorMessageLabel;
    private boolean showError = false;

    public JoinGameUIState(UI UI) {
        super(UI);
        setup();
    }

    @Override
    public void setup() {
        // Create buttons
        Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back");
        Button joinButton = new Button(50, 600, 250, 650, () -> onJoinClicked(), "Join");
        // Create input box for host IP and port
        portInput = new InputBox(50, 50, 200, 30, 1, 60000, "8080");
        hostIPInput = new InputBox(50, 100, 250, 30, "", "string");

        // Add labels for the input boxes
        portInputLabel = new Label(50, 45, "Port:", 14);
        hostIPInputLabel = new Label(50, 95, "Host IP:", 14);
        errorMessageLabel = new Label(50, 250, "Invalid input!", 14);

        // Add the buttons to the button manager
        buttonManager.add(backButton);
        buttonManager.add(joinButton);
    }


    @Override
    public void draw(Window scene) {
        super.draw(scene);
        portInput.draw(scene);
        portInputLabel.draw(scene);
        hostIPInput.draw(scene);
        hostIPInputLabel.draw(scene);

        if (showError) {
            errorMessageLabel.draw(scene);
        }
    }

    @Override
    public boolean clicked(int xpos, int ypos) {
        // Check if any buttons or inputs were clicked and perform actions
        if (super.clicked(xpos, ypos)) {
            return true;
        }
       if (hostIPInput.isClicked(xpos, ypos)) {
            hostIPInput.setSelected(true);
            portInput.setSelected(false);
            return true;
        } else if (portInput.isClicked(xpos, ypos)) {
            portInput.setSelected(true);
            hostIPInput.setSelected(false);
            return true;
        }
        return false;
    }

    public void keyPressed(char key) {
        if (key == PApplet.BACKSPACE) {
            if (hostIPInput.isSelected()) {
                hostIPInput.removeCharacter();
            } else if (portInput.isSelected()) {
                portInput.removeCharacter();
            }
        } else {
            if (hostIPInput.isSelected()) {
                hostIPInput.addCharacter(key);
            } else if (portInput.isSelected()) {
                portInput.addCharacter(key);
            }
        }
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        UI.setState(new OnlineUIState(UI));
    }

    public void onJoinClicked() {
        // Get the port and host IP from the input boxes
        int port = portInput.getIntValue();
        String hostIP = hostIPInput.getStringValue();
        // Check if the port and host IP are valid
        if (port >= 1 && port <= 60000 && hostIP != null) {
            // Join the game
            UI.scene.joinGame(hostIP, port);
            showError = false;
        } else {
            showError = true;
        }
    }
}
