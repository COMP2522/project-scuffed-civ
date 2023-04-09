package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PApplet;

/**
 * The type Join game menu state.
 */
public class JoinGameMenuState extends MenuState {

    private InputBox portInput;
    private InputBox hostIPInput;
    private InputBox usernameInput;
    private Label hostIPInputLabel;
    private Label portInputLabel;
    private Label usernameInputLabel;
    private Label errorMessageLabel;
    private boolean showError = false;

  /**
   * Instantiates a new Join game menu state.
   *
   * @param scene the scene
   * @param menu  the menu
   */
  public JoinGameMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }

    @Override
    public void setup() {
        // Create buttons
        Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", scene);
        Button joinButton = new Button(50, 600, 250, 650, () -> onJoinClicked(), "Join", scene);
        // Create input box for host IP, port, and username
        portInput = new InputBox(50, 50, 200, 30, scene, 1, 60000, "8080");
        hostIPInput = new InputBox(50, 100, 250, 30, scene, "", "string");
        usernameInput = new InputBox(50, 150, 250, 30, scene, "", "string");

        // Add labels for the input boxes
        portInputLabel = new Label(50, 45, "Port:", 14, scene);
        hostIPInputLabel = new Label(50, 95, "Host IP:", 14, scene);
        usernameInputLabel = new Label(50, 145, "Username:", 14, scene);
        errorMessageLabel = new Label(50, 250, "Invalid input!", 14, scene);

        // Add the buttons to the button manager
        buttonManager.add(backButton);
        buttonManager.add(joinButton);
    }


    @Override
    public void draw() {
        super.draw();
        portInput.draw();
        portInputLabel.draw();
        hostIPInput.draw();
        hostIPInputLabel.draw();
        usernameInput.draw();
        usernameInputLabel.draw();

        if (showError) {
            errorMessageLabel.draw();
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
            usernameInput.setSelected(false);
            return true;
        } else if (portInput.isClicked(xpos, ypos)) {
            portInput.setSelected(true);
            hostIPInput.setSelected(false);
            usernameInput.setSelected(false);
            return true;
        } else if (usernameInput.isClicked(xpos, ypos)) {
            usernameInput.setSelected(true);
            hostIPInput.setSelected(false);
            portInput.setSelected(false);
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
            if (hostIPInput.isSelected()) {
                hostIPInput.removeCharacter();
            } else if (portInput.isSelected()) {
                portInput.removeCharacter();
            } else if (usernameInput.isSelected()) {
                usernameInput.removeCharacter();
            }
        } else {
            if (hostIPInput.isSelected()) {
                hostIPInput.addCharacter(key);
            } else if (portInput.isSelected()) {
                portInput.addCharacter(key);
            } else if (usernameInput.isSelected()) {
                usernameInput.addCharacter(key);
            }
        }
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        menu.setState(new OnlineMenuState(scene, menu));
    }

  /**
   * On join clicked.
   */
  public void onJoinClicked() {
        // Get the port, host IP, and username from the input boxes
        int port = portInput.getIntValue();
        String hostIP = hostIPInput.getStringValue();
        String username = usernameInput.getStringValue();
        // Check if the port, host IP, and username are valid
        if (port >= 1 && port <= 60000 && hostIP != null && username != null && !username.equals("")) {
            // Join the game
            scene.joinGame(hostIP, port, username);
            showError = false;
        } else {
            showError = true;
        }
    }
}
