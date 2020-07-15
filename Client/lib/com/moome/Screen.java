package com.moome;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public abstract class Screen {
    public Color background = new Color(0, 205, 240);
    
    public abstract void draw(Graphics g, int animation);
    public abstract void keyPressed(int i);
    public abstract void keyReleased(int i);
    public void keyPressed(char i) {}
    public void keyReleased(char i) {}
    
    public int getMusic() {
        return -1;
    }
}
