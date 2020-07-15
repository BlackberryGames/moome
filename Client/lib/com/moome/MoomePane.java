package com.moome;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class MoomePane extends JPanel implements KeyListener {
    private MoomeGame game;
    private int width, height = 0;
    public static final int WIDTH = 12;
    public static final int HEIGHT = 12;
    public static int animation = 0;
    
    BufferedImage screen = new BufferedImage(WIDTH * 20, HEIGHT * 20, BufferedImage.TYPE_INT_ARGB);
    
    public MoomePane(MoomeGame m) {
        this.addKeyListener(this);
        setBackground(Color.black);
        this.width = WIDTH * 20;
        this.height = HEIGHT * 20;
        this.game = m;
        this.setFocusable(true);
        this.requestFocus();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(MoomeGame.WEIRD) {
            g.drawImage(screen, (int)((width / 2 - height / 2) + height * Math.sin(Math.toRadians(animation)) * -0.5 + height / 2), 0, (int)(height * Math.sin(Math.toRadians(animation))), height, null);
        } else {
            g.drawImage(screen, (width / 2 - height / 2), 0, height, height, null);
        }
    }
    
    public void redraw() {
        Graphics g = screen.createGraphics();
        super.paintComponent(g);
        g.setColor(ScreenManager.getScreen().background);
        g.fillRect(0, 0, WIDTH * 20, HEIGHT * 20);
        ScreenManager.getScreen().draw(g, animation);
        animation++;
    }
    
    public void setSize(int w, int h) {
        System.out.println(w + ", " + h);
        this.width = w;
        this.height = h;
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        ScreenManager.getScreen().keyPressed(e.getKeyCode());
        ScreenManager.getScreen().keyPressed(e.getKeyChar());
    }
    
    public void keyReleased(KeyEvent e) {
        ScreenManager.getScreen().keyReleased(e.getKeyCode());
        ScreenManager.getScreen().keyReleased(e.getKeyChar());
    }
    
    public void keyTyped(KeyEvent e) {}
}
