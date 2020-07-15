package com.moome;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Util {
    public static BufferedImage[] fonts = new BufferedImage[] {getImage("../data/images/misc/fontWhite.png"),
                                                               getImage("../data/images/misc/fontBlack.png")};
    public static final int FONT_WHITE = 0, FONT_BLACK = 1;
    public static final File tmpFile = new File(new File(new File(System.getProperty("user.home")), "moome"), ".tmp");
    public static boolean adventureMode = false;
    public static MoomeServerConnector server = null;
    
    public static BufferedImage getImage(String filename) {
        File f = new File(filename);
        try {
            if(f.exists() && !f.isDirectory()) {
                return ImageIO.read(f);
            } else {
                System.out.println("Image \"" + filename + "\" could not be read");
                System.exit(1);
                return null;
            }
        } catch (IOException e) {
            System.out.println("Image \"" + filename + "\" could not be read");
            System.exit(1);
            return null;
        }
    }
    
    public static BufferedImage flip(BufferedImage image, boolean rightLeft, boolean upDown) {
        BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        int a = rightLeft ? 1 : 0;
        int b = upDown ? 1 : 0;
        g.drawImage(image, a * bi.getWidth(), b * bi.getHeight(), bi.getWidth() - (bi.getWidth() * a * 2), bi.getHeight() - (bi.getHeight() * b * 2), null);
        g.dispose();
        return bi;
    }
    
    public static void drawText(Graphics g, int x, int y, String text, int font) {
        for(int i = 0; i < text.length(); i++) {
            int j = (int)text.charAt(i);
            int a = 0;
            int b = 0;
            if(j >= 97 && j <= 122) {
                a = (j - 97) % 12;
                b = (int)Math.floor((float)(j - 97) / 12F);
                g.drawImage(fonts[font].getSubimage(a * 5, b * 7, 5, 7), i * 7 + x, y, null);
            } else if(j >= 65 && j <= 90) {
                a = (j - 65) % 12;
                b = (int)Math.floor((float)(j - 65) / 12F);
                g.drawImage(fonts[font].getSubimage(a * 5, b * 7, 5, 7), i * 7 + x, y, null);
            } else if(j >= 48 && j <= 57) {
                a = (j - 22) % 12;
                b = (int)Math.floor((float)(j - 22) / 12F);
                g.drawImage(fonts[font].getSubimage(a * 5, b * 7, 5, 7), i * 7 + x, y, null);
            } else if((char)j == ':') {
                g.drawImage(fonts[font].getSubimage(0, 21, 5, 7), i * 7 + x, y, null);
            }
        }
    }
    
    public static BufferedImage drawText(String text, int font) {
        BufferedImage bi = new BufferedImage(text.length() * 7 + (text.length() < 1 ? 1 : -2), 7, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        drawText(g, 0, 0, text, font);
        g.dispose();
        return bi;
    }
    
    public static BufferedImage drawMultilinedText(String text, boolean centered, int font) {
        return drawMultilinedText(text, centered, 2, font);
    }
    
    public static BufferedImage drawMultilinedText(String text, boolean centered, int lineSpacing, int font) {
        String[] lines = text.split("\n");
        int width = 0;
        for(int i = 0; i < lines.length; i++) {
            if(lines[i].length() > width) {
                width = lines[i].length();
            }
        }
        BufferedImage bi = new BufferedImage(width * 7 - 1, lines.length * (7 + lineSpacing) - 2, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        for(int i = 0; i < lines.length; i++) {
            String text1 = lines[i];
            boolean b1 = test(text1, "<a>");
            boolean b2 = test(text1, "<b>");
            BufferedImage b = drawText(text1, b1 ? 1 : font);
            g.drawImage(b, centered || b2 ? center(b.getWidth(), width * 7) : 0, i * (7 + lineSpacing), null);
        }
        g.dispose();
        return bi;
    }
    
    public static int center(int boxDimension, int parentBoxDimension) {
        return parentBoxDimension / 2 - boxDimension / 2;
    }
    
    public static void unzip(File zipFile, File folder) {
        byte[] buffer = new byte[1024];
        
        try {
            if(!folder.exists()){
                folder.mkdir();
            }
            
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry ze = zis.getNextEntry();
            
            while(ze != null) {
                String fileName = ze.getName();
                File newFile = new File(folder.getPath() + File.separator + fileName);
                
                System.out.println("file unzip : "+ newFile.getAbsoluteFile());
                
                new File(newFile.getParent()).mkdirs();
                
                FileOutputStream fos = new FileOutputStream(newFile);             
                
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                
                fos.close();   
                ze = zis.getNextEntry();
            }
            
            zis.closeEntry();
            zis.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files != null) {
            for(int i = 0; i < files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteFolder(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        folder.delete();
    }
    
    public static boolean test(String s, String d) {
        boolean a = s.startsWith(d);
        if(a) {
            s = s.substring(d.length(), s.length());
        }
        return a;
    }
    
    public static void clearFolder(File dir) {
        for(File f: dir.listFiles()) {
            f.delete();
        }
    }
    
    public static List<String> index(List<String> all, String keyword) {
        List<String> indexlist = new ArrayList<>();
        String k = keyword.toLowerCase();
        for(String s : all) {
            String l = s.toLowerCase();
            if(l.contains(k)) {
                indexlist.add(s);
            }
            l = null;
            k = null;
        }
        return indexlist;
    }
    
    public static void sleep(int n) {
        try {
            Thread.sleep(n);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
