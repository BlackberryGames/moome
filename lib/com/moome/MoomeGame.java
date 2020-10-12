package com.moome;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.lang.Thread;
import java.lang.Runnable;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class MoomeGame {
    boolean pastBeta = false;
    JFrame frame;
    MoomePane gamePane;
    public final static boolean WEIRD = false;
    
    public MoomeGame() {
        if(pastBeta) {
            ScreenManager.addScreen(new PastBetaScreen());
        } else {
            ScreenManager.addScreen(new IntroScreen());
            ScreenManager.addScreen(new MainMenu());
            ScreenManager.addScreen(new GameScreen());
            ScreenManager.addScreen(new OptionScreen());
            ScreenManager.addScreen(new CreditScreen());
            ScreenManager.addScreen(new ShopScreen((GameScreen)ScreenManager.getScreen(2)));
            ScreenManager.addScreen(new StartGameScreen((GameScreen)ScreenManager.getScreen(2)));
            ScreenManager.addScreen(new BetaTestScreen());
            ScreenManager.addScreen(new GameTypeScreen());
            ScreenManager.addScreen(new TextScreen("Connecting..."));
        }
        SoundManager.sound.addSound("../data/audio/coin.wav");
        SoundManager.sound.addSound("../data/audio/jump.wav");
        SoundManager.sound.addSound("../data/audio/explode.wav");
        SoundManager.sound.addMusic("../data/audio/music/music1.wav");
        SoundManager.sound.addMusic("../data/audio/music/credits.wav");
        ScreenManager.setScreen(0);
        
        gamePane = new MoomePane(this);
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        gamePane.setSize(new Dimension(width, height));
        
        frame = new JFrame("Moome");
        frame.setIconImage(Util.getImage("../data/images/entities/moome.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setUndecorated(true);
        frame.setResizable(false);
        gd.setFullScreenWindow(frame);

        frame.setLayout(new BorderLayout());
        frame.getContentPane().add("Center", gamePane);
        frame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if(Util.server != null) {
                    Util.server.disconnect();
                }
            }
        });
        Runnable renderer = new Runnable() {
            public void run() {
                gamePane.redraw();
                gamePane.repaint();
            }
        };
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                for(;;) {
                    if(ScreenManager.currentScreen == 2) {
                        Player.player.move(i);
                        MapManager.mapManager.getCurrentMap().entityManager.moveEntities(i);
                        i++;
                    }
                    new Thread(renderer).start();
                    try {
                        Thread.sleep(40);
                    } catch(Exception e) {}
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for(;;) {
                    if(Util.server != null) {
                        Util.server.updateData();
                    }
                    try {
                        Thread.sleep(50);
                    } catch(Exception e) {}
                }
            }
        });
        t2.start();
    }
}
