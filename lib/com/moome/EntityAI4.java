package com.moome;

public abstract class EntityAI4 extends Entity {
    int lengthX = 0;
    int lengthY = 0;
    int dirX = 0;
    int dirY = 0;
    
    public void move(int animation) {
        if(lengthX == 0) {
            boolean followPlayer = r.nextInt(5) > 0;
            lengthY = r.nextInt(20) + 10;
            dirX = ((Math.abs(Player.player.x - x) < 70) ? -2 * (((Player.player.x) - x) / Math.max(1, Math.abs(Player.player.x - x))) : r.nextInt(3) - 1);
        }
        if(dirX == 0) {
            velX = 0;
        } else {
            if(dirX < 0) {
                velX = -1 * Math.max(2, Math.min(5, 5 - Math.abs(Player.player.x - x) / 20));
            } else {
                velX = Math.max(2, Math.min(5, 5 - Math.abs(Player.player.x - x) / 20));
            }
        }
        if(lengthY == 0) {
            boolean followPlayer = r.nextInt(5) > 0;
            lengthY = r.nextInt(20) + 10;
            dirY = ((Math.abs(Player.player.y - y) < 20) ? -2 * (((Player.player.y) - y) / Math.max(1, Math.abs(Player.player.y - y))) : r.nextInt(3) - 1);
        }
        if(dirY == 0) {
            velY = 0;
        } else {
            if(dirY < 0) {
                velY = -1 * Math.max(2, Math.min(5, 5 - Math.abs(Player.player.y - y) / 20));
            } else {
                velY = Math.max(2, Math.min(5, 5 - Math.abs(Player.player.y - y) / 20));
            }
        }
        lengthX--;
        lengthY--;
        super.move(animation);
    }
    
    public void XCollisionEvent() {
        lengthX = 0;
    }
    
    public void YCollisionEvent() {
        lengthY = 0;
    }
}
