package com.moome;

public abstract class EntityAI2 extends Entity {
    int length = 20;
    int dir = 1;
    
    public void move(int animation) {
        if(length == 0) {
            boolean followPlayer = r.nextInt(2) == 0;
            length = r.nextInt(20) + 10;
            dir = (r.nextInt(3) - 1) + (followPlayer && !(Math.abs(Player.player.x - x) < 50) ? 2 * ((Player.player.x - x) / Math.abs(Player.player.x - x)) : 0);
        }
        if(dir == 0) {
            velX = 0;
        } else {
            if(dir < 0) {
                velX = -2;
            } else {
                velX = 2;
            }
        }
        length--;
        super.move(animation);
    }
    
    public void aboutToCollideX() {
        if(check(getWidth() - 1, getHeight()) || check(0, getHeight())) {
            velY = -7F;
        }
    }
    
    public void XCollisionEvent() {
        length = 0;
    }
}
