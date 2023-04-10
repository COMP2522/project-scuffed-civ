package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uiComponents.Button;
import org.bcit.com2522.project.scuffed.uiComponents.ButtonManager;

/**
 * The Online menu state where a player chooses to host or join a game.
 */
public class OnlineMenuState extends MenuState {

  /**
   * Instantiates a new Online menu state.
   *
   * @param menu  the menu
   */
  public OnlineMenuState( Menu menu) {
        super( menu, new ButtonManager(menu.scene));
        setup();
    }
    @Override
    public void setup() {
        Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", menu.scene);
        Button hostButton = new Button(50, 200, 250, 250, () -> onHostClicked(), "Host Game", menu.scene);
        Button joinButton = new Button(50, 300, 250, 350, () -> onJoinClicked(), "Join Game", menu.scene);

        buttonManager.add(backButton);
        buttonManager.add(hostButton);
        buttonManager.add(joinButton);
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        menu.setState(new MainMenuMenuState( menu));
    }

  /**
   * On host clicked.
   */
  public void onHostClicked() {
        // Change the menu state to the New Game state
        menu.setState(new HostGameMenuState( menu));
    }

  /**
   * On join clicked.
   */
  public void onJoinClicked() {
        // Change the menu state to the New Game state
        menu.setState(new JoinGameMenuState( menu));
    }
}
