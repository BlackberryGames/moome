package com.moome;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;

public class CreditScreen extends MenuScreen {
    BufferedImage b = Util.getImage("../data/images/misc/blackberry.png");
    BufferedImage text = Util.drawMultilinedText("<a>Programmers\nmooncat39\narinerron", true, Util.FONT_WHITE);
    public int y = 20;
    boolean up = false;
    boolean down = false;
    
    public void draw(Graphics g, int animation) {
        g.drawImage(b, Util.center(b.getWidth(), MoomePane.WIDTH * 20), y, null);
        g.drawImage(text, Util.center(text.getWidth(), MoomePane.WIDTH * 20), y + b.getHeight() + 10, null);
        
        super.draw(g, animation);
        
        if(up != down) {
            if(up) {
                y--;
            } else {
                y++;
            }
        }
    }
    
    public CreditScreen() {
        this.elements.add(new Button("Exit", MoomePane.HEIGHT * 20 - 22, 90));
    }
    
    public void buttonPress() {
        switch(selected) {
            case 0:
                ScreenManager.setScreen(1);
            break;
            default: break;
        }
    }
    
    public void keyPressed(int i) {
        if(i == KeyEvent.VK_UP) {
            up = true;
        }
        if(i == KeyEvent.VK_DOWN) {
            down = true;
        }
        super.keyPressed(i);
    }
    
    public void keyReleased(int i) {
        if(i == KeyEvent.VK_UP) {
            up = false;
        }
        if(i == KeyEvent.VK_DOWN) {
            down = false;
        }
        super.keyReleased(i);
    }
}
