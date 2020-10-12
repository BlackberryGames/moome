package com.moome;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;

public class CutsceneScreen extends Screen {
    BufferedImage b;
    int startAnimation = 0;
    
    public CutsceneScreen(String text) {
        b = Util.drawMultilinedText(text, false, Util.FONT_WHITE);
    }
    
    public void draw(Graphics g, int animation) {
        ScreenManager.getScreen(2).draw(g, 0);
        g.drawImage(b, Util.center(b.getWidth(), MoomePane.WIDTH * 20), Util.center(b.getHeight(), MoomePane.HEIGHT * 20), null);
        int a = MoomePane.animation - startAnimation;
        int fade = 0;
        if(a < 100) {
            fade = (74 - a) * 4;
            System.out.println(a);
        } else {
            fade = (a - 100) * 4;
        }
        if(a == 164) {
            ScreenManager.resetOverride();
        }
        g.setColor(new Color(background.getRed(), background.getGreen(), background.getBlue(), Math.min(Math.max(fade, 0), 255)));
        g.fillRect(0, 0, MoomePane.WIDTH * 20, MoomePane.HEIGHT * 20);
    }
    
    public void keyPressed(int i) {}
    
    public void keyReleased(int i) {}
    
    public void show() {
        System.out.println("SET: " + MoomePane.animation);
        this.startAnimation = MoomePane.animation;
    }
}
