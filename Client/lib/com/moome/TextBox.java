package com.moome;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class TextBox {
    private String text;
    public int x;
    public int y;
    public int w;
    public int maxCharacters;
    
    public TextBox(int b, int c) {
        this.w = c;
        this.x = Util.center(getWidth(), MoomePane.WIDTH * 20);
        this.text = "";
        this.y = b;
    }
    
    public void draw(Graphics g, boolean bright) {
        BufferedImage b = Util.drawText(text, Util.FONT_BLACK);
        if(bright) {
            g.setColor(Color.white);
        } else {
            g.setColor(new Color(240, 240, 240));
        }
        g.fillRect(x, y, getWidth(), getHeight());
        g.setColor(Color.black);
        g.drawRect(x, y, getWidth(), getHeight());
        g.drawImage(b, x - 5, y + 2, null);
    }
    
    public int getWidth() {
        return w == 0 ? 2 + maxCharacters * 7 : w;
    }
    
    public int getHeight() {
        return 11;
    }
    
    public void setText(String s) {
        text = s;
    }
    
    public String getText() {
        return text;
    }
}
