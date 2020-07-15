package com.moome;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class IntroScreen extends Screen {
    final BufferedImage text = Util.drawMultilinedText("Copyright 2015\nBlackberryCode\nAll Rights\nReserved\nPress any key", true, 4, Util.FONT_WHITE);
    
    public IntroScreen() {}
    
    public void draw(Graphics g, int animation) {
        g.drawImage(text, Util.center(text.getWidth(), MoomePane.WIDTH * 20), Util.center(text.getHeight(), MoomePane.HEIGHT * 20), null);
        if(animation > 80) {
            ScreenManager.setScreen(1);
        }
    }
    
    public void keyPressed(int i) {
        ScreenManager.setScreen(1);
    }
    
    public void keyReleased(int i) {}
}
