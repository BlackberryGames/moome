package com.moome;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;

public class GameScreen extends Screen {
    private Tileset tileset;
    public BufferedImage cloud = Util.getImage("../data/images/misc/cloud.png");
    public BufferedImage draw;
    public BufferedImage drawF;
    public int offsetX;
    public int offsetY;
    public static GameScreen gameScreen;
    public int coins = 0;
    public int rubies = 0;
    Preferences preferences = new Preferences();
    ArrayList<Cutscene> cutscenes = new ArrayList<>();
    
    public GameScreen() {
        File f = new File(Util.tmpFile, "map");
        if(f.exists()) {
            Util.clearFolder(f);
        } else {
            f.mkdir();
        }
        Util.unzip(new File("../data/maps/map.zip"), f);
        
        File f1 = new File(Util.tmpFile + File.separator + "map" + File.separator + "properties.txt");
        if(f1.exists()) {
            preferences.setPropertiesFile(f1);
        } else {
            System.out.println("Error: no properties.txt found");
            System.exit(1);
        }
        gameScreen = this;
        tileset = new Tileset("../data/images/tileset.txt");
        String[] maps = preferences.get("maps").split(",");
        for(int i = 0; i < maps.length; i++) {
            MapManager.mapManager.maps.add(new Map(new File(Util.tmpFile + File.separator + "map" + File.separator + maps[i]).getPath(), this));
        }
        Player.player = new Player();
        Player.player.setGameScreen(this);
        Player.player.spawn();
        offsetX = ceil2(MoomePane.WIDTH);
        offsetY = ceil2(MoomePane.HEIGHT);
        renderMap();
    }
    
    public void draw(Graphics g, int animation) {
        Map map = MapManager.mapManager.getCurrentMap();
        g.setColor(new Color(255, 200, 0));
        int w = map.width * 20 + 40;
        if(map.isLoaded()) {
            for(int i = 0; i < map.width; i++) {
                g.drawImage(cloud, ((map.cloudXY[0][i] + 100 - animation) % w + w) % w - Player.player.x, map.cloudXY[1][i] + 100 - (Player.player.y + 10) / 1, null);
            }
        }
        g.drawImage(draw, Player.player.x * -1 + MoomePane.WIDTH * 10 - 10 - 20 * offsetX, Player.player.y * -1 + MoomePane.HEIGHT * 10 - 10 - 20 * offsetY, null);
        for(int i = 0; i < map.entityManager.entities.size(); i++) {
            Entity e = map.entityManager.entities.get(i);
            if(!e.removed) {
                if(e.flip) {
                    g.drawImage(e.getFlippedImage(), e.x - Player.player.x + MoomePane.WIDTH * 10 - 10, e.y - Player.player.y + MoomePane.HEIGHT * 6 + 38, null);
                } else {
                    g.drawImage(e.getImage(), e.x - Player.player.x + MoomePane.WIDTH * 10 - 10, e.y - Player.player.y + MoomePane.HEIGHT * 6 + 38, null);
                }
            }
        }
        if(Util.server != null) {
            System.out.println("drawing for multiplayer...");
            for(final MoomeServerUser m : Util.server.usrdata) {
                final int x = Integer.parseInt(m.x);
                final int y = Integer.parseInt(m.y);
                final int l = Integer.parseInt(m.looks);
                if(m.visible.equals("true") && !(Util.server.csrf.equals(m.csrf)/*Player.player.x == x && Player.player.y == y && Player.player.looks == l*/)) {
                    drawPlayer(g, l % 2 == 0, x - Player.player.x + MoomePane.WIDTH * 10 - 10, y - Player.player.y + MoomePane.HEIGHT * 6 + 38, Math.floor(l / 2) == 1);
                    /*System.out.println("  drawing: " + (Integer.parseInt(m.x) - Player.player.x + MoomePane.WIDTH * 10 - 10) + ", " + (Integer.parseInt(m.y) - Player.player.y + MoomePane.HEIGHT * 6 + 38));*/
                }
                
            }
        }
        drawPlayer(g, !Player.player.flip, MoomePane.WIDTH * 10 - 10, MoomePane.HEIGHT * 10 - 10, Math.round(animation / 2) % 2 == 0);
        g.drawImage(drawF, Player.player.x * -1 + MoomePane.WIDTH * 10 - 10 - 20 * offsetX, Player.player.y * -1 + MoomePane.HEIGHT * 10 - 10 - 20 * offsetY, null);
        g.drawImage(tileset.image('C'), 0, 0, null);
        Util.drawText(g, 20, 6, Integer.toString(rubies), Util.FONT_WHITE);
        g.drawImage(tileset.image('c'), 0, 20, null);
        Util.drawText(g, 20, 26, Integer.toString(coins), Util.FONT_WHITE);
    }
    
