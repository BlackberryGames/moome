package com.moome;

import java.awt.image.BufferedImage;

public class Player extends Entity {
    public boolean upSpace, up, right, down, left, a, d = false;
    public static Player player;
    private GameScreen gameScreen;
    public int looks = 0;
    
    public void spawn() {
        this.x = MapManager.mapManager.getCurrentMap().playerSpawnX;
        this.y = MapManager.mapManager.getCurrentMap().playerSpawnY;
        this.velX = 0;
        this.velY = 0;
    }
    
    public void setGameScreen(GameScreen a) {
        gameScreen = a;
    }
    
    public void move(int animation) {
        if(right) {
            velX = 3;
        }
        if(left) {
            velX = -3;
        }
        if(left == right) {
            velX = 0;
        }
        if(block(19, 19) == 'H' || block(0, 19) == 'H' || block(19, 2) == 'H' || block(0, 2) == 'H') {
            if(up || upSpace) {
                velY = -3;
            }
            if(down) {
                velY = 3;
            }
            if((up || upSpace) == down) {
                velY = 0;
            }
        }
        if(inWater()) {
            if(up || upSpace) {
                velY = -1F;
            }
        } else {
            if((up || upSpace) && (check(getWidth() - 1, 20) || check(0, 20))) {
                SoundManager.sound.playSound(1);
                velY = -9F;
            }
        }
        
        // START EXPERIMENT
        
        if(a) {
            this.flip = true;
        }
        if(d) {
            this.flip = false;
        }
        
        // END EXPERIMENT
        
        super.move(animation);
        checkCollectable(10, 10);
        if(Util.server != null && animation % 2 == 0) {
            looks = (this.flip ? 1 : 0) + (Math.round(animation / 2) % 2 == 0 ? 2 : 0);
            Util.server.updateCoords(this.x, this.y, looks);
        }
    }
    
    public String getImagePath() {
        return "../data/images/entities/moome.png";
    }
    
    public String getWalkingImagePath() {
        return "../data/images/entities/moome_walking.png";
    }
    
    public int getAILevel() {
        return 0;
    }
    
    public int getWidth() {
        return 18;
    }
    
    public int getHeight() {
        return 18;
    }
    
    public int getSpeed() {
        return 1;
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
                        map.entityManager.entities.remove(this.id);
                    }
                }
            }
        }
        if(tile == 'O') {
            MapManager.mapManager.nextMap();
            velX = 0;
            velY = 0;
            this.spawn();
        }
        if(tile == 'P') {
            MapManager.mapManager.getCurrentMap().playerSpawnX = c * 20;
            MapManager.mapManager.getCurrentMap().playerSpawnY = d * 20;
        }
        if(tile == ':') {
            map.setBlock(c, d, ' ');
            gameScreen.removeTile(c, d);
            gameScreen.cutscene(c - 1, d - 1);
        }
    }
    
    public void checkCollectable(int a, int b) {
        int c = (int)Math.round((this.x + a) / 20);
        int d = (int)Math.round((this.y + b) / 20);
        Map map = MapManager.mapManager.getCurrentMap();
        char tile = map.getTile(c, d);
        if(tile == 'c') {
            map.setBlock(c, d, ' ');
            gameScreen.removeTile(c, d);
            gameScreen.collectCoin();
            SoundManager.sound.playSound(0);
        }
        if(tile == 'C') {
            map.setBlock(c, d, ' ');
            gameScreen.removeTile(c, d);
            gameScreen.collectRuby();
            SoundManager.sound.playSound(0);
        }
    }
}
