package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

/**
 * The type Loading menu state.
 */
public class LoadingMenuState extends MenuState {

    private String loadingText;

  /**
   * Instantiates a new Loading menu state.
   *
   * @param scene the scene
   * @param menu  the menu
   */
  public LoadingMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        loadingText = "Loading...";
    }

    @Override
    public void setup() {
    }


    @Override
    public void draw() {
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