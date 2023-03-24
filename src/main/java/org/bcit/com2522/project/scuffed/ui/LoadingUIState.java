package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class LoadingUIState extends UIState {

    private String loadingText;

    public LoadingUIState(UI UI) {
        super(UI);
        loadingText = "Loading...";
    }

    @Override
    public void setup() {
    }


    @Override
    public void draw(Window scene) {
        scene.background(200);
        scene.fill(0);
        scene.textSize(24);
        scene.textAlign(scene.CENTER, scene.CENTER);
        scene.text(loadingText, scene.width / 2, scene.height / 2);
    }

    @Override
    public void onBackClicked() {

    }
}