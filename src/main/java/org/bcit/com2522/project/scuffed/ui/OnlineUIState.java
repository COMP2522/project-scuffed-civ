package org.bcit.com2522.project.scuffed.ui;

public class OnlineUIState extends UIState {

    public OnlineUIState(UI UI) {
        super(UI);
        setup();
    }
    @Override
    public void setup() {
        Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back");
        Button hostButton = new Button(50, 200, 250, 250, () -> onHostClicked(), "Host Game");
        Button joinButton = new Button(50, 300, 250, 350, () -> onJoinClicked(), "Join Game");

        clickableManager.add(backButton);
        clickableManager.add(hostButton);
        clickableManager.add(joinButton);
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        UI.setState(new MainMenuUIState(UI));
    }

    public void onHostClicked() {
        // Change the menu state to the New Game state
        UI.setState(new HostGameUIState(UI));
    }

    public void onJoinClicked() {
        // Change the menu state to the New Game state
        UI.setState(new JoinGameUIState(UI));
    }
}
