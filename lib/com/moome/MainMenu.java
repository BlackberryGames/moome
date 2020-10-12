package com.moome;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class MainMenu extends MenuScreen {
    BufferedImage b = Util.getImage("../data/images/misc/logo.png");
    
    public void draw(Graphics g, int animation) {
        super.draw(g, animation);
        g.drawImage(b, Util.center(100, MoomePane.WIDTH * 20), 10, null);
    }
    
    public MainMenu() {
        this.elements.add(new Button("Singleplayer", (MoomePane.HEIGHT * 10 + 15) - 30, 90));
        this.elements.add(new Button("Multiplayer", (MoomePane.HEIGHT * 10 + 15) - 17, 90));
        this.elements.add(new Button("Options", (MoomePane.HEIGHT * 10 + 15) - 4, 90));
        this.elements.add(new Button("Credits", (MoomePane.HEIGHT * 10 + 15) + 9, 90));
        this.elements.add(new Button("Beta Testing", (MoomePane.HEIGHT * 10 + 15) + 22, 90, Color.GREEN.darker(), Util.FONT_WHITE));
        this.elements.add(new Button("Exit", (MoomePane.HEIGHT * 10 + 15) + 35, 90));
    }
    
    public void buttonPress() {
        switch(selected) {
            case 0:
                ScreenManager.setScreen(8);
            break;
            case 1:
                try {
                    String[] s = JOptionPane.showInputDialog("IP").split(":");
                    MoomeServerConnector server = new MoomeServerConnector(s[0], Integer.parseInt(s[1]), JOptionPane.showInputDialog("Name?"));
                    server.setListener(new MoomeServerEventListener() {
                        public void onConnect() {
                            ScreenManager.setScreen(6);
                        }
                        
                        public void onDisconnect(String reason) {}
                    });
                    server.connect();
                    Util.server = server;
                    ScreenManager.setScreen(9);
                } catch(MoomeServerException e) {
                } catch(ArrayIndexOutOfBoundsException e) {}
            break;
            case 2:
                ScreenManager.setScreen(3);
            break;
            case 3:
                ScreenManager.setScreen(4);
            break;
            case 4:
                ScreenManager.setScreen(7);
            break;
            case 5:
                System.exit(0);
            break;
            default: break;
        }
    }
}