    public void drawPlayer(final Graphics g, final boolean facingRight, final int x, final int y, final boolean walkAnimation) {
        if(facingRight) {
            if(Player.player.velX > 0 && walkAnimation && !Player.player.canMoveDown()) {
                g.drawImage(Player.player.getWalkingImage(), x, y, null);
            } else {
                g.drawImage(Player.player.getImage(), x, y, null);
            }
        } else {
            if(Player.player.velX < 0 && walkAnimation && !Player.player.canMoveDown()) {
                g.drawImage(Player.player.getWalkingFlippedImage(), x - 2, y, null);
            } else {
                g.drawImage(Player.player.getFlippedImage(), x - 2, y, null);
            }
        }
    }
    
    public int ceil2(int i) {
        return (int)Math.round((float)i / 2F + 0.4F);
    }
    
    public void keyPressed(int i) {
        switch(i) {
            case KeyEvent.VK_RIGHT:
                Player.player.right = true;
            break;
            case KeyEvent.VK_LEFT:
                Player.player.left = true;
            break;
            case KeyEvent.VK_UP:
                Player.player.up = true;
            break;
            case KeyEvent.VK_DOWN:
                Player.player.down = true;
            break;
            case KeyEvent.VK_R:
                Player.player.spawn();
            break;
            case KeyEvent.VK_SPACE:
                Player.player.upSpace = true;
            break;
            case KeyEvent.VK_A:
                Player.player.a = true;
            break;
            case KeyEvent.VK_D:
                Player.player.d = true;
            break;
        }
    }
    
    public void keyReleased(int i) {
        switch(i) {
            case KeyEvent.VK_RIGHT:
                Player.player.right = false;
            break;
            case KeyEvent.VK_LEFT:
                Player.player.left = false;
            break;
            case KeyEvent.VK_UP:
                Player.player.up = false;
            break;
            case KeyEvent.VK_DOWN:
                Player.player.down = false;
            break;
            case KeyEvent.VK_SPACE:
                Player.player.upSpace = false;
            break;
            case KeyEvent.VK_A:
                Player.player.a = false;
            break;
            case KeyEvent.VK_D:
                Player.player.d = false;
            break;
        }
    }
    
    public void removeTile(int x, int y) {
        Graphics2D g = (Graphics2D)draw.getGraphics();
        if(MapManager.mapManager.getCurrentMap().underground(y)) {
            g.drawImage(tileset.image('@'), (x + offsetX) * 20, (y + offsetY) * 20, null);
        } else {
            g.setBackground(new Color(255, 255, 255, 0));
            g.clearRect((x + offsetX) * 20, (y + offsetY) * 20, 20, 20);
        }
        g.dispose();
    }
    
