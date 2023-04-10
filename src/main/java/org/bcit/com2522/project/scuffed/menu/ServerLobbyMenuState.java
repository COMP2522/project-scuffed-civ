package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uiComponents.Button;
import org.bcit.com2522.project.scuffed.uiComponents.ButtonManager;
import org.bcit.com2522.project.scuffed.uiComponents.Label;

/**
 * The server lobby. This is where the host can see the players connected and start the game.
 * --> as of this version only the host is shown the lobby
 * --> The game currently starts as soon as all players connect, so the start button is not used
 *
 * @author Cameron Walford
 * @version 1.0
 */
public class ServerLobbyMenuState extends MenuState {
  private Label titleLabel;
  private Label playersLabel;
  private Button backButton;
  private Button startButton;

  /**
   * Instantiates a new Server lobby menu state.
   *
   * @param menu the menu
   */
  public ServerLobbyMenuState(Menu menu) {
    super(menu, new ButtonManager(menu.scene));
    setup();
  }

  @Override
  public void setup() {
    titleLabel = new Label(50, 50, "Server Lobby", 20);
    playersLabel = new Label(50, 100, "Players Connected: ", 16);

    backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", menu.scene);
    startButton = new Button(50, 600, 250, 650, () -> onStartClicked(), "start", menu.scene);
    startButton.setClickable(false);

    buttonManager.add(backButton);
    buttonManager.add(startButton);
  }

  @Override
  public void draw(Window scene) {
    super.draw(scene);
    titleLabel.draw(scene);
    playersLabel.setText("Players Currently Connected: " + scene.getConnectedPlayers().size());
    playersLabel.draw(scene);
  }

  public void onBackClicked() {
    menu.setState(new HostGameMenuState(menu));
  }

  /**
   * On start clicked.
   */
  public void onStartClicked() {
    // Start the game
    menu.scene.inGame = true;
  }
}
