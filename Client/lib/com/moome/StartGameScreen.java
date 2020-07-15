package com.moome;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class StartGameScreen extends Screen {
    BufferedImage a;
    BufferedImage b;
    
    public StartGameScreen(GameScreen gs) {
        a = Util.drawText(gs.preferences.get("name"), Util.FONT_WHITE);
        b = Util.drawText("By " + gs.preferences.get("author"), Util.FONT_WHITE);
    }
    
    public void draw(Graphics g, int animation) {
        g.drawImage(a, Util.center(a.getWidth(), MoomePane.WIDTH * 20), Util.center(a.getHeight(), MoomePane.HEIGHT * 20) - 7, null);
        g.drawImage(b, Util.center(b.getWidth(), MoomePane.WIDTH * 20), Util.center(b.getHeight(), MoomePane.HEIGHT * 20) + 7, null);
    }
    
    public void keyPressed(int i) {
        ScreenManager.setScreen(2);
    }
    
    public void keyReleased(int i) {}
}
