package com.nous.snake;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.*;

// cette classe  Charge les maps .tmx Gère : 
//serpent 
//obstacles 
//bordures 
//fruits

public class TiledGameMap {

    public static final int TILE_SIZE = 32;

    private TiledMap map;
    public Snake snake;

    private TiledMapTileLayer borderLayer;
    private TiledMapTileLayer obstaclesLayer;
    private TiledMapTileLayer fruitsLayer;

    public TiledGameMap(String path) {
        map = new TmxMapLoader().load(path);

        borderLayer    = (TiledMapTileLayer) map.getLayers().get("bordure");
        obstaclesLayer = (TiledMapTileLayer) map.getLayers().get("obstacles");
        fruitsLayer    = (TiledMapTileLayer) map.getLayers().get("fruits");

        loadSnake();
    }

    private void loadSnake() {
        for (MapObject obj : map.getLayers().get("serpent").getObjects()) {
            if ("snake_spawn".equals(obj.getProperties().get("type"))) {
                float x = (float) obj.getProperties().get("x");
                float y = (float) obj.getProperties().get("y");
                snake = new Snake(x, y);
            }
        }
    }

    public boolean isBlocked(int tileX, int tileY) {
        if (tileX < 0 || tileY < 0 ||
            tileX >= borderLayer.getWidth() ||
            tileY >= borderLayer.getHeight()) return true;

        if (borderLayer.getCell(tileX, tileY) != null) return true;
        if (obstaclesLayer.getCell(tileX, tileY) != null) return true;

        return false;
    }

    public boolean hasFruit(int tileX, int tileY) {
        return fruitsLayer != null && fruitsLayer.getCell(tileX, tileY) != null;
    }

    public void removeFruit(int tileX, int tileY) {
        if (fruitsLayer != null)
            fruitsLayer.setCell(tileX, tileY, null);
    }

    public int getMapWidthPixels() {
        return map.getProperties().get("width", Integer.class) * TILE_SIZE;
    }

    public int getMapHeightPixels() {
        return map.getProperties().get("height", Integer.class) * TILE_SIZE;
    }

    public TiledMap getMap() { return map; }
    public int countFruits() {
    int count = 0;

    for (int x = 0; x < fruitsLayer.getWidth(); x++) {
        for (int y = 0; y < fruitsLayer.getHeight(); y++) {
            if (fruitsLayer.getCell(x, y) != null) {
                count++;
            }
        }
    }
    return count;
}

}
