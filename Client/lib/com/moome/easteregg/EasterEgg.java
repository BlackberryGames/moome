package com.moome.easteregg;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import com.moome.Util;

public class EasterEgg extends JPanel {
    BufferedImage awesomeface = Util.getImage("../data/images/misc/awesomeface.png");
    float x = 0;
    
    public EasterEgg() {
        this.setPreferredSize(new Dimension(255, 255));
        this.setBackground(Color.white);
        
        JFrame frame = new JFrame("You found the easter egg!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setVisible(true);
        
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    x += (float)EasterEgg.this.getWidth() / 50f;
                    repaint();
                    try {
                        Thread.sleep(40);
                    } catch(Exception e) {}
                }
            }
        });
        t.start();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(awesomeface, ((int)x % (this.getWidth() * 2)) - this.getWidth(), 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(awesomeface, (((int)x + this.getWidth()) % (this.getWidth() * 2)) - this.getWidth(), 0, this.getWidth(), this.getHeight(), null);
    }
}
