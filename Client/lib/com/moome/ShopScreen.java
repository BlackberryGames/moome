package com.moome;

import java.awt.Graphics;

public class ShopScreen extends MenuScreen {
    private GameScreen gameScreen;
    
    public ShopScreen(GameScreen gs) {
        gameScreen = gs;
        this.elements.add(new Button("Double jumping: 50c", Util.center(11, MoomePane.HEIGHT * 20) - 7, 90));
        this.elements.add(new Button("Back", Util.center(11, MoomePane.HEIGHT * 20) + 7, 90));
    }
    
    public void buttonPress() {
        switch(selected) {
            case 1:
                ScreenManager.setScreen(2);
            break;
            default: break;
        }
    }
    
    public void draw(Graphics g, int animation) {
        gameScreen.draw(g, 0);
        super.draw(g, animation);
    }
}
