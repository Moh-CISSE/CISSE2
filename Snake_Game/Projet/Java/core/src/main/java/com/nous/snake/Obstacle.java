package com.nous.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//cette classe  permet:
//Obstacle bloquant 
//Collision = Game Over 
//Affichage avec texture 
public class Obstacle extends Entity {

    private Texture texture;

    public Obstacle(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;

        texture = new Texture("tilesets/tree.png"); // mets ton image
    }

    @Override
    public boolean collidesWith(float px, float py) {
        return (px >= x && px < x + width && py >= y && py < y + height);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}
