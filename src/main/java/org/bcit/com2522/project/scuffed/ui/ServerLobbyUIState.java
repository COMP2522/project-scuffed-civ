package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class ServerLobbyUIState extends UIState {

    public ServerLobbyUIState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
    }

    @Override
    public void setup() {
        //TODO: show list of connected players' clientIds
        //TODO: indicate which player is the host
        //TODO: Add button to ready up
        //TODO: Add button to start game that's only visible to the host when everyone is ready
        //TODO: Add button to leave game
    }

    @Override
    public void onBackClicked() {

    }
}
