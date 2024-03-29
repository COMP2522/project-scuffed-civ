package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uicomponents.ButtonManager;

/**
 * The Loading menu state. This is a menu state that is used to display a loading screen.
 */
public class LoadingMenuState extends MenuState {

  private final String loadingText;

  /**
   * Instantiates a new Loading menu state.
   *
   * @param menu the menu
   */
  public LoadingMenuState(Menu menu) {
    super(menu, new ButtonManager(menu.scene));
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