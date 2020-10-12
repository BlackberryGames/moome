package com.moome;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Button {
    public int x;
    public int y;
    public String text;
    public BufferedImage image;
    public BufferedImage imageHighlight;
    
    public Button(String a, int b, int forcedWidth) {
        int w;
        if(forcedWidth == 0) {
            w = 2 + a.length() * 7;
        } else {
            w = forcedWidth;
        }
        this.text = a;
        this.x = Util.center(w, MoomePane.WIDTH * 20);
        this.y = b;
        this.image = new BufferedImage(w, 11, BufferedImage.TYPE_INT_ARGB);
        drawImage(a, false, w);
        this.imageHighlight = new BufferedImage(w, 11, BufferedImage.TYPE_INT_ARGB);
        drawImage(a, true, w);
    }
    
    public Button(String a, int b, int forcedWidth, Color c, int i) {
        int w;
        if(forcedWidth == 0) {
            w = 2 + a.length() * 7;
        } else {
            w = forcedWidth;
        }
        this.text = a;
        this.x = Util.center(w, MoomePane.WIDTH * 20);
        this.y = b;
        this.image = new BufferedImage(w, 11, BufferedImage.TYPE_INT_ARGB);
        drawImage(a, false, w, c, i);
        this.imageHighlight = new BufferedImage(w, 11, BufferedImage.TYPE_INT_ARGB);
        drawImage(a, true, w, c, i);
    }
    
    public void drawImage(String text, boolean h, int fw) {
        int w = 2 + text.length() * 7;
        Graphics g;
        if(h) {
            g = this.imageHighlight.createGraphics();
            g.setColor(new Color(192, 192, 192));
        } else {
            g = this.image.createGraphics();
            g.setColor(Color.gray);
        }
        g.fillRect(0, 0, w <= fw ? fw + fw / 2 - w / 2 : fw, 11);
        Util.drawText(g, 2 + (w <= fw ? fw / 2 - w / 2 : 0), 2, text, Util.FONT_WHITE);
        g.dispose();
    }
    
    public void drawImage(String text, boolean h, int fw, Color c, int i) {
        int w = 2 + text.length() * 7;
        Graphics g;
        if(h) {
            g = this.imageHighlight.createGraphics();
            g.setColor(c.brighter());
        } else {
            g = this.image.createGraphics();
            g.setColor(c);
        }
        g.fillRect(0, 0, w <= fw ? fw + fw / 2 - w / 2 : fw, 11);
        Util.drawText(g, 2 + (w <= fw ? fw / 2 - w / 2 : 0), 2, text, i);
        g.dispose();
    }
}
