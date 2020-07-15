package com.moome.leveleditor;

import com.moome.TileSettings;
import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

public class Selector extends JPanel implements MouseListener, MouseMotionListener {
    int mousePos = 0;
    LevelEditor levelEditor;
    
    public Selector(LevelEditor le) {
        levelEditor = le;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(le.tiles * 50, 50));
    }
    
    public void paintComponent(Graphics g) {
        int a = 128 + (int)Math.abs(Math.round(Math.sin(Math.toRadians(levelEditor.i * 5)) * 40));
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        int j = 0;
        for(int i = 0; j < Math.floor(this.getWidth() / 50 + 1); i++) {
            for(; TileSettings.isGenerated(levelEditor.tileset.ids.get((i + levelEditor.dx) % levelEditor.tileset.images.size())); i++) {}
            g.drawImage(levelEditor.tileset.images.get((i + levelEditor.dx) % levelEditor.tiles), j * 50 + 5, 5, 40, 40, null);
            j++;
        }
        
        g.setColor(new Color(a, a, a, 128));
        g.fillRect(mousePos * 50, 0, 50, 50);
    }
    
    public void mousePressed(MouseEvent e) {
        int j = -1;
        for(int i = 0; j < Math.floor(this.getWidth() / 50 + 1) - 1; i++) {
            for(; TileSettings.isGenerated(levelEditor.tileset.ids.get((i + levelEditor.dx) % levelEditor.tiles)); i++) {}
            int a = j * 50 + 5;
            int b = a + 50;
            
            if(mousePos * 50 < b && mousePos * 50 > a) {
                if(mousePos == levelEditor.tiles) {
                    //derp
                } else {
                    levelEditor.tile = levelEditor.tileset.ids.get((i + levelEditor.dx) % levelEditor.tiles);
                }
            }
            j++;
        }
    }
    
    public void mouseMoved(MouseEvent e) {
        mousePos = Math.round(e.getX() / 50);
    }
    
    
    public void mouseDragged(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
