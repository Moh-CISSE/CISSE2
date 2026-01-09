package com.nous.snake;

public class Border extends Entity implements Collidable {

    public Border(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;
    }

    @Override
    public boolean collidesWith(float px, float py) {
        return px == x && py == y;
    }
}
