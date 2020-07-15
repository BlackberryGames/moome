package com.moome;

public class EntityChicken extends EntityAI3 {
    public String getImagePath() {
        return "../data/images/entities/chicken.png";
    }
    
    public String getWalkingImagePath() {
        return "../data/images/entities/chicken_walking.png";
    }
    
    public int getWidth() {
        return 17;
    }
    
    public int getHeight() {
        return 14;
    }
    
    public int getSpeed() {
        return 2;
    }
}
