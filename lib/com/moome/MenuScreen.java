package com.moome;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class MenuScreen extends Screen {
    public int selected = 0;
    public ArrayList<Object> elements = new ArrayList<>();
    
    public void draw(Graphics g, int animation) {
        drawElements(g);
    }
    
    public final void drawElements(Graphics g) {
        for(int i = 0; i < elements.size(); i++) {
            if(elements.get(i) instanceof Button) {
                Button b = (Button)elements.get(i);
                if(selected == i) {
                    g.drawImage(b.imageHighlight, b.x, b.y, null);
                } else {
                    g.drawImage(b.image, b.x, b.y, null);
                }
            } else if(elements.get(i) instanceof TextBox) {
                ((TextBox)elements.get(i)).draw(g, selected == i);
            }
        }
    }
    
    public void keyPressed(int i) {
        switch(i) {
            case KeyEvent.VK_UP:
                selected = ((selected - 1) % elements.size() + elements.size()) % elements.size();
            break;
            case KeyEvent.VK_DOWN:
                selected = (selected + 1) % elements.size();
            break;
            case KeyEvent.VK_ENTER:
                if(elements.get(selected) instanceof Button) {
                    buttonPress();
                }
            break;
        }
    }
    
    public void keyReleased(int i) {}
    public abstract void buttonPress();
    
    public void keyPressed(char i) {
        if(elements.get(selected) instanceof TextBox) {
            //((TextBox)elements.get(a)).text += i;
        }
    }

    public int getMusic() {
        return 1;
        
    }
}
