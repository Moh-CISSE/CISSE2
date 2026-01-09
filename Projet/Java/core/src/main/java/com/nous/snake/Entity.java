package com.nous.snake;

public abstract class Entity implements Collidable {
    protected float x, y;
    protected float width = 32;
    protected float height = 32;

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
