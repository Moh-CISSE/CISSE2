package com.nous.snake;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class TiledGameMap {

    public static final int TILE_SIZE = 32;

    private TiledMap map;
    public Snake snake;

    private TiledMapTileLayer borderLayer;
    private TiledMapTileLayer obstaclesLayer;

    public TiledGameMap(String path) {
        map = new TmxMapLoader().load(path);

        borderLayer = (TiledMapTileLayer) map.getLayers().get("bordure");
        obstaclesLayer = (TiledMapTileLayer) map.getLayers().get("obstacles");

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

    // 🔹 utilisé par GameScreen
    public TiledMap getMap() {
        return map;
    }

    // 🔹 taille en pixels (caméra)
    public int getMapWidthPixels() {
        int widthTiles = map.getProperties().get("width", Integer.class);
        return widthTiles * TILE_SIZE;
    }

    public int getMapHeightPixels() {
        int heightTiles = map.getProperties().get("height", Integer.class);
        return heightTiles * TILE_SIZE;
    }

    // 🔹 collision mur / obstacle
    public boolean isBlocked(int tileX, int tileY) {

        // hors map = mort
        if (tileX < 0 || tileY < 0 ||
            tileX >= borderLayer.getWidth() ||
            tileY >= borderLayer.getHeight()) {
            return true;
        }

        if (borderLayer != null && borderLayer.getCell(tileX, tileY) != null)
            return true;

        if (obstaclesLayer != null && obstaclesLayer.getCell(tileX, tileY) != null)
            return true;

        return false;
    }
}
