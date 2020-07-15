package com.moome;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PastBetaScreen extends Screen {
    BufferedImage text = Util.drawMultilinedText("No more beta testing!\n\nThe release will be out soon", true, Util.FONT_WHITE);
    
    public void draw(Graphics g, int animation) {
        g.drawImage(text, Util.center(text.getWidth(), MoomePane.WIDTH * 20), Util.center(text.getHeight(), MoomePane.HEIGHT * 20), null);
    }
    
    public void keyPressed(int i) {}
    public void keyReleased(int i) {}
}
