package com.moome;

import java.util.Arrays;

public class TileSettings {
    static char[] transparentBlocks = {' ', 'c', 'h', '4', 'U', 'H', 'S', 'T', 'O', '^', '*', 'F'};
    static char[] solidBlocks = {'=', '#', '%', '>', 's', 'E', '<', '!', '(', ')'};
    static char[] foregroundBlocks = {'O', 'o'};
    static char[] generatedBlocks = {'/', '=', '\\', '.', '\''};
    static char[] killBlocks = {'^', 'X'};
    
    public static boolean isTransparent(char c) {
        for (int i = 0; i < transparentBlocks.length; i++) {
            if (c == transparentBlocks[i]) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isSolid(char c) {
        for (int i = 0; i < solidBlocks.length; i++) {
            if (c == solidBlocks[i]) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isForeground(char c) {
        for (int i = 0; i < foregroundBlocks.length; i++) {
            if (c == foregroundBlocks[i]) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean canKill(char c) {
        for (int i = 0; i < killBlocks.length; i++) {
            if (c == killBlocks[i]) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isGenerated(char c) {
        for (int i = 0; i < generatedBlocks.length; i++) {
            if (c == generatedBlocks[i]) {
                return true;
            }
        }
        return false;
    }
}
