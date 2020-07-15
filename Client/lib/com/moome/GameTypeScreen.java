package com.moome;

import java.awt.image.BufferedImage;

public class GameTypeScreen extends MenuScreen {
    public GameTypeScreen() {
        this.elements.add(new Button("Adventure", MoomePane.HEIGHT * 10 - 12, 90));
        this.elements.add(new Button("Arcade", MoomePane.HEIGHT * 10 + 1, 90));
        this.elements.add(new Button("Back", MoomePane.HEIGHT * 10 + 14, 90));
    }
    
    public void buttonPress() {
        switch(selected) {
            case 0:
                Util.adventureMode = true;
                ScreenManager.setScreen(6);
            break;
            case 1:
                Util.adventureMode = false;
                ScreenManager.setScreen(6);
            break;
            case 2:
                ScreenManager.setScreen(1);
            break;
            default: break;
        }
    }
}
