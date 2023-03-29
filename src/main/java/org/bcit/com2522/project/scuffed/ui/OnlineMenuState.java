package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class OnlineMenuState extends MenuState {

    public OnlineMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }
    @Override
    public void setup() {
        Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", scene);
        Button hostButton = new Button(50, 200, 250, 250, () -> onHostClicked(), "Host Game", scene);
        Button joinButton = new Button(50, 300, 250, 350, () -> onJoinClicked(), "Join Game", scene);

        buttonManager.add(backButton);
        buttonManager.add(hostButton);
        buttonManager.add(joinButton);
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        menu.setState(new MainMenuMenuState(scene, menu));
    }

    public void onHostClicked() {
        // Change the menu state to the New Game state
        menu.setState(new HostGameMenuState(scene, menu));
    }

    public void onJoinClicked() {
        // Change the menu state to the New Game state
        menu.setState(new JoinGameMenuState(scene, menu));
    }
}
