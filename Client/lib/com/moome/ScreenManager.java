package com.moome;

import java.util.ArrayList;

public class ScreenManager {
    private static ArrayList<Screen> screens = new ArrayList<Screen>();
    public static int currentScreen = 0;
    public static Screen screen;
    
    public static void addScreen(Screen s) {
        screens.add(s);
        screen = screens.get(currentScreen);
    }
    
    public static Screen getScreen() {
        return screen;
    }
    
    public static Screen getScreen(int i) {
        return screens.get(i);
    }
    
    public static void setScreen(int i) {
        currentScreen = i;
        screen = screens.get(currentScreen);
        init();
    }
    
    public static void overrideScreen(Screen s) {
        screen = s;
        init();
    }
    
    public static void resetOverride() {
        screen = screens.get(currentScreen);
        init();
    }
    
    public static void init() {
        if(screen.getMusic() != SoundManager.sound.currentMusicId) {
            SoundManager.sound.playMusic(screen.getMusic());
        }
    }
}
