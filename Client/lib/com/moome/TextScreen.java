package com.moome;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class TextScreen extends Screen {
    BufferedImage text;
    
    public TextScreen(String s) {
        text = Util.drawText(s, Util.FONT_WHITE);
    }
    
    public void draw(Graphics g, int animation) {
        g.drawImage(text, Util.center(text.getWidth(), MoomePane.WIDTH * 20), Util.center(text.getHeight(), MoomePane.HEIGHT * 20), null);
    }
    
    public void keyPressed(int i) {}
    
    public void keyReleased(int i) {}
}
