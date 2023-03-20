package org.bcit.com2522.project.scuffed.ui;

public interface UIState {
    public void draw();
    public boolean clicked(int xpos, int ypos);
    public void setState(UIState newState);
}