    public void renderMap() {
        Map map = MapManager.mapManager.getCurrentMap();
        draw = new BufferedImage((map.width * offsetX) * 20, (map.height * offsetY + 2) * 20, BufferedImage.TYPE_INT_ARGB);
        drawF = new BufferedImage((map.width * offsetX) * 20, (map.height * offsetY + 2) * 20, BufferedImage.TYPE_INT_ARGB);
        Graphics g = draw.getGraphics();
        for(int x = 1; x < map.width; x++) {
            for(int y = 1; y < map.height + 1; y++) {
                char tile = map.getTile(x, y);
                int drawX = (x + offsetX) * 20;
                int drawY = (y + offsetY) * 20;
                if(map.underground(y)) {
                    g.drawImage(tileset.image('@'), drawX, drawY, null);
                }
                if(!TileSettings.isForeground(tile)) {
                    if(tile == '#') {
                        if(TileSettings.isTransparent(map.getTile(x, y - 1))) {
                            g.drawImage(tileset.image('='), drawX, drawY, null);
                        } else {
                            g.drawImage(tileset.image('#'), drawX, drawY, null);
                        }
                    } else {
                        g.drawImage(tileset.image(tile), drawX, drawY, null);
                    }
                }
                if(tile == 'T') {
                    EntityTurtle e = new EntityTurtle();
                    map.entityManager.addEntity(e.setID(map.entityManager.entities.size()), x * 20, y * 20 + MoomePane.HEIGHT * 2 - (10 + e.getHeight()));
                    map.setBlock(x, y, ' ');
                } else {
                    if(tile == 'n') {
                        EntityCat e = new EntityCat();
                        map.entityManager.addEntity(e.setID(map.entityManager.entities.size()), x * 20, y * 20 + MoomePane.HEIGHT * 2 - (10 + e.getHeight()));
                        map.setBlock(x, y, ' ');
                    } else {
                        if(tile == 'q') {
                            EntityChicken e = new EntityChicken();
                            map.entityManager.addEntity(e.setID(map.entityManager.entities.size()), x * 20, y * 20 + MoomePane.HEIGHT * 2 - (10 + e.getHeight()));
                            map.setBlock(x, y, ' ');
                        } else {
                            if(tile == 'b') {
                                EntitySimese e = new EntitySimese();
                                map.entityManager.addEntity(e.setID(map.entityManager.entities.size()), x * 20, y * 20 + MoomePane.HEIGHT * 2 - (10 + e.getHeight()));
                                map.setBlock(x, y, ' ');
                            } else {
                                if(tile == 'z') {
                                    EntityCow e = new EntityCow();
                                    map.entityManager.addEntity(e.setID(map.entityManager.entities.size()), x * 20, y * 20 + MoomePane.HEIGHT * 2 - (10 + e.getHeight()));
                                    map.setBlock(x, y, ' ');
                                } else {
                                    if(tile == 'f') {
                                        EntityFish e = new EntityFish();
                                        map.entityManager.addEntity(e.setID(map.entityManager.entities.size()), x * 20, y * 20 + MoomePane.HEIGHT * 2 - (10 + e.getHeight()));
                                        map.setBlock(x, y, 'o');
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        g.dispose();
        drawF = new BufferedImage((map.width + offsetX + 2) * 20, (map.height * offsetY + 2) * 20, BufferedImage.TYPE_INT_ARGB);
        g = drawF.getGraphics();
        for(int x = 1; x < map.width; x++) {
            for(int y = 1; y < map.height + 1; y++) {
                char tile = map.getTile(x, y);
                int drawX = (x + offsetX) * 20;
                int drawY = (y + offsetY) * 20;
                if(TileSettings.isForeground(tile)) {
                    g.drawImage(tileset.image(tile), drawX, drawY, null);
                }
                if(TileSettings.isTransparent(tile)) {
                    char tile1 = map.getTile(x, y + 1);
                    if(tile1 == '#') {
                        g.drawImage(tileset.image('.'), drawX, drawY, null);
                    }
                    tile1 = map.getTile(x, y - 1);
                    if(tile1 == '#' || tile1 == '$') {
                        g.drawImage(tileset.image('\''), drawX, drawY, null);
                    }
                    tile1 = map.getTile(x + 1, y);
                    if((tile1 == '#' || tile1 == '$') && TileSettings.isTransparent(map.getTile(x + 1, y - 1))) {
                        g.drawImage(tileset.image('/'), drawX, drawY, null);
                    }
                    tile1 = map.getTile(x - 1, y);
                    if((tile1 == '#' || tile1 == '$') && TileSettings.isTransparent(map.getTile(x - 1, y - 1))) {
                        g.drawImage(tileset.image('\\'), drawX, drawY, null);
                    }
                } else if(tile == 'o' || tile == 'W') {
                    if(TileSettings.isTransparent(map.getTile(x, y - 1))) {
                        g.drawImage(tileset.image('*'), drawX, drawY, null);
                    } else {
                        g.drawImage(tileset.image('o'), drawX, drawY, null);
                    }
                    if(map.getTile(x, y + 1) == '#') {
                        g.drawImage(tileset.image('_'), drawX, drawY, null);
                    }
                } else {
                    if(map.getTile(x, y - 1) == '#' && (TileSettings.isTransparent(tile))) {
                        g.drawImage(tileset.image('\''), drawX, drawY, null);
                    }
                }
            }
        }
        g.dispose();
    }
    
    public void collectCoin() {
        coins++;
    }
    
    public void collectRuby() {
        rubies++;
    }
    
    public void cutscene(int x, int y) {
        System.out.println("trying to load cutscene...");
        ArrayList<Cutscene> possibleCutscenes = new ArrayList<>();
        for(int i = 0; i < cutscenes.size(); i++) {
            System.out.println(x + " == " + cutscenes.get(i).x + ", " + y + " == " + cutscenes.get(i).y);
            if(cutscenes.get(i).x == x && cutscenes.get(i).y == y) {
                Map m = MapManager.mapManager.getCurrentMap();
                System.out.println("  yep...");
                System.out.println(i + " >= " + m.cutsceneStart + " && " + i + " <= " + (m.cutsceneEnd - 1));
                if(i >= m.cutsceneStart && i <= m.cutsceneEnd - 1) {
                    cutscenes.get(i).screen.show();
                    ScreenManager.overrideScreen(cutscenes.get(i).screen);
                    Player.player.up = false;
                    Player.player.right = false;
                    Player.player.down = false;
                    Player.player.left = false;
                    Player.player.d = false;
                    Player.player.a = false;
                }
            }
        }
    }
    
    public int getMusic() {
        return 0;
    }
}
