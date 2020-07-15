package com.moome;

import java.util.ArrayList;

public class EntityManager {
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    
    public void addEntity(Entity e, int a, int b) {
        e.x = a;
        e.y = b;
        entities.add(e);
    }
    
    public void moveEntities(int animation) {
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).move(animation);
        }
    }
}
