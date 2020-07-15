package com.moome;

import java.util.Random;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public float velX, velY = 0.0F;
    public int AILevel, x, y = 0;
    protected Random r = new Random();
    public boolean flip = false;
    public BufferedImage[] images = new BufferedImage[4];
    public String imagePath;
    protected boolean removed = false;
    public int id;
    
    public Entity() {
        images[0] = Util.getImage(getImagePath());
        images[2] = Util.getImage(getWalkingImagePath());
    }
    
    public void move(int animation) {
        changeY(Math.round(velY));
        if(animation % getSpeed() == 0) {
            changeX(Math.round(velX));
        }
        if(!staysInWater()) {
            velY += 0.5;
            velY = inWater() ? (velY > 0 ? 0.5F : velY) : (velY < 10 ? velY + 0.5F : velY);
        }
        checkSpecial(0, 0);
        checkSpecial(getWidth() - 1, 0);
        checkSpecial(getWidth() - 1, getHeight() - 1);
        checkSpecial(0, getHeight() - 1);
    }
    
    public void changeY(int dy) {
        if(dy != 0) {
            if(dy > 0) { // DOWN
                for(int i = 0; i < dy; i++) {
                    if(canMoveDown()) {
                        y += 1;
                    } else {
                        this.YCollisionEvent();
                    }
                }
            } else { // UP
                for(int i = 0; i < dy * -1; i++) {
                    if(canMoveUp() && !(staysInWater() && (!(block(getWidth() - 1, -1) == 'o' || block(0, -1) == 'o')))) {
                        y -= 1;
                    } else {
                        this.YCollisionEvent();
                    }
                }
            }
        }
    }
    
    public void changeX(int dx) {
        if(dx != 0) {
            if(dx > 0) { // RIGHT
                flip = false;
                for(int i = 0; i < dx; i++) {
                    if(canMoveRight()) {
                        x += 1;
                    } else {
                        this.XCollisionEvent();
                    }
                }
                if((check(getWidth() + 10, 0, 1) || check(getWidth() + 10, getHeight() - 1, 1)) && !check(getWidth() + 10, -20, 1)) {
                    this.aboutToCollideX();
                }
            } else { // LEFT
                flip = true;
                for(int i = 0; i < dx * -1; i++) {
                    if(canMoveLeft()) {
                        x -= 1;
                    } else {
                        this.XCollisionEvent();
                    }
                }
                if((check(-10, 0, 2) || check(-10, getHeight() - 1, 2)) && !check(-10, -20, 2)) {
                    this.aboutToCollideX();
                }
            }
        }
    }
    
    public boolean check(int a, int b) {
        if(block(a, b) == '>') {
            if(velX < 10) {
                velX += 2;
            }
        }
        if(block(a, b) == '<') {
            if(velX > -10) {
                velX -= 2;
            }
        }
        return TileSettings.isSolid(block(a, b));
    }
    
    public boolean check(int a, int b, int c) {
        return check(a, b) && !((block(a, b) == '(' && c == 2) || (block(a, b) == ')' && c == 1));
    }
    
    public char block(int a, int b) {
        return MapManager.mapManager.getCurrentMap().getTile((int)Math.round((this.x + a) / 20), (int)Math.round((this.y + b) / 20));
    }
    
    public void checkSpecial(int a, int b) {
        int c = (int)Math.round((this.x + a) / 20);
        int d = (int)Math.round((this.y + b) / 20);
        Map map = MapManager.mapManager.getCurrentMap();
        char tile = map.getTile(c, d);
        if(TileSettings.canKill(tile)) {
            if(TileSettings.canKill(block(a, b - 10))) {
                if(this instanceof Player) {
                    Player.player.spawn();
                } else {
                    if(!removed) {
                        removed = true;
                    }
                }
            }
        }
    }
    
    public boolean canMoveUp() {
        return !(check(getWidth() - 1, -1) || check(0, -1));
    }
    
    public boolean canMoveDown() {
        return !(check(getWidth() - 1, getHeight()) || check(0, getHeight()));
    }
    
    public boolean canMoveRight() {
        return !(check(getWidth(), getHeight() - 1, 1) || check(getWidth(), 0, 1));
    }
    
    public boolean canMoveLeft() {
        return !(check(-1, getHeight() - 1, 2) || check(-1, 0, 2));
    }
    
    public BufferedImage getImage() {
        return images[0];
    }
    
    public BufferedImage getFlippedImage() {
        if(images[1] == null) {
            if(images[0] == null) {
                return null;
            } else {
                images[1] = Util.flip(images[0], true, false);
            }
        }
        return images[1];
    }
    
    public BufferedImage getWalkingImage() {
        return images[2];
    }
    
    public BufferedImage getWalkingFlippedImage() {
        if(images[3] == null) {
            if(images[2] == null) {
                return null;
            } else {
                images[3] = Util.flip(images[2], true, false);
            }
        }
        return images[3];
    }
    
    public abstract String getImagePath();
    public abstract String getWalkingImagePath();
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getSpeed();
    
    public void XCollisionEvent() {
        velX = 0;
    }
    
    public void YCollisionEvent() {
        velY = 0;
    }
    
    public void aboutToCollideX() {}
    public boolean staysInWater() {
        return false;
    }
    
    public boolean inWater() {
        return block(getWidth() - 1, getHeight() - 1) == 'o' || block(0, getHeight() - 1) == 'o';
    }
    
    public Entity setID(int i) {
        this.id = i;
        return this;
    }
}
