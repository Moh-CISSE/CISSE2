package com.nous.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.LinkedList;

public class Snake extends Entity {

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private static final int TILE_SIZE = 32;

    private Direction direction = Direction.RIGHT;
    private Direction nextDirection = Direction.RIGHT;

    // 🟢 textures tête
    private Texture headUp, headDown, headLeft, headRight;

    // 🟢 textures queue (⚠️ ICI était l’erreur)
    private Texture tailUp, tailDown, tailLeft, tailRight;

    // 🟢 segments du serpent (tête = first)
    private LinkedList<float[]> segments = new LinkedList<>();

    public Snake(float x, float y) {
        this.x = x;
        this.y = y;
        width = height = TILE_SIZE;

        // tête
        headUp    = new Texture("tilesets/head_h.png");
        headDown  = new Texture("tilesets/head_b.png");
        headLeft  = new Texture("tilesets/head_g.png");
        headRight = new Texture("tilesets/head_d.png");

        // queue
        tailUp    = new Texture("tilesets/til_h.png");
        tailDown  = new Texture("tilesets/til_b.png");
        tailLeft  = new Texture("tilesets/til_g.png");
        tailRight = new Texture("tilesets/til_d.png");

        // segments initiaux
        segments.add(new float[]{x, y});                 // tête
        segments.add(new float[]{x - TILE_SIZE, y});     // queue
    }

    public void setDirection(Direction newDir) {
        if ((direction == Direction.UP && newDir == Direction.DOWN) ||
            (direction == Direction.DOWN && newDir == Direction.UP) ||
            (direction == Direction.LEFT && newDir == Direction.RIGHT) ||
            (direction == Direction.RIGHT && newDir == Direction.LEFT)) {
            return;
        }
        nextDirection = newDir;
    }

    public void move() {
        direction = nextDirection;

        float[] head = segments.getFirst();
        float newX = head[0];
        float newY = head[1];

        if (direction == Direction.UP) newY += TILE_SIZE;
        else if (direction == Direction.DOWN) newY -= TILE_SIZE;
        else if (direction == Direction.LEFT) newX -= TILE_SIZE;
        else if (direction == Direction.RIGHT) newX += TILE_SIZE;

        segments.addFirst(new float[]{newX, newY});
        segments.removeLast();
    }

    public void draw(SpriteBatch batch) {
        float[] head = segments.getFirst();
        batch.draw(getHeadTexture(), head[0], head[1]);

        float[] tail = segments.getLast();
        batch.draw(getTailTexture(), tail[0], tail[1]);
    }

    private Texture getHeadTexture() {
        if (direction == Direction.UP) return headUp;
        if (direction == Direction.DOWN) return headDown;
        if (direction == Direction.LEFT) return headLeft;
        return headRight;
    }

    private Texture getTailTexture() {
        if (direction == Direction.UP) return tailUp;
        if (direction == Direction.DOWN) return tailDown;
        if (direction == Direction.LEFT) return tailLeft;
        return tailRight;
    }

    @Override
    public boolean collidesWith(float px, float py) {
        return false;
    }
    public boolean collidesWithSelf() {

    // tête du serpent
    float[] head = segments.getFirst();

    // on commence à 1 pour ignorer la tête
    for (int i = 1; i < segments.size(); i++) {
        float[] segment = segments.get(i);

        if (head[0] == segment[0] && head[1] == segment[1]) {
            return true; // 💀 collision avec le corps
        }
    }
    return false;
}

}
