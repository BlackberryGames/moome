package com.moome;

import java.util.ArrayList;

public class MapManager {
    public int currentMap = 0;
    public ArrayList<Map> maps = new ArrayList<Map>();
    public static final MapManager mapManager = new MapManager();
    
    private MapManager() {}
    
    public Map getCurrentMap() {
        return maps.get(currentMap);
    }
    
    public void setMap(int i) {
        this.currentMap = i;
        Player.player.spawn();
    }
    
    public void nextMap() {
        this.setMap((currentMap + 1) % maps.size());
        GameScreen.gameScreen.renderMap();
    }
}
