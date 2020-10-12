package com.moome.leveleditor;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;

public class SMap {
    public String[] map;
    public int width = 0;
    public int height = 0;
    
    public SMap(File f) {
        map = getFile(f);
    }
    
    public SMap(int w, int h) {
        map = new String[h];
        for(int i = 0; i < h; i++) {
            map[i] = new String(new char[w]).replace('\0', ' ');
        }
    }
    
    public char getTile(int x, int y) {
        try {
            return map[y].charAt(x);
        } catch(ArrayIndexOutOfBoundsException e) {
            return ' ';
        } catch(StringIndexOutOfBoundsException e) {
            return ' ';
        }
    }
    
    public void setBlock(int x, int y, char c) {
        if(y < map.length && y >= 0 && x < map[y].length() && x >= 0) {
            map[y] = map[y].substring(0, x) + c + map[y].substring(x + 1);
        }
    }
    
    public String[] getFile(File file) {
        String s = "";
        try {
            FileReader f = new FileReader(file);
            BufferedReader b = new BufferedReader(f);
            String a = b.readLine();
            if(a != null && a.equals("&")) {
                a = b.readLine();
            }
            height = -1;
            while(a != null) {
                if(a.substring(a.length() - 1).equals("+")) {
                    s = s + "\n" + removeLastChar(a);
                    if(a.length() - 1 > width) {
                        width = a.length() - 1;
                    }
                } else {
                    s = s + "\n" + a;
                    if(a.length() > width) {
                        width = a.length();
                    }
                }
                a = b.readLine();
                height++;
            }
            f.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        height += 1;
        return removeFirstChar(s).split("\n");
    }
    
    private String removeLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }
    
    private String removeFirstChar(String s) {
        return s.substring(1, s.length());
    }
    
    public void writeFile(File file) {
        try {
            FileWriter f = new FileWriter(file);
            BufferedWriter b = new BufferedWriter(f);
            for(int i = 0; i < map.length; i++) {
                String x = map[i];
                b.write(x);
                b.newLine();
            }
            b.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public String toString() {
        String a = "";
        for(int i = 0; i < map.length; i++) {
            if(i == map.length - 1) {
                a += map[i];
            } else {
                a += map[i] + "\n";
            }
        }
        return a;
    }
}
