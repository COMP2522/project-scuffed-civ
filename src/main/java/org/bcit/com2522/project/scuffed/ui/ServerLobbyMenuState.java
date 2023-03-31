package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class ServerLobbyMenuState extends MenuState {
    private Label titleLabel;
    private Label playersLabel;
    private Button backButton;
    private Button startButton;

    public ServerLobbyMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }

    @Override
    public void setup() {
        titleLabel = new Label(50, 50, "Server Lobby", 20, scene);
        playersLabel = new Label(50, 100, "Players Connected: ", 16, scene);

        backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", scene);
        startButton = new Button(50, 600, 250, 650, () -> onStartClicked(), "start", scene);

        buttonManager.add(backButton);
        buttonManager.add(startButton);
    }

    @Override
    public void draw() {
        super.draw();
        titleLabel.draw();
        playersLabel.setText("Players Connected: " + scene.getConnectedPlayers().size());
        playersLabel.draw();
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        menu.setState(new HostGameMenuState(scene, menu));
    }

    public void onStartClicked() {
        // Start the game
        scene.inGame = true;
    }
}
