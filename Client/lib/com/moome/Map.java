package com.moome;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import java.io.File;
import java.util.ArrayList;

public class Map {
    public int width, height = 0;
    public String[] map;
    private static int id = 0;
    public int playerSpawnX, playerSpawnY = 0;
    public int dirtline = -1;
    public boolean hasDirtline = false;
    public boolean cloudWorld = false;
    public EntityManager entityManager;
    public int[][] cloudXY;
    Random random = new Random();
    int cutsceneStart;
    int cutsceneEnd;
    
    public Map(String s, GameScreen g) {
        cutsceneStart = g.cutscenes.size();
        cutsceneEnd = g.cutscenes.size();
        map = getFile(s, g);
        entityManager = new EntityManager();
    }
    
    public Map(GameScreen g) {
        cutsceneStart = g.cutscenes.size();
        cutsceneEnd = g.cutscenes.size();
        generateWorld(10, 10);
        entityManager = new EntityManager();
    }
    
    public char getTile(int x, int y) {
        try {
            if(x == 0 && !cloudWorld) {
                return '#';
            } else {
                if(map[y].charAt(x) == ' ' && y == dirtline && hasDirtline) {
                    return ' ';
                }
            }
            return map[y].charAt(x);
        } catch(ArrayIndexOutOfBoundsException e) {
            if((x <= 0 || x >= width) && !cloudWorld) {
                return '#';
            } else {
                return ' ';
            }
        } catch(StringIndexOutOfBoundsException e) {
            if((x <= 0 || x >= width) && !cloudWorld) {
                return '#';
            } else {
                return ' ';
            }
        }
    }
    
    public void setBlock(int x, int y, char c) {
        if(y < map.length && y >= 0 && x < map[y].length() && x >= 1) {
            map[y] = map[y].substring(0, x) + c + map[y].substring(x + 1);
        }
    }
    
    public boolean underground(int y) {
        return y >= dirtline + 1 && hasDirtline;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return map.length;
    }
    
    public String[] getFile(String filename, GameScreen g) {
        System.out.println("Loading map " + filename + "...");
        String s = "";
        try {
            FileReader f = new FileReader(filename);
            BufferedReader b = new BufferedReader(f);
            String a = b.readLine();
            if(a != null && a.equals("&")) {
                this.cloudWorld = true;
                a = b.readLine();
            }
            int y = 0;
            while(a != null) {
                a = " " + a;
                if(a.substring(a.length() - 1).equals("+")) {
                    dirtline = y;
                    hasDirtline = true;
                    s = s + "\n" + removeLastChar(a);
                } else {
                    s = s + "\n" + a;
                }
                if(a.length() > width) {
                    width = a.length();
                }
                if(a.contains("S")) {
                    playerSpawnX = a.indexOf("S") * 20;
                    playerSpawnY = y * 20 + 20;
                }
                for(int i = 0; i < a.length(); i++) {
                    if(a.charAt(i) == ':') {
                        g.cutscenes.add(new Cutscene(i - 1, y, new CutsceneScreen(g.preferences.get("cutscenes").split(",")[g.cutscenes.size()])));
                        cutsceneEnd++;
                    }
                }
                a = b.readLine();
                y++;
            }
            height = y;
            f.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        int clouds = width;
        cloudXY = new int[][] {new int[clouds], new int[clouds]};
        for(int i = 0; i < clouds; i++) {
            cloudXY[0][i] = Math.round(i * ((float)width * 20 / (float)clouds));// + random.nextInt(10) - 5);
            cloudXY[1][i] = random.nextInt(100) * -1;
        }
        return s.split("\n");
    }
    
    public boolean isLoaded() {
        return map != null;
    }
    
    public static String removeLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }
    
    public void generateWorld(int w, int h) {
        map = new String[h];
        for(int i = 0; i < h; i++) {
            map[i] = new String(new char[10]).replace('\0', ' ');
        }
        setBlock(0, 0, 'S');
        for(int i = 0; i < w; i++) {
            setBlock(i, h - 1, '#');
        }
        for(int i = 0; i < h; i++) {
            System.out.println(map[i]);
        }
    }
}
