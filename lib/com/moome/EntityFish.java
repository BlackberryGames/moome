package com.moome;

public class EntityFish extends EntityAI4 {
    public String getImagePath() {
        return "../data/images/entities/fish.png";
    }
    
    public String getWalkingImagePath() {
        return "../data/images/entities/fish.png";
    }
    
    public int getWidth() {
        return 20;
    }
    
    public int getHeight() {
        return 10;
    }
    
    public int getSpeed() {
        return 1;
    }
    
    public boolean staysInWater() {
        return true;
    }
}
