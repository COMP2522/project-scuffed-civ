package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uiComponents.Button;
import org.bcit.com2522.project.scuffed.uiComponents.ButtonManager;
import org.bcit.com2522.project.scuffed.uiComponents.InputBox;
import org.bcit.com2522.project.scuffed.uiComponents.Label;
import processing.core.PApplet;

/**
 * The Join game menu state. This is the menu that the user sees when they want to join a game.
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
   * @param menu the menu
   */
  public JoinGameMenuState(Menu menu) {
    super(menu, new ButtonManager(menu.scene));
    setup();
  }

  @Override
  public void setup() {
    // Create buttons
    Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", menu.scene);
    Button joinButton = new Button(50, 600, 250, 650, () -> onJoinClicked(), "Join", menu.scene);
    // Create input box for host IP, port, and username
    portInput = new InputBox(50, 50, 200, 30, 1, 60000, "8080");
    hostIPInput = new InputBox(50, 100, 250, 30, "", "string");
    usernameInput = new InputBox(50, 150, 250, 30, "", "string");

    // Add labels for the input boxes
    portInputLabel = new Label(50, 45, "Port:", 14);
    hostIPInputLabel = new Label(50, 95, "Host IP:", 14);
    usernameInputLabel = new Label(50, 145, "Username:", 14);
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
    usernameInput.draw(scene);
    usernameInputLabel.draw(scene);

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
   * When a key is pressed, add it to the input box if it is selected.
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

  /**
   * When back button is click set the menustate back to the previous.
   */
  public void onBackClicked() {
    // Change the menu state to the New Game state
    menu.setState(new OnlineMenuState(menu));
  }

  /**
   * Attempts to join the game if the input is valid.
   */
  public void onJoinClicked() {
    // Get the port, host IP, and username from the input boxes
    int port = portInput.getIntValue();
    String hostIP = hostIPInput.getStringValue();
    String username = usernameInput.getStringValue();
    // Check if the port, host IP, and username are valid
    if (port >= 1 && port <= 60000 && hostIP != null && username != null && !username.equals("")) {

      menu.scene.joinGame(hostIP, port, username);
      showError = false;

    } else {
      showError = true;
    }
  }
}
