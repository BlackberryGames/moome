package com.moome;

public abstract class EntityAI3 extends Entity {
    int length = 20;
    int dir = 1;
    
    public void move(int animation) {
        for(Entity e : MapManager.mapManager.getCurrentMap().entityManager.entities) {
            //code here... not done
        }
        if(length == 0) {
            boolean followPlayer = r.nextInt(5) > 0;
            length = r.nextInt(20) + 10;
            dir = ((Math.abs(Player.player.x - x) < 70) ? -2 * (((Player.player.x) - x) / Math.max(1, Math.abs(Player.player.x - x))) : r.nextInt(3) - 1);
        }
        if(dir == 0) {
            velX = 0;
        } else {
            if(dir < 0) {
                velX = -1 * Math.max(2, Math.min(5, 5 - Math.abs(Player.player.x - x) / 20));
            } else {
                velX = Math.max(2, Math.min(5, 5 - Math.abs(Player.player.x - x) / 20));
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
