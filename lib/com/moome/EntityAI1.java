package com.moome;

public abstract class EntityAI1 extends Entity {
    int length = 0;
    int dir = 0;
    
    public void move(int animation) {
        if(length == 0) {
            length = r.nextInt(5) + 3;
            dir = r.nextInt(3);
        }
        if(dir == 0) {
            velX = -1;
        } else {
            if(dir == 1) {
                velX = 0;
            } else {
                velX = 1;
            }
        }
        length--;
        super.move(animation);
    }
}
