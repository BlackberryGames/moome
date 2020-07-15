package com.moome;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Tileset {
    public ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    public ArrayList<Character> ids = new ArrayList<Character>();
    public Tileset(String s) {
        parseFile(s);
    }
    
    public void parseFile(String filename) {
        String s = "";
        try {
            FileReader f = new FileReader(filename);
            BufferedReader b = new BufferedReader(f);
            String a = b.readLine();
            while(a != null) {
                parse(a);
                a = b.readLine();
            }
            f.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void parse(String s) {
        String[] a = s.split(" ");
        if(s.charAt(0) == ' ' && s.charAt(1) == ' ') {
            images.add(Util.getImage("../data/images/" + a[2] + ".png"));
            ids.add(' ');
        } else {
            images.add(Util.getImage("../data/images/" + a[1] + ".png"));
            ids.add(a[0].charAt(0));
        }
    }
    
    public BufferedImage image(char c) {
        if(ids.contains(c)) {
            return images.get(ids.indexOf(c));
        } else {
            return null;
        }
    }
}
