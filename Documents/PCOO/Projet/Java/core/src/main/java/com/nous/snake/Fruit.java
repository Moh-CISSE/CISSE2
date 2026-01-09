package com.nous.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fruit extends Entity implements Collectible {

    private Texture texture;

    public Fruit(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;

        texture = new Texture("tilesets/fruit.png");
    }

    @Override
    public void collect() {}

    @Override
    public boolean collidesWith(float px, float py) {
        return (px >= x && px < x + width && py >= y && py < y + height);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}
